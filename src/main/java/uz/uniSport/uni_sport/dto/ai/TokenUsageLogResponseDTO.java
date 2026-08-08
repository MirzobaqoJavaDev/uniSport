package uz.uniSport.uni_sport.dto.ai;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class TokenUsageLogResponseDTO {
    private UUID id;
    private UUID userId;
    private String requestType;
    private Integer tokensUsed;
    private LocalDateTime timestamp;
}
