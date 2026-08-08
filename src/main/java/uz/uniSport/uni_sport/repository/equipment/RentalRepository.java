package uz.uniSport.uni_sport.repository.equipment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uniSport.uni_sport.domain.equipment.Rental;

import java.util.List;
import java.util.UUID;

/**
 * Inventarlarni ijaraga berish jarayonlarini (Rental) boshqarish uchun Repository.
 */
@Repository
public interface RentalRepository extends JpaRepository<Rental, UUID> {
    /**
     * Foydalanuvchining ijaraga olgan barcha inventarlari tarixi.
     * @param userId foydalanuvchi ID'si
     * @return ijaralar ro'yxati
     */
    List<Rental> findByUserId(UUID userId);
}
