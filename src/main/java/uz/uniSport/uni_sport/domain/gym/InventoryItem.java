package uz.uniSport.uni_sport.domain.gym;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.uniSport.uni_sport.domain.common.BaseEntity;

import java.util.UUID;

/**
 * Sport jihozlari va inventarlarini (Inventory Item) ifodalovchi Entity.
 */
@Getter
@Setter
@Entity
@Table(name = "inventory_items")
public class InventoryItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id", nullable = false)
    private Facility facility;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(name = "total_quantity", nullable = false)
    private Integer totalQuantity;

    @Column(name = "available_quantity", nullable = false)
    private Integer availableQuantity;

    /**
     * Masalan: 'ACTIVE', 'MAINTENANCE', 'RETIRED'
     */
    @Column(nullable = false, length = 50)
    private String status;
}
