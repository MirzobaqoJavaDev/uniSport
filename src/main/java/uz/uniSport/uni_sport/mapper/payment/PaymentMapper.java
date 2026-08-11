package uz.uniSport.uni_sport.mapper.payment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import uz.uniSport.uni_sport.domain.payment.PaymentTransaction;
import uz.uniSport.uni_sport.domain.payment.Subscription;
import uz.uniSport.uni_sport.domain.payment.SubscriptionPlan;
import uz.uniSport.uni_sport.dto.payment.PaymentTransactionCreateDTO;
import uz.uniSport.uni_sport.dto.payment.PaymentTransactionResponseDTO;
import uz.uniSport.uni_sport.dto.payment.PaymentTransactionUpdateDTO;
import uz.uniSport.uni_sport.dto.payment.SubscriptionCreateDTO;
import uz.uniSport.uni_sport.dto.payment.SubscriptionResponseDTO;
import uz.uniSport.uni_sport.dto.payment.SubscriptionUpdateDTO;
import uz.uniSport.uni_sport.dto.payment.SubscriptionPlanCreateDTO;
import uz.uniSport.uni_sport.dto.payment.SubscriptionPlanResponseDTO;
import uz.uniSport.uni_sport.dto.payment.SubscriptionPlanUpdateDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMapper {
    
    @Mapping(source = "uuid", target = "id")
    SubscriptionPlanResponseDTO toDto(SubscriptionPlan plan);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    SubscriptionPlan toEntity(SubscriptionPlanCreateDTO dto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    void updateEntity(SubscriptionPlanUpdateDTO dto, @MappingTarget SubscriptionPlan plan);

    @Mapping(source = "uuid", target = "id")
    @Mapping(source = "user.uuid", target = "userId")
    @Mapping(source = "plan", target = "subscriptionPlan")
    SubscriptionResponseDTO toDto(Subscription sub);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "plan", ignore = true)
    @Mapping(target = "startDate", ignore = true)
    @Mapping(target = "endDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    Subscription toEntity(SubscriptionCreateDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    void updateEntity(SubscriptionUpdateDTO dto, @MappingTarget Subscription sub);

    @Mapping(source = "uuid", target = "id")
    @Mapping(source = "user.uuid", target = "userId")
    PaymentTransactionResponseDTO toDto(PaymentTransaction transaction);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "providerTransactionId", ignore = true)
    PaymentTransaction toEntity(PaymentTransactionCreateDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    void updateEntity(PaymentTransactionUpdateDTO dto, @MappingTarget PaymentTransaction transaction);
}
