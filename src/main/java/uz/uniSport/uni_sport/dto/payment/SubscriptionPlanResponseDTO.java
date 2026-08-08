package uz.uniSport.uni_sport.dto.payment;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class SubscriptionPlanResponseDTO {
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer durationInDays;
}
