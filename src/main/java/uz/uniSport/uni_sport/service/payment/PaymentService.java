package uz.uniSport.uni_sport.service.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.uniSport.uni_sport.domain.auth.User;
import uz.uniSport.uni_sport.domain.payment.PaymentTransaction;
import uz.uniSport.uni_sport.dto.payment.PaymentTransactionCreateDTO;
import uz.uniSport.uni_sport.dto.payment.PaymentTransactionResponseDTO;
import uz.uniSport.uni_sport.dto.payment.PaymentTransactionUpdateDTO;
import uz.uniSport.uni_sport.exception.BusinessLogicException;
import uz.uniSport.uni_sport.exception.ResourceNotFoundException;
import uz.uniSport.uni_sport.mapper.payment.PaymentMapper;
import uz.uniSport.uni_sport.repository.auth.UserRepository;
import uz.uniSport.uni_sport.repository.payment.PaymentTransactionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentTransactionRepository paymentRepository;
    private final UserRepository userRepository;
    private final PaymentMapper mapper;

    @Transactional(readOnly = true)
    public List<PaymentTransactionResponseDTO> getAllTransactions() {
        return paymentRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PaymentTransactionResponseDTO getTransactionById(UUID id) {
        PaymentTransaction transaction = paymentRepository.findByUuid(id)
                .orElseThrow(() -> new ResourceNotFoundException("PaymentTransaction not found with id: " + id));
        return mapper.toDto(transaction);
    }

    @Transactional
    public PaymentTransactionResponseDTO processPayment(PaymentTransactionCreateDTO createDTO) {
        // Idempotency check
        Optional<PaymentTransaction> existingTransaction = paymentRepository.findByIdempotencyKey(createDTO.getIdempotencyKey());
        if (existingTransaction.isPresent()) {
            return mapper.toDto(existingTransaction.get());
        }

        User user = userRepository.findByUuid(createDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + createDTO.getUserId()));

        PaymentTransaction transaction = mapper.toEntity(createDTO);
        transaction.setUser(user);
        transaction.setStatus("PENDING");
        
        // Simulating external payment gateway interaction
        // Here we just set a mock reference
        transaction.setProviderTransactionId("REF-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

        PaymentTransaction saved = paymentRepository.save(transaction);
        return mapper.toDto(saved);
    }

    @Transactional
    public PaymentTransactionResponseDTO updateTransactionStatus(UUID id, PaymentTransactionUpdateDTO updateDTO) {
        PaymentTransaction transaction = paymentRepository.findByUuid(id)
                .orElseThrow(() -> new ResourceNotFoundException("PaymentTransaction not found with id: " + id));
                
        if ("COMPLETED".equals(transaction.getStatus()) || "FAILED".equals(transaction.getStatus())) {
            throw new BusinessLogicException("Cannot update a completed or failed transaction");
        }

        mapper.updateEntity(updateDTO, transaction);
        PaymentTransaction saved = paymentRepository.save(transaction);
        return mapper.toDto(saved);
    }
}
