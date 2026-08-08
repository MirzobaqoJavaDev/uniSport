package uz.uniSport.uni_sport.service.files;

import org.springframework.web.multipart.MultipartFile;
import uz.uniSport.uni_sport.dto.files.FileMetadataDto;

import java.util.UUID;

public interface FileService {
    FileMetadataDto uploadFile(UUID userId, MultipartFile file);
    FileMetadataDto getFileById(UUID id);
    String getFileDownloadUrl(UUID id);
}
