package uz.uniSport.uni_sport.service.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.uniSport.uni_sport.domain.auth.User;
import uz.uniSport.uni_sport.domain.payment.Subscription;
import uz.uniSport.uni_sport.domain.payment.SubscriptionPlan;
import uz.uniSport.uni_sport.dto.payment.SubscriptionCreateDTO;
import uz.uniSport.uni_sport.dto.payment.SubscriptionResponseDTO;
import uz.uniSport.uni_sport.dto.payment.SubscriptionUpdateDTO;
import uz.uniSport.uni_sport.exception.ResourceNotFoundException;
import uz.uniSport.uni_sport.mapper.payment.PaymentMapper;
import uz.uniSport.uni_sport.repository.auth.UserRepository;
import uz.uniSport.uni_sport.repository.payment.SubscriptionPlanRepository;
import uz.uniSport.uni_sport.repository.payment.SubscriptionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionPlanRepository subscriptionPlanRepository;
    private final UserRepository userRepository;
    private final PaymentMapper mapper;

    @Transactional(readOnly = true)
    public List<SubscriptionResponseDTO> getAllSubscriptions() {
        return subscriptionRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SubscriptionResponseDTO> getSubscriptionsByUser(UUID userId) {
        return subscriptionRepository.findByUserUuid(userId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SubscriptionResponseDTO getSubscriptionById(UUID id) {
        Subscription subscription = subscriptionRepository.findByUuid(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found with id: " + id));
        return mapper.toDto(subscription);
    }

    @Transactional
    public SubscriptionResponseDTO createSubscription(SubscriptionCreateDTO createDTO) {
        User user = userRepository.findByUuid(createDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + createDTO.getUserId()));
                
        SubscriptionPlan plan = subscriptionPlanRepository.findByUuid(createDTO.getSubscriptionPlanId())
                .orElseThrow(() -> new ResourceNotFoundException("SubscriptionPlan not found with id: " + createDTO.getSubscriptionPlanId()));

        Subscription subscription = mapper.toEntity(createDTO);
        subscription.setUser(user);
        subscription.setPlan(plan);
        subscription.setStartDate(LocalDateTime.now());
        subscription.setEndDate(LocalDateTime.now().plusDays(plan.getDurationInDays()));
        subscription.setStatus("ACTIVE"); // Initial status
        
        Subscription saved = subscriptionRepository.save(subscription);
        return mapper.toDto(saved);
    }

    @Transactional
    public SubscriptionResponseDTO updateSubscription(UUID id, SubscriptionUpdateDTO updateDTO) {
        Subscription subscription = subscriptionRepository.findByUuid(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found with id: " + id));
        mapper.updateEntity(updateDTO, subscription);
        Subscription saved = subscriptionRepository.save(subscription);
        return mapper.toDto(saved);
    }

    @Transactional
    public void deleteSubscription(UUID id) {
        if (!subscriptionRepository.findByUuid(id).isPresent()) {
            throw new ResourceNotFoundException("Subscription not found with id: " + id);
        }
        subscriptionRepository.findByUuid(id).ifPresent(subscriptionRepository::delete);
    }
}
