package uz.uniSport.uni_sport.repository.ai;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uniSport.uni_sport.domain.ai.PromptVersion;

import java.util.Optional;
import java.util.UUID;

/**
 * AI prompt versiyalarini (PromptVersion) boshqarish uchun Repository.
 */
@Repository
public interface PromptVersionRepository extends JpaRepository<PromptVersion, UUID> {
    /**
     * Ma'lum bir shablonga tegishli faol (active) versiyani qidirish.
     * @param templateId shablon ID'si
     * @param isActive faollik holati (odatda true)
     * @return faol versiya
     */
    Optional<PromptVersion> findByTemplateIdAndIsActive(UUID templateId, boolean isActive);
}
