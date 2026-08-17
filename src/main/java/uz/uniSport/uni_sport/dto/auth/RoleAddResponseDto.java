package uz.uniSport.uni_sport.dto.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
public class RoleAddResponseDto {
    private UUID uuid;
    private String name;
    private Set<String> permissionCodes;
}
