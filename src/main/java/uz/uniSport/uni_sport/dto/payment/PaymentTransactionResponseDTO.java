package uz.uniSport.uni_sport.dto.payment;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class PaymentTransactionResponseDTO {
    private UUID id;
    private UUID userId;
    private BigDecimal amount;
    private String currency;
    private String status;
    private String idempotencyKey;
    private String providerTransactionId;
}
