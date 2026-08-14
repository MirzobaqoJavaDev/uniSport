package uz.uniSport.uni_sport.mapper.auth;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import uz.uniSport.uni_sport.domain.auth.Permission;
import uz.uniSport.uni_sport.domain.auth.Role;
import uz.uniSport.uni_sport.domain.auth.User;
import uz.uniSport.uni_sport.dto.auth.PermissionCreateDTO;
import uz.uniSport.uni_sport.dto.auth.PermissionResponseDTO;
import uz.uniSport.uni_sport.dto.auth.PermissionUpdateDTO;
import uz.uniSport.uni_sport.dto.auth.RoleDto;
import uz.uniSport.uni_sport.dto.auth.UserDto;

/**
 * MapStruct orqali Entity'larni DTO'larga aylantirish uchun interfeys.
 * Qoida: entity.uuid → dto.uuid (public identifier)
 *        entity.id   → hech qachon frontendga chiqmasin
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthMapper {

    // Role: uuid → RoleDto.uuid
    @Mapping(source = "uuid", target = "uuid")
    RoleDto toDto(Role role);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    Role toEntity(RoleDto roleDto);

    // User: uuid → UserDto.uuid
    @Mapping(source = "uuid", target = "uuid")
    @Mapping(source = "role", target = "role")
    UserDto toDto(User user);

    // Permission: uuid → PermissionResponseDTO.uuid
    @Mapping(source = "uuid", target = "uuid")
    PermissionResponseDTO toDto(Permission permission);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    Permission toEntity(PermissionCreateDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    void updateEntity(PermissionUpdateDTO dto, @MappingTarget Permission permission);
}
