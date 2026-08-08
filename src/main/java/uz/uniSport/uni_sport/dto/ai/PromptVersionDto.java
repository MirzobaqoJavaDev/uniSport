package uz.uniSport.uni_sport.dto.ai;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Prompt versiyasi (Prompt Version) uchun DTO.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromptVersionDto {
    private UUID id;
    private PromptTemplateDto template;
    private String versionNumber;
    private String promptText;
    private boolean isActive;
}
