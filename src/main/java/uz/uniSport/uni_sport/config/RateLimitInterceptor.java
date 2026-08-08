package uz.uniSport.uni_sport.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RateLimitInterceptor implements HandlerInterceptor {

    private final StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ip = request.getRemoteAddr();
        String path = request.getRequestURI();

        // Limitni belgilash: Auth uchun 5, oddiy API lar uchun 60 ta
        int limit = path.startsWith("/api/v1/auth") ? 5 : 60;
        String key = "rate_limit:" + ip + ":" + path;

        Long count = redisTemplate.opsForValue().increment(key);
        if (count != null && count == 1) {
            // Birinchi marta kiritilganda 1 minutlik TTL o'rnatamiz
            redisTemplate.expire(key, 1, TimeUnit.MINUTES);
        }

        if (count != null && count > limit) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Too many requests - Limitdan oshib ketdingiz!");
            return false;
        }

        return true;
    }
}
