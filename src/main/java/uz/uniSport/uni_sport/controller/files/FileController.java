package uz.uniSport.uni_sport.controller.files;

import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.uniSport.uni_sport.dto.files.FileMetadataDto;
import uz.uniSport.uni_sport.service.files.FileService;

import java.util.Map;
import java.util.UUID;

@Tag(name = "Fayllar", description = "Tizimga rasm, video va boshqa fayllarni yuklash hamda ularni ko'rish")
@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<FileMetadataDto> uploadFile(
            @RequestParam("userId") UUID userId,
            @RequestParam("file") MultipartFile file) {
            
        FileMetadataDto metadata = fileService.uploadFile(userId, file);
        return new ResponseEntity<>(metadata, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileMetadataDto> getFileMetadata(@PathVariable UUID id) {
        return ResponseEntity.ok(fileService.getFileById(id));
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Map<String, String>> getDownloadUrl(@PathVariable UUID id) {
        String url = fileService.getFileDownloadUrl(id);
        return ResponseEntity.ok(Map.of("downloadUrl", url));
    }
}
