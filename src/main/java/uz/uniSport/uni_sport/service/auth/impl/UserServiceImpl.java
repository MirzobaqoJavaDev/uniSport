package uz.uniSport.uni_sport.service.auth.impl;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.uniSport.uni_sport.domain.auth.Permission;
import uz.uniSport.uni_sport.domain.auth.Role;
import uz.uniSport.uni_sport.domain.auth.User;
import uz.uniSport.uni_sport.dto.auth.*;
import uz.uniSport.uni_sport.exception.BusinessLogicException;
import uz.uniSport.uni_sport.exception.ResourceNotFoundException;
import uz.uniSport.uni_sport.mapper.auth.AuthMapper;
import uz.uniSport.uni_sport.repository.auth.PermissionRepository;
import uz.uniSport.uni_sport.repository.auth.RoleRepository;
import uz.uniSport.uni_sport.repository.auth.UserRepository;
import uz.uniSport.uni_sport.service.auth.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthMapper authMapper;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserById(UUID id) {
        User user = userRepository.findByUuid(id)
                .orElseThrow(() -> new ResourceNotFoundException("Foydalanuvchi topilmadi: " + id));
        return authMapper.toDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Foydalanuvchi topilmadi, email: " + email));
        return authMapper.toDto(user);
    }

    @Override
    public RoleAddResponseDto createRole(RoleCreateRequestDto dto) {
        if (roleRepository.existsByName(dto.getName())){
            throw new BusinessLogicException("Role already exists "+dto.getName());
        }

        Role role = new Role();
        role.setName(dto.getName());
        if (dto.getAssigmentPermission().getPermissions().isEmpty()) {
            role.setPermissions(new HashSet<>());
        }else {
            List<Permission> permissions = getPermissions(dto.getAssigmentPermission());
            role.setPermissions(new HashSet<>(permissions));
        }
        roleRepository.save(role);
        return toResponse(role);
    }

    @Override
    public RoleAddResponseDto assigmentPermissions(UUID uuid, RoleAssigmentPermissionDto dto) {
        Role role = roleRepository.findByUuid(uuid)
                .orElseThrow(()-> new BusinessLogicException("Role  topilmadi: " + uuid));


        List<Permission> permissions = getPermissions(dto);
        role.setPermissions(new HashSet<>(permissions));
        roleRepository.save(role);
        return toResponse(role);
    }

    @Override
    public List<RoleDto> allRole() {
        return roleRepository.findAll().stream().
                map(role -> authMapper.toDto(role)).collect(Collectors.toList());
    }

    public RoleAddResponseDto toResponse(Role role) {
        return RoleAddResponseDto.builder()
                .name(role.getName())
                .uuid(role.getUuid())
                .permissionCodes(role.getPermissions().stream().
                        map(p->p.getName()).collect(Collectors.toSet()))
                .build();
    }

    private @NotNull List<Permission> getPermissions(RoleAssigmentPermissionDto dto) {
        List<Permission> permissions = permissionRepository.findAllByUuidIn(dto.getPermissions());

        if (permissions.size() != dto.getPermissions().size()){
            Set<UUID> foundUuids = permissions
                    .stream()
                    .map(Permission::getUuid)
                    .collect(Collectors.toSet());
            Set<UUID> missingUuids =new HashSet<>(dto.getPermissions());
            missingUuids.removeAll(foundUuids);
            throw new BusinessLogicException("Permissions not found in " + missingUuids);
        }
        return permissions;
    }
}
