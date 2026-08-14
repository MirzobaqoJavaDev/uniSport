package uz.uniSport.uni_sport.dto.auth;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PermissionResponseDTO {
    private UUID uuid;
    private String name;
    private String description;
}
