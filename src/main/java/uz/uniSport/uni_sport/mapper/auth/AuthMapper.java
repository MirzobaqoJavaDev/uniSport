package uz.uniSport.uni_sport.mapper.auth;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import uz.uniSport.uni_sport.domain.auth.Role;
import uz.uniSport.uni_sport.domain.auth.User;
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
}
