package uz.uniSport.uni_sport.domain.gym;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.uniSport.uni_sport.domain.common.BaseEntity;

/**
 * Sport jihozlari va inventarlarini (Inventory Item) ifodalovchi Entity.
 */
@Getter
@Setter
@Entity
@Table(name = "inventory_items")
public class InventoryItem extends BaseEntity {

    /**
     * Ushbu inventar joylashgan sport inshooti (Facility).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id", nullable = false)
    private Facility facility;

    /**
     * Inventar nomlanishi (masalan, 'Tennis to'pi', 'Basketbol to'pi').
     */
    @Column(nullable = false, length = 150)
    private String name;

    /**
     * Umumiy soni.
     */
    @Column(name = "total_quantity", nullable = false)
    private Integer totalQuantity;

    /**
     * Hozir foydalanish uchun mavjud soni.
     */
    @Column(name = "available_quantity", nullable = false)
    private Integer availableQuantity;

    /**
     * Inventar holati: 'ACTIVE', 'MAINTENANCE', 'RETIRED'.
     */
    @Column(nullable = false, length = 50)
    private String status;
}
