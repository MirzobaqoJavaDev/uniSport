package uz.uniSport.uni_sport.dto.payment;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SubscriptionPlanUpdateDTO {
    private String name;
    private String description;
    @Min(value = 0, message = "Price cannot be negative")
    private BigDecimal price;
    @Min(value = 1, message = "Duration must be at least 1 day")
    private Integer durationInDays;
}
