package uz.uniSport.uni_sport.service.auth.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.uniSport.uni_sport.domain.auth.User;
import uz.uniSport.uni_sport.dto.auth.UserDto;
import uz.uniSport.uni_sport.exception.ResourceNotFoundException;
import uz.uniSport.uni_sport.mapper.auth.AuthMapper;
import uz.uniSport.uni_sport.repository.auth.UserRepository;
import uz.uniSport.uni_sport.service.auth.UserService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthMapper authMapper;

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserById(UUID id) {
        User user = userRepository.findByUuid(id)
                .orElseThrow(() -> new ResourceNotFoundException("Foydalanuvchi topilmadi: " + id));
        return authMapper.toDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Foydalanuvchi topilmadi, email: " + email));
        return authMapper.toDto(user);
    }
}
