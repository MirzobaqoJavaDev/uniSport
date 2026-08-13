package uz.uniSport.uni_sport.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import uz.uniSport.uni_sport.config.RateLimitInterceptor;
import uz.uniSport.uni_sport.dto.auth.LoginRequest;
import uz.uniSport.uni_sport.dto.auth.JwtAuthResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthorizationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RedisConnectionFactory redisConnectionFactory;
    @MockBean
    private ReactiveRedisConnectionFactory reactiveRedisConnectionFactory;
    @MockBean
    private RateLimitInterceptor rateLimitInterceptor;

    private String getAccessToken(String email, String password) throws Exception {
        Mockito.when(rateLimitInterceptor.preHandle(any(), any(), any())).thenReturn(true);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);

        String response = mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JwtAuthResponse tokenResponse = objectMapper.readValue(response, JwtAuthResponse.class);
        return tokenResponse.getAccessToken();
    }

    @Test
    public void userRole_CanReadFacilities_ButCannotCreateFacilities() throws Exception {
        String token = getAccessToken("test.user", "Password123!");

        mockMvc.perform(get("/api/v1/facilities")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/v1/facilities")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void sportDirectorRole_CanCreateFacilities() throws Exception {
        String token = getAccessToken("test.sport.director", "Password123!");

        String validPayload = "{\"name\":\"Test Facility\", \"description\":\"Test\", \"capacity\":100, \"location\":\"Campus\"}";

        mockMvc.perform(post("/api/v1/facilities")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validPayload))
                .andExpect(status().isCreated()); // Assuming 201 Created
    }
}
