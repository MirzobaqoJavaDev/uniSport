package uz.uniSport.uni_sport.controller.payment;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Obuna Rejalari", description = "Pullik obuna tariflari va rejalarini boshqarish")
@RestController
@RequestMapping("/api/v1/subscription-plans")
@RequiredArgsConstructor
public class SubscriptionPlanController {

    private final SubscriptionPlanService service;

    @GetMapping
    public ResponseEntity<List<SubscriptionPlanResponseDTO>> getAllPlans() {
        return ResponseEntity.ok(service.getAllPlans());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<SubscriptionPlanResponseDTO> getPlanById(@PathVariable UUID uuid) {
        return ResponseEntity.ok(service.getPlanById(uuid));
    }

    @PostMapping
    public ResponseEntity<SubscriptionPlanResponseDTO> createPlan(@Valid @RequestBody SubscriptionPlanCreateDTO createDTO) {
        return new ResponseEntity<>(service.createPlan(createDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<SubscriptionPlanResponseDTO> updatePlan(@PathVariable UUID uuid, @Valid @RequestBody SubscriptionPlanUpdateDTO updateDTO) {
        return ResponseEntity.ok(service.updatePlan(uuid, updateDTO));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deletePlan(@PathVariable UUID uuid) {
        service.deletePlan(uuid);
        return ResponseEntity.noContent().build();
    }
}
