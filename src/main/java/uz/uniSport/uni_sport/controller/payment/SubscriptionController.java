package uz.uniSport.uni_sport.controller.payment;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.uniSport.uni_sport.dto.payment.SubscriptionCreateDTO;
import uz.uniSport.uni_sport.dto.payment.SubscriptionResponseDTO;
import uz.uniSport.uni_sport.dto.payment.SubscriptionUpdateDTO;
import uz.uniSport.uni_sport.service.payment.SubscriptionService;

import java.util.List;
import java.util.UUID;

@Tag(name = "Obunalar", description = "Foydalanuvchilarning sport to'garaklariga obunalarini boshqarish")
@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService service;

    @GetMapping
    public ResponseEntity<List<SubscriptionResponseDTO>> getAllSubscriptions() {
        return ResponseEntity.ok(service.getAllSubscriptions());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SubscriptionResponseDTO>> getSubscriptionsByUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(service.getSubscriptionsByUser(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionResponseDTO> getSubscriptionById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getSubscriptionById(id));
    }

    @PostMapping
    public ResponseEntity<SubscriptionResponseDTO> createSubscription(@Valid @RequestBody SubscriptionCreateDTO createDTO) {
        return new ResponseEntity<>(service.createSubscription(createDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionResponseDTO> updateSubscription(@PathVariable UUID id, @Valid @RequestBody SubscriptionUpdateDTO updateDTO) {
        return ResponseEntity.ok(service.updateSubscription(id, updateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable UUID id) {
        service.deleteSubscription(id);
        return ResponseEntity.noContent().build();
    }
}
