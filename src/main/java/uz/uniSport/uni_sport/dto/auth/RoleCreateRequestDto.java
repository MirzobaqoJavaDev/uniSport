package uz.uniSport.uni_sport.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleCreateRequestDto {
    private String name;
    private RoleAssigmentPermissionDto assigmentPermission;
}
