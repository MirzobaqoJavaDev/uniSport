package uz.uniSport.uni_sport.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uniSport.uni_sport.domain.auth.Permission;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Tizimdagi huquqlarni (Permission) boshqarish uchun Repository.
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    /**
     * Huquqni UUID bo'yicha qidirish (API so'rovlarida ishlatiladi).
     * @param uuid huquq tashqi identifikatori
     * @return topilgan huquq
     */
    Optional<Permission> findByUuid(UUID uuid);

    List<Permission> findAllByUuidIn(Set<UUID> permissions);

    boolean existsByName(String name);
}
