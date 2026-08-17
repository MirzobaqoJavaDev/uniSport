package uz.uniSport.uni_sport.service.auth;

import uz.uniSport.uni_sport.dto.auth.*;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserDto getUserById(UUID id);
    UserDto getUserByEmail(String email);

    RoleAddResponseDto createRole(RoleCreateRequestDto dto);

    RoleAddResponseDto assigmentPermissions(UUID uuid, RoleAssigmentPermissionDto dto);

    List<RoleDto> allRole();
}
