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
public interface PromptVersionRepository extends JpaRepository<PromptVersion, Long> {

    /**
     * Prompt versiyasini UUID bo'yicha qidirish.
     */
    Optional<PromptVersion> findByUuid(UUID uuid);

    /**
     * Ma'lum bir shablonga tegishli faol (active) versiyani qidirish.
     * @param templateId shablon DB ichki id si
     * @param isActive faollik holati (odatda true)
     * @return faol versiya
     */
    Optional<PromptVersion> findByTemplateIdAndIsActive(Long templateId, boolean isActive);
}
