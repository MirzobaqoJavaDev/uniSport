package uz.uniSport.uni_sport.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class IdempotencyFilter extends OncePerRequestFilter {

    private final StringRedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String idempotencyKey = request.getHeader("Idempotency-Key");

        // Faqat POST, PUT yoki DELETE so'rovlari va Idempotency-Key bor bo'lsa ishlaydi
        if (StringUtils.hasText(idempotencyKey) && 
            (request.getMethod().equals("POST") || request.getMethod().equals("PUT") || request.getMethod().equals("DELETE"))) {
            
            String redisKey = "idempotency:" + idempotencyKey;
            String cachedResponse = redisTemplate.opsForValue().get(redisKey);

            if (cachedResponse != null) {
                // Javob keshlangan bo'lsa, xuddi shuni qaytaramiz (takrorlanish oldi olinadi)
                response.setStatus(HttpServletResponse.SC_OK); // Yoki keshdagi haqiqiy status kodi (hozircha sodda variant)
                response.setContentType("application/json");
                response.getWriter().write(cachedResponse);
                return;
            }

            // Javobni ushlab qolish uchun wrapper
            ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
            
            filterChain.doFilter(request, responseWrapper);

            // Javobni o'qib olamiz
            byte[] responseArray = responseWrapper.getContentAsByteArray();
            String responseBody = new String(responseArray, responseWrapper.getCharacterEncoding());

            // Agar so'rov muvaffaqiyatli bo'lsa (2xx), keshga saqlaymiz
            if (response.getStatus() >= 200 && response.getStatus() < 300) {
                redisTemplate.opsForValue().set(redisKey, responseBody, 24, TimeUnit.HOURS);
            }

            // Wrapper ichidagi javobni asliga ko'chiramiz
            responseWrapper.copyBodyToResponse();
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
