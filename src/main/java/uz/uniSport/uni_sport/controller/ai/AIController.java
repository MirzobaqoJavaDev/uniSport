package uz.uniSport.uni_sport.controller.ai;

import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.uniSport.uni_sport.dto.ai.AIPlanRequest;
import uz.uniSport.uni_sport.dto.ai.AIRequestLogDto;
import uz.uniSport.uni_sport.service.ai.AIService;

import java.util.List;
import java.util.UUID;

@Tag(name = "Sun'iy Intellekt", description = "AI yordamchisi va neyrotarmoqlar orqali xizmatlar ko'rsatish")
@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AIController {

    private final AIService aiService;

    @PostMapping("/generate-plan")
    public ResponseEntity<String> generateWorkoutPlan(@RequestBody AIPlanRequest request) {
        String plan = aiService.generateWorkoutPlan(request.getUserId(), request.getPromptText());
        return ResponseEntity.ok(plan);
    }

    @GetMapping("/logs/user/{userUuid}")
    public ResponseEntity<List<AIRequestLogDto>> getUserAILogs(@PathVariable UUID userUuid) {
        return ResponseEntity.ok(aiService.getUserAILogs(userUuid));
    }
}
