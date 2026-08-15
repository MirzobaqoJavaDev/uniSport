package uz.uniSport.uni_sport.dto.auth;

import lombok.*;


@Builder
@Getter
public class JwtAuthResponse {
    private String accessToken;
    private String tokenType;
}
