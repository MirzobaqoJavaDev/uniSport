package uz.uniSport.uni_sport.dto.ai;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Prompt shabloni (Prompt Template) uchun DTO.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromptTemplateDto {
    private UUID id;
    private String name;
    private String description;
}
