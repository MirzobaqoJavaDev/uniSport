package uz.uniSport.uni_sport.controller.payment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.uniSport.uni_sport.dto.payment.PaymentTransactionCreateDTO;
import uz.uniSport.uni_sport.dto.payment.PaymentTransactionResponseDTO;
import uz.uniSport.uni_sport.dto.payment.PaymentTransactionUpdateDTO;
import uz.uniSport.uni_sport.service.payment.PaymentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;

    @GetMapping
    public ResponseEntity<List<PaymentTransactionResponseDTO>> getAllTransactions() {
        return ResponseEntity.ok(service.getAllTransactions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentTransactionResponseDTO> getTransactionById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getTransactionById(id));
    }

    @PostMapping
    public ResponseEntity<PaymentTransactionResponseDTO> processPayment(@Valid @RequestBody PaymentTransactionCreateDTO createDTO) {
        return new ResponseEntity<>(service.processPayment(createDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<PaymentTransactionResponseDTO> updateTransactionStatus(
            @PathVariable UUID id, 
            @Valid @RequestBody PaymentTransactionUpdateDTO updateDTO) {
        return ResponseEntity.ok(service.updateTransactionStatus(id, updateDTO));
    }
}
