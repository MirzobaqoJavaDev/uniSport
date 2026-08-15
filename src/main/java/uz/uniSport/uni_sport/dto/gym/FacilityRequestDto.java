package uz.uniSport.uni_sport.dto.gym;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FacilityRequestDto {
    private String name;
    private String description;
}
