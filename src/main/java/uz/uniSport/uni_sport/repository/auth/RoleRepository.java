package uz.uniSport.uni_sport.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uniSport.uni_sport.domain.auth.Role;

import java.util.Optional;

/**
 * Tizimdagi rollarni (Role) boshqarish uchun Repository.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * Rolni nomi bo'yicha qidirish.
     * @param name rol nomi (masalan, STUDENT)
     * @return topilgan rol
     */
    Optional<Role> findByName(String name);
}
