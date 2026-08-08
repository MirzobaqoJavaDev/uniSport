package uz.uniSport.uni_sport.dto.integration;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class DynamicQRCodeResponseDTO {
    private UUID id;
    private UUID userId;
    private String qrData;
    private LocalDateTime expiresAt;
    private Boolean isUsed;
}
