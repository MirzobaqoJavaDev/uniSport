package uz.uniSport.uni_sport.service.ai;

import uz.uniSport.uni_sport.dto.ai.AIRequestLogDto;

import java.util.List;
import java.util.UUID;

public interface AIService {
    // Kelajakda OpenAI/Gemini ga ulanib haqiqiy javob qaytaradi.
    String generateWorkoutPlan(UUID userId, String promptText);
    List<AIRequestLogDto> getUserAILogs(UUID userId);
}
