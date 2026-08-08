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
 * Spring Component modelidan foydalanadi (Injection qilish uchun).
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthMapper {
    RoleDto toDto(Role role);
    Role toEntity(RoleDto roleDto);

    UserDto toDto(User user);

    PermissionResponseDTO toDto(Permission permission);
    
    @Mapping(target = "id", ignore = true)
    Permission toEntity(PermissionCreateDTO dto);
    
    @Mapping(target = "id", ignore = true)
    void updateEntity(PermissionUpdateDTO dto, @MappingTarget Permission permission);
}
