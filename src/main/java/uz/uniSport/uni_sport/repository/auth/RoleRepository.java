package uz.uniSport.uni_sport.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uniSport.uni_sport.domain.auth.Role;

import java.util.Optional;
import java.util.UUID;

/**
 * Tizimdagi rollarni (Role) boshqarish uchun Repository.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Rolni UUID bo'yicha qidirish (API so'rovlarida ishlatiladi).
     * @param uuid rol tashqi identifikatori
     * @return topilgan rol
     */
    Optional<Role> findByUuid(UUID uuid);

    /**
     * Rolni nomi bo'yicha qidirish.
     * @param name rol nomi (masalan, STUDENT)
     * @return topilgan rol
     */
    Optional<Role> findByName(String name);

    boolean existsByName(String name);

}
