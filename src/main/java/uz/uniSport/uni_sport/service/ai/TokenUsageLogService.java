package uz.uniSport.uni_sport.service.ai;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.uniSport.uni_sport.domain.ai.TokenUsageLog;
import uz.uniSport.uni_sport.domain.auth.User;
import uz.uniSport.uni_sport.dto.ai.TokenUsageLogCreateDTO;
import uz.uniSport.uni_sport.dto.ai.TokenUsageLogResponseDTO;
import uz.uniSport.uni_sport.exception.ResourceNotFoundException;
import uz.uniSport.uni_sport.mapper.ai.AIMapper;
import uz.uniSport.uni_sport.repository.ai.TokenUsageLogRepository;
import uz.uniSport.uni_sport.repository.auth.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenUsageLogService {

    private final TokenUsageLogRepository repository;
    private final UserRepository userRepository;
    private final AIMapper mapper;

    @Transactional(readOnly = true)
    public List<TokenUsageLogResponseDTO> getAllLogs() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TokenUsageLogResponseDTO getLogById(UUID id) {
        TokenUsageLog log = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TokenUsageLog not found with id: " + id));
        return mapper.toDto(log);
    }

    @Transactional(readOnly = true)
    public List<TokenUsageLogResponseDTO> getLogsByUserId(UUID userId) {
        return repository.findByUserId(userId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public TokenUsageLogResponseDTO createLog(TokenUsageLogCreateDTO createDTO) {
        User user = userRepository.findById(createDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + createDTO.getUserId()));

        TokenUsageLog log = mapper.toEntity(createDTO);
        log.setUser(user);
        log.setTimestamp(LocalDateTime.now());

        TokenUsageLog saved = repository.save(log);
        return mapper.toDto(saved);
    }

    @Transactional
    public void deleteLog(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("TokenUsageLog not found with id: " + id);
        }
        repository.deleteById(id);
    }
}
