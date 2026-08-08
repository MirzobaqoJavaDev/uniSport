package uz.uniSport.uni_sport.service.ai.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.RestTemplate;
import uz.uniSport.uni_sport.domain.ai.AIRequestLog;
import uz.uniSport.uni_sport.domain.ai.PromptTemplate;
import uz.uniSport.uni_sport.domain.auth.User;
import uz.uniSport.uni_sport.dto.ai.AIRequestLogDto;
import uz.uniSport.uni_sport.exception.BusinessLogicException;
import uz.uniSport.uni_sport.exception.ResourceNotFoundException;
import uz.uniSport.uni_sport.mapper.ai.AIMapper;
import uz.uniSport.uni_sport.repository.ai.AIRequestLogRepository;
import uz.uniSport.uni_sport.repository.ai.PromptTemplateRepository;
import uz.uniSport.uni_sport.repository.auth.UserRepository;
import uz.uniSport.uni_sport.service.ai.AIService;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AIServiceImpl implements AIService {

    private final AIRequestLogRepository aiLogRepository;
    private final PromptTemplateRepository templateRepository;
    private final UserRepository userRepository;
    private final AIMapper aiMapper;
    
    private final RestTemplate restTemplate;
    private final StringRedisTemplate redisTemplate;

    @Value("${ai.openai.api-key}")
    private String openAiKey;

    @Value("${ai.openai.url}")
    private String openAiUrl;

    @Value("${ai.gemini.api-key}")
    private String geminiKey;

    @Value("${ai.gemini.url}")
    private String geminiUrl;

    @Override
    @Transactional
    public String generateWorkoutPlan(UUID userId, String promptText) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Foydalanuvchi topilmadi: " + userId));

        // 1. Promptni Xeshlash va Keshdan tekshirish (Token xarajatlarini kamaytirish uchun)
        String promptHash = DigestUtils.md5DigestAsHex(promptText.getBytes(StandardCharsets.UTF_8));
        String cacheKey = "ai:response:" + promptHash;
        String cachedResponse = redisTemplate.opsForValue().get(cacheKey);

        if (cachedResponse != null) {
            log.info("AI javobi keshdan olindi: {}", cacheKey);
            return cachedResponse;
        }

        PromptTemplate template = templateRepository.findByName("WORKOUT_PLAN").orElse(null);
        String finalPrompt = (template != null) ? template.getTemplateText().replace("{{prompt}}", promptText) : promptText;

        long startTime = System.currentTimeMillis();
        String aiResponse = "";

        try {
            // 2. OpenAI ga so'rov yuborish
            aiResponse = callOpenAI(finalPrompt);
        } catch (Exception e) {
            log.error("OpenAI xatosi: {}. Gemini zaxirasiga o'tilmoqda...", e.getMessage());
            try {
                // 3. Fallback: Gemini ga so'rov yuborish
                aiResponse = callGemini(finalPrompt);
            } catch (Exception ex) {
                log.error("Gemini xatosi: {}", ex.getMessage());
                throw new BusinessLogicException("AI xizmati vaqtincha ishlamayapti.");
            }
        }

        long executionTime = System.currentTimeMillis() - startTime;

        // 4. Keshga yozish (24 soat TTL)
        redisTemplate.opsForValue().set(cacheKey, aiResponse, 24, TimeUnit.HOURS);

        // 5. Log yaratish va DB ga saqlash
        AIRequestLog requestLog = new AIRequestLog();
        requestLog.setUser(user);
        requestLog.setPromptTemplate(template);
        requestLog.setRequestPayload(finalPrompt);
        requestLog.setResponsePayload(aiResponse);
        requestLog.setExecutionTimeMs(executionTime);

        aiLogRepository.save(requestLog);

        return aiResponse;
    }

    private String callOpenAI(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAiKey);

        Map<String, Object> requestBody = Map.of(
                "model", "gpt-3.5-turbo",
                "messages", List.of(Map.of("role", "user", "content", prompt))
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        
        // Mock javob, aslini ochish uchun quyidagilarni ishlatish mumkin:
        // ResponseEntity<Map> response = restTemplate.postForEntity(openAiUrl, entity, Map.class);
        // return parseOpenAiResponse(response.getBody());
        
        return "Siz uchun maxsus mashg'ulot rejasi (OpenAI orqali): " + prompt;
    }

    private String callGemini(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        Map<String, Object> requestBody = Map.of(
                "contents", List.of(Map.of("parts", List.of(Map.of("text", prompt))))
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        // String urlWithKey = geminiUrl + "?key=" + geminiKey;
        // ResponseEntity<Map> response = restTemplate.postForEntity(urlWithKey, entity, Map.class);
        // return parseGeminiResponse(response.getBody());
        
        return "Siz uchun maxsus mashg'ulot rejasi (Gemini orqali): " + prompt;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AIRequestLogDto> getUserAILogs(UUID userId) {
        return aiLogRepository.findByUserId(userId).stream()
                .map(aiMapper::toDto)
                .collect(Collectors.toList());
    }
}
