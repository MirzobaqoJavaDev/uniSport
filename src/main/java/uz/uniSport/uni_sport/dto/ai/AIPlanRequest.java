package uz.uniSport.uni_sport.dto.ai;

import lombok.Data;
import java.util.UUID;

@Data
public class AIPlanRequest {
    private UUID userId;
    private String promptText;
}
