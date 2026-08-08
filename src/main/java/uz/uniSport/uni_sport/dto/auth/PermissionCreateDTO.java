package uz.uniSport.uni_sport.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionCreateDTO {
    @NotBlank(message = "Permission name is required")
    private String name;

    private String description;
}
