package uz.uniSport.uni_sport.service.auth.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.LocaleResolver;
import uz.uniSport.uni_sport.domain.auth.Role;
import uz.uniSport.uni_sport.domain.auth.User;
import uz.uniSport.uni_sport.dto.auth.JwtAuthResponse;
import uz.uniSport.uni_sport.dto.auth.LoginRequest;
import uz.uniSport.uni_sport.dto.auth.UserDto;
import uz.uniSport.uni_sport.exception.BusinessLogicException;
import uz.uniSport.uni_sport.exception.ResourceNotFoundException;
import uz.uniSport.uni_sport.mapper.auth.AuthMapper;
import uz.uniSport.uni_sport.repository.auth.RoleRepository;
import uz.uniSport.uni_sport.repository.auth.UserRepository;
import uz.uniSport.uni_sport.security.JwtTokenProvider;
import uz.uniSport.uni_sport.service.auth.AuthService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthMapper authMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public UserDto register(String email, String password, String firstName, String lastName) {
        if (userRepository.existsByEmail(email)) {
            throw new BusinessLogicException("Bu email orqali avval ro'yxatdan o'tilgan: " + email);
        }

        Role studentRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new ResourceNotFoundException("STUDENT roli bazada topilmadi"));

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPasswordHash(passwordEncoder.encode(password)); 
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setRole(studentRole);

        User savedUser = userRepository.save(newUser);
        return authMapper.toDto(savedUser);
    }

    @Override
    public JwtAuthResponse login(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtTokenProvider.generateToken(authentication);
        return JwtAuthResponse.builder()
                .accessToken(accessToken)
                .tokenType("Bearer")
                .build();
    }
}
