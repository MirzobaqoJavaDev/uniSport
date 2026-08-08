package uz.uniSport.uni_sport.dto.gym;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuotaUpdateDTO {
    @Min(value = 1, message = "Max active bookings must be at least 1")
    private Integer maxActiveBookings;

    @Min(value = 1, message = "Max bookings per week must be at least 1")
    private Integer maxBookingsPerWeek;
}
