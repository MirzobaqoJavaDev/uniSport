package uz.uniSport.uni_sport.dto.ai;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.uniSport.uni_sport.dto.auth.UserDto;

import java.util.UUID;

/**
 * AI so'rovlari jurnali (AI Request Log) uchun DTO.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIRequestLogDto {
    private UUID id;
    private UserDto user;
    private PromptTemplateDto promptTemplate;
    private String requestPayload;
    private String responsePayload;
    private Long executionTimeMs;
}
