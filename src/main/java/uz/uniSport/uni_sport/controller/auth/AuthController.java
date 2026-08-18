package uz.uniSport.uni_sport.controller.auth;

import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.uniSport.uni_sport.dto.auth.*;
import uz.uniSport.uni_sport.service.auth.AuthService;
import uz.uniSport.uni_sport.service.auth.impl.RefreshTokenServiceImpl;

/**
 * Autentifikatsiya qilish (Ro'yxatdan o'tish, Login) uchun API.
 */
@Tag(name = "Autentifikatsiya", description = "Tizimga kirish, ro'yxatdan o'tish va tokenlarni boshqarish")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenServiceImpl refreshTokenServiceImpl;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterRequest request) {
        UserDto registeredUser = authService.register(
                request.getEmail(),
                request.getPassword(),
                request.getFirstName(),
                request.getLastName()
        );
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthResponse> refresh(@RequestBody RefreshTokenRequestDto dto){
        return ResponseEntity.ok(authService.refresh(dto));
    }
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody RefreshTokenRequestDto request) {
        refreshTokenServiceImpl.deleteByToken(request.getRefreshToken());
        return ResponseEntity.noContent().build();
    }

}
