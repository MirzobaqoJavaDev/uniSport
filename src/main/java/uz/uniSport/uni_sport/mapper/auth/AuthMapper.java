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
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthMapper {
    
    // Role uchun id Long type'da ekan, uuid shart emas
    RoleDto toDto(Role role);
    
    @Mapping(target = "uuid", ignore = true)
    Role toEntity(RoleDto roleDto);

    @Mapping(source = "uuid", target = "id")
    @Mapping(source = "role", target = "role") // role.id (Long) RoleDto.id (Long) ga to'g'ri keladi
    UserDto toDto(User user);

    // Permission uchun ham id Long bo'lsa
    PermissionResponseDTO toDto(Permission permission);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    Permission toEntity(PermissionCreateDTO dto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    void updateEntity(PermissionUpdateDTO dto, @MappingTarget Permission permission);
}
