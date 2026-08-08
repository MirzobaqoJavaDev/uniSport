package uz.uniSport.uni_sport.dto.payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentTransactionUpdateDTO {
    private String status;
    private String providerTransactionId;
}
