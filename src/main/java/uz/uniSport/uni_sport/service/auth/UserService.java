package uz.uniSport.uni_sport.service.auth;

import uz.uniSport.uni_sport.dto.auth.UserDto;
import java.util.UUID;

public interface UserService {
    UserDto getUserById(UUID id);
    UserDto getUserByEmail(String email);
}
