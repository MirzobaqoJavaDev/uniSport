package uz.uniSport.uni_sport.repository.ai;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uniSport.uni_sport.domain.ai.PromptTemplate;

import java.util.Optional;
import java.util.UUID;

/**
 * AI prompt shablonlarini (PromptTemplate) boshqarish uchun Repository.
 */
@Repository
public interface PromptTemplateRepository extends JpaRepository<PromptTemplate, UUID> {
    /**
     * Shablonni nomi orqali qidirish.
     * @param name shablon nomi (masalan, WORKOUT_PLAN)
     * @return topilgan shablon
     */
    Optional<PromptTemplate> findByName(String name);
}
