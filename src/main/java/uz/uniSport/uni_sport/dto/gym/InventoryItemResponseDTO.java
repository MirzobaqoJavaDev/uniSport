package uz.uniSport.uni_sport.dto.gym;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class InventoryItemResponseDTO {
    private UUID id;
    private UUID facilityId;
    private String name;
    private Integer totalQuantity;
    private Integer availableQuantity;
    private String status;
}
