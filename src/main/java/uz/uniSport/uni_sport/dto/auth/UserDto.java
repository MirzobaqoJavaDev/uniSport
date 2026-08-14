package uz.uniSport.uni_sport.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Foydalanuvchi ma'lumotlarini (User) xavfsiz shaklda tashish uchun DTO.
 * Parol kabi maxfiy ma'lumotlarni o'z ichiga olmaydi.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private UUID uuid;
    private String email;
    private String firstName;
    private String lastName;
    private RoleDto role;
    private LocalDateTime createdAt;
}
