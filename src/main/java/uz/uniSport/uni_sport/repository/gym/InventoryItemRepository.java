package uz.uniSport.uni_sport.repository.gym;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uniSport.uni_sport.domain.gym.InventoryItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Inventar mahsulotlarini (InventoryItem) boshqarish uchun Repository.
 */
@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {

    /**
     * Inventarni UUID bo'yicha qidirish (API so'rovlarida ishlatiladi).
     * @param uuid inventar tashqi identifikatori
     * @return topilgan inventar
     */
    Optional<InventoryItem> findByUuid(UUID uuid);

    /**
     * Ma'lum bir sport inshootiga (Facility) tegishli inventarlarni topish.
     * @param facilityId sport inshooti DB ichki id si
     * @return inventarlar ro'yxati
     */
    List<InventoryItem> findByFacilityUuid(UUID facilityId);
}
