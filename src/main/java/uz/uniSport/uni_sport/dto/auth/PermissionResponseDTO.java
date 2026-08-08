package uz.uniSport.uni_sport.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionResponseDTO {
    private Long id;
    private String name;
    private String description;
}
