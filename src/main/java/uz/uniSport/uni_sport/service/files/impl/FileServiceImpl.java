package uz.uniSport.uni_sport.service.files.impl;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uz.uniSport.uni_sport.domain.auth.User;
import uz.uniSport.uni_sport.domain.files.FileMetadata;
import uz.uniSport.uni_sport.dto.files.FileMetadataDto;
import uz.uniSport.uni_sport.exception.BusinessLogicException;
import uz.uniSport.uni_sport.exception.ResourceNotFoundException;
import uz.uniSport.uni_sport.mapper.files.FilesMapper;
import uz.uniSport.uni_sport.repository.auth.UserRepository;
import uz.uniSport.uni_sport.repository.files.FileMetadataRepository;
import uz.uniSport.uni_sport.service.files.FileService;

import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileMetadataRepository fileRepository;
    private final UserRepository userRepository;
    private final FilesMapper filesMapper;
    private final MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String bucketName;

    @Override
    @Transactional
    public FileMetadataDto uploadFile(UUID userId, MultipartFile file) {
        User user = userRepository.findByUuid(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Foydalanuvchi topilmadi: " + userId));

        if (file.isEmpty()) {
            throw new BusinessLogicException("Fayl bo'sh bo'lishi mumkin emas");
        }

        String originalName = file.getOriginalFilename();
        String contentType = file.getContentType();
        long size = file.getSize();

        // Xavfsizlik: Bajariladigan fayllarni (executable) cheklash mumkin
        if (contentType != null && (contentType.contains("exe") || contentType.contains("sh") || contentType.contains("bat"))) {
            throw new BusinessLogicException("Xavfsiz bo'lmagan fayl formati taqiqlanadi.");
        }

        String fileName = UUID.randomUUID().toString() + "_" + originalName;

        try (InputStream inputStream = file.getInputStream()) {
            // MinIO ga saqlash
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(inputStream, size, -1)
                            .contentType(contentType)
                            .build()
            );

            // DB ga metama'lumot yozish
            FileMetadata metadata = new FileMetadata();
            metadata.setOriginalName(originalName);
            metadata.setContentType(contentType);
            metadata.setSize(size);
            metadata.setUploadedBy(user);
            metadata.setStoragePath(fileName); // Fayl yo'li (kalit)

            FileMetadata saved = fileRepository.save(metadata);
            return filesMapper.toDto(saved);

        } catch (Exception e) {
            throw new BusinessLogicException("Faylni MinIO ga saqlashda xatolik yuz berdi: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public FileMetadataDto getFileById(UUID id) {
        FileMetadata metadata = fileRepository.findByUuid(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fayl topilmadi: " + id));
        return filesMapper.toDto(metadata);
    }

    @Override
    public String getFileDownloadUrl(UUID id) {
        FileMetadata metadata = fileRepository.findByUuid(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fayl topilmadi: " + id));
        
        try {
            // MinIO dan Pre-signed (vaqtinchalik ruxsat berilgan) URL olish (masalan, 1 soat)
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(metadata.getStoragePath())
                            .expiry(1, TimeUnit.HOURS)
                            .build()
            );
        } catch (Exception e) {
            throw new BusinessLogicException("Fayl uchun yuklab olish URL manzilini shakllantirib bo'lmadi: " + e.getMessage());
        }
    }
}
