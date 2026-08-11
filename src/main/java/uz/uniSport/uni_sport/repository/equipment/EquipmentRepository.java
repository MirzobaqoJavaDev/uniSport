package uz.uniSport.uni_sport.repository.equipment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uniSport.uni_sport.domain.equipment.Equipment;

import java.util.Optional;
import java.util.UUID;

/**
 * Sport inventarlarini (Equipment) boshqarish uchun Repository.
 */
@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

    Optional<Equipment> findByUuid(UUID uuid);
}
