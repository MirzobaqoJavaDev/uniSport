package uz.uniSport.uni_sport.controller.payment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.uniSport.uni_sport.dto.payment.SubscriptionPlanCreateDTO;
import uz.uniSport.uni_sport.dto.payment.SubscriptionPlanResponseDTO;
import uz.uniSport.uni_sport.dto.payment.SubscriptionPlanUpdateDTO;
import uz.uniSport.uni_sport.service.payment.SubscriptionPlanService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/subscription-plans")
@RequiredArgsConstructor
public class SubscriptionPlanController {

    private final SubscriptionPlanService service;

    @GetMapping
    public ResponseEntity<List<SubscriptionPlanResponseDTO>> getAllPlans() {
        return ResponseEntity.ok(service.getAllPlans());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionPlanResponseDTO> getPlanById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getPlanById(id));
    }

    @PostMapping
    public ResponseEntity<SubscriptionPlanResponseDTO> createPlan(@Valid @RequestBody SubscriptionPlanCreateDTO createDTO) {
        return new ResponseEntity<>(service.createPlan(createDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionPlanResponseDTO> updatePlan(@PathVariable UUID id, @Valid @RequestBody SubscriptionPlanUpdateDTO updateDTO) {
        return ResponseEntity.ok(service.updatePlan(id, updateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable UUID id) {
        service.deletePlan(id);
        return ResponseEntity.noContent().build();
    }
}
