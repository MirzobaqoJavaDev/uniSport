package uz.uniSport.uni_sport.dto.gym;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Sport inshootlari uchun DTO.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacilityDto {
    private UUID id;
    private String name;
    private String description;
}
