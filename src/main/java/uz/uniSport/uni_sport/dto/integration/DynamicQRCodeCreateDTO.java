package uz.uniSport.uni_sport.dto.integration;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DynamicQRCodeCreateDTO {
    @NotNull(message = "User ID is required")
    private UUID userId;

    @NotBlank(message = "QR Data is required")
    private String qrData;

    @NotNull(message = "Expires At is required")
    private LocalDateTime expiresAt;
}
