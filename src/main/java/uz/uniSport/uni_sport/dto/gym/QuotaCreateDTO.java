package uz.uniSport.uni_sport.dto.gym;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class QuotaCreateDTO {
    @NotNull(message = "Role ID is required")
    private UUID roleId;

    @NotNull(message = "Max active bookings is required")
    @Min(value = 1, message = "Max active bookings must be at least 1")
    private Integer maxActiveBookings;

    @Min(value = 1, message = "Max bookings per week must be at least 1")
    private Integer maxBookingsPerWeek;
}
