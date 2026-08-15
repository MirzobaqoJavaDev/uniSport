package uz.uniSport.uni_sport.service.auth;

import uz.uniSport.uni_sport.dto.auth.JwtAuthResponse;
import uz.uniSport.uni_sport.dto.auth.LoginRequest;
import uz.uniSport.uni_sport.dto.auth.UserDto;

public interface AuthService {
    UserDto register(String email, String password, String firstName, String lastName);
    JwtAuthResponse login(LoginRequest request);
}
