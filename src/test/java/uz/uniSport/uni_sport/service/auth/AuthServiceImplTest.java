package uz.uniSport.uni_sport.service.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.uniSport.uni_sport.domain.auth.Role;
import uz.uniSport.uni_sport.domain.auth.User;
import uz.uniSport.uni_sport.dto.auth.UserDto;
import uz.uniSport.uni_sport.exception.BusinessLogicException;
import uz.uniSport.uni_sport.exception.ResourceNotFoundException;
import uz.uniSport.uni_sport.mapper.auth.AuthMapper;
import uz.uniSport.uni_sport.repository.auth.RoleRepository;
import uz.uniSport.uni_sport.repository.auth.UserRepository;
import uz.uniSport.uni_sport.security.JwtTokenProvider;
import uz.uniSport.uni_sport.service.auth.impl.AuthServiceImpl;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private AuthMapper authMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthServiceImpl authService;

    private User user;
    private Role role;

    @BeforeEach
    void setUp() {
        role = new Role();
        role.setId(1L);
        role.setName("STUDENT");

        user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("test@student.uz");
        user.setPasswordHash("hashedpassword");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setRole(role);
    }

    @Test
    void register_Success() {
        // Arrange
        String email = "new@student.uz";
        String password = "password";
        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(roleRepository.findByName("STUDENT")).thenReturn(Optional.of(role));
        when(passwordEncoder.encode(password)).thenReturn("hashed");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto userDto = new UserDto();
        userDto.setEmail(email);
        when(authMapper.toDto(user)).thenReturn(userDto);

        // Act
        UserDto result = authService.register(email, password, "New", "User");

        // Assert
        assertNotNull(result);
        assertEquals(email, result.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void register_EmailAlreadyExists_ThrowsException() {
        // Arrange
        String email = "test@student.uz";
        when(userRepository.existsByEmail(email)).thenReturn(true);

        // Act & Assert
        BusinessLogicException exception = assertThrows(BusinessLogicException.class, () -> 
            authService.register(email, "pass", "Test", "User")
        );
        assertTrue(exception.getMessage().contains("avval ro'yxatdan o'tilgan"));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void register_RoleNotFound_ThrowsException() {
        // Arrange
        String email = "new@student.uz";
        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(roleRepository.findByName("STUDENT")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> 
            authService.register(email, "pass", "Test", "User")
        );
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void login_Success() {
        // Arrange
        String email = "test@student.uz";
        String password = "password";
        Authentication authentication = mock(Authentication.class);
        
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtTokenProvider.generateToken(authentication)).thenReturn("mocked.jwt.token");

        // Act
        String token = authService.login(email, password);

        // Assert
        assertEquals("mocked.jwt.token", token);
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void login_BadCredentials_ThrowsException() {
        // Arrange
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> authService.login("test", "wrong"));
    }
}
