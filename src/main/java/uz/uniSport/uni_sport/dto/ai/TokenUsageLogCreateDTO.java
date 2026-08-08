package uz.uniSport.uni_sport.dto.ai;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenUsageLogCreateDTO {
    @NotNull(message = "User ID is required")
    private UUID userId;

    @NotBlank(message = "Request Type is required")
    private String requestType;

    @NotNull(message = "Tokens Used is required")
    private Integer tokensUsed;
}
