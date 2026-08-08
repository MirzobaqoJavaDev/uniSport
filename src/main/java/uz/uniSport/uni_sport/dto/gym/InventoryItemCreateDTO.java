package uz.uniSport.uni_sport.dto.gym;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class InventoryItemCreateDTO {
    @NotNull(message = "Facility ID is required")
    private UUID facilityId;

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Total quantity is required")
    @Min(value = 0, message = "Total quantity cannot be negative")
    private Integer totalQuantity;
}
