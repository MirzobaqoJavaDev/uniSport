package uz.uniSport.uni_sport.dto.auth;

import lombok.*;

import java.util.UUID;


@Builder
@Getter
public class JwtAuthResponse {
    private String accessToken;
    private String tokenType;
    private String name;
    private String role;
    private UUID uuid;



}
