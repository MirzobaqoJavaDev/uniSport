package uz.uniSport.uni_sport.dto.gym;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryItemUpdateDTO {
    private String name;

    @Min(value = 0, message = "Total quantity cannot be negative")
    private Integer totalQuantity;

    private String status;
}
