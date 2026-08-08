package uz.uniSport.uni_sport.controller.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.uniSport.uni_sport.dto.auth.JwtAuthResponse;
import uz.uniSport.uni_sport.dto.auth.LoginRequest;
import uz.uniSport.uni_sport.dto.auth.RegisterRequest;
import uz.uniSport.uni_sport.dto.auth.UserDto;
import uz.uniSport.uni_sport.service.auth.AuthService;

/**
 * Autentifikatsiya qilish (Ro'yxatdan o'tish, Login) uchun API.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

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
        String token = authService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(new JwtAuthResponse(token, "Bearer"));
    }
}
