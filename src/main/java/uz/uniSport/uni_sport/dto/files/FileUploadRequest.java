package uz.uniSport.uni_sport.dto.files;

import lombok.Data;
import java.util.UUID;

@Data
public class FileUploadRequest {
    private UUID userId;
    private String originalName;
    private String contentType;
    private Long size;
}
