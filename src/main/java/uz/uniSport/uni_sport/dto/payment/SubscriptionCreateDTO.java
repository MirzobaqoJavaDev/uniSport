package uz.uniSport.uni_sport.dto.payment;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SubscriptionCreateDTO {
    @NotNull(message = "User ID is required")
    private UUID userId;

    @NotNull(message = "Subscription Plan ID is required")
    private UUID subscriptionPlanId;
}
