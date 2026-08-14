package uz.uniSport.uni_sport.controller.ai;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.uniSport.uni_sport.dto.ai.TokenUsageLogCreateDTO;
import uz.uniSport.uni_sport.dto.ai.TokenUsageLogResponseDTO;
import uz.uniSport.uni_sport.service.ai.TokenUsageLogService;

import java.util.List;
import java.util.UUID;

@Tag(name = "AI Tokenlar Sarfi", description = "Sun'iy intellekt xizmatlari uchun sarflangan tokenlar hisobini yuritish")
@RestController
@RequestMapping("/api/v1/token-usage-logs")
@RequiredArgsConstructor
public class TokenUsageLogController {

    private final TokenUsageLogService service;

    @GetMapping
    public ResponseEntity<List<TokenUsageLogResponseDTO>> getAllLogs() {
        return ResponseEntity.ok(service.getAllLogs());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<TokenUsageLogResponseDTO> getLogById(@PathVariable UUID uuid) {
        return ResponseEntity.ok(service.getLogById(uuid));
    }

    @GetMapping("/user/{userUuid}")
    public ResponseEntity<List<TokenUsageLogResponseDTO>> getLogsByUserId(@PathVariable UUID userUuid) {
        return ResponseEntity.ok(service.getLogsByUserId(userUuid));
    }

    @PostMapping
    public ResponseEntity<TokenUsageLogResponseDTO> createLog(@Valid @RequestBody TokenUsageLogCreateDTO createDTO) {
        return new ResponseEntity<>(service.createLog(createDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteLog(@PathVariable UUID uuid) {
        service.deleteLog(uuid);
        return ResponseEntity.noContent().build();
    }
}
