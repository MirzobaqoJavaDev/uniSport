package uz.uniSport.uni_sport.controller.auth;

import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.uniSport.uni_sport.dto.auth.UserDto;
import uz.uniSport.uni_sport.service.auth.UserService;

import java.util.UUID;

/**
 * Foydalanuvchilar ma'lumotlarini boshqarish uchun API.
 */
@Tag(name = "Foydalanuvchilar", description = "Foydalanuvchilar profillari va ma'lumotlarini boshqarish")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
}
