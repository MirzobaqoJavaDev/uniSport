package uz.uniSport.uni_sport.dto.files;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.uniSport.uni_sport.dto.auth.UserDto;

import java.util.UUID;

/**
 * Yuklangan fayllar (File Metadata) uchun DTO.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileMetadataDto {
    private UUID id;
    private String originalName;
    private String storagePath;
    private String contentType;
    private Long size;
    private UserDto uploadedBy;
}
