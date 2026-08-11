package uz.uniSport.uni_sport.service.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.uniSport.uni_sport.domain.payment.SubscriptionPlan;
import uz.uniSport.uni_sport.dto.payment.SubscriptionPlanCreateDTO;
import uz.uniSport.uni_sport.dto.payment.SubscriptionPlanResponseDTO;
import uz.uniSport.uni_sport.dto.payment.SubscriptionPlanUpdateDTO;
import uz.uniSport.uni_sport.exception.ResourceNotFoundException;
import uz.uniSport.uni_sport.mapper.payment.PaymentMapper;
import uz.uniSport.uni_sport.repository.payment.SubscriptionPlanRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionPlanService {

    private final SubscriptionPlanRepository repository;
    private final PaymentMapper mapper;

    @Transactional(readOnly = true)
    public List<SubscriptionPlanResponseDTO> getAllPlans() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SubscriptionPlanResponseDTO getPlanById(UUID id) {
        SubscriptionPlan plan = repository.findByUuid(id)
                .orElseThrow(() -> new ResourceNotFoundException("SubscriptionPlan not found with id: " + id));
        return mapper.toDto(plan);
    }

    @Transactional
    public SubscriptionPlanResponseDTO createPlan(SubscriptionPlanCreateDTO createDTO) {
        SubscriptionPlan plan = mapper.toEntity(createDTO);
        SubscriptionPlan saved = repository.save(plan);
        return mapper.toDto(saved);
    }

    @Transactional
    public SubscriptionPlanResponseDTO updatePlan(UUID id, SubscriptionPlanUpdateDTO updateDTO) {
        SubscriptionPlan plan = repository.findByUuid(id)
                .orElseThrow(() -> new ResourceNotFoundException("SubscriptionPlan not found with id: " + id));
        mapper.updateEntity(updateDTO, plan);
        SubscriptionPlan saved = repository.save(plan);
        return mapper.toDto(saved);
    }

    @Transactional
    public void deletePlan(UUID id) {
        if (!repository.findByUuid(id).isPresent()) {
            throw new ResourceNotFoundException("SubscriptionPlan not found with id: " + id);
        }
        repository.findByUuid(id).ifPresent(repository::delete);
    }
}
