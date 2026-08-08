package uz.uniSport.uni_sport.dto.payment;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class SubscriptionResponseDTO {
    private UUID id;
    private UUID userId;
    private SubscriptionPlanResponseDTO subscriptionPlan;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
}
