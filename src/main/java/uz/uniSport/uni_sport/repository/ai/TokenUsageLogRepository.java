package uz.uniSport.uni_sport.repository.ai;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uniSport.uni_sport.domain.ai.TokenUsageLog;

import java.util.List;
import java.util.UUID;

@Repository
public interface TokenUsageLogRepository extends JpaRepository<TokenUsageLog, UUID> {
    List<TokenUsageLog> findByUserId(UUID userId);
}
