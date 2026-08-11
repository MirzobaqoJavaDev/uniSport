package uz.uniSport.uni_sport.controller.integration;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.uniSport.uni_sport.dto.integration.DynamicQRCodeCreateDTO;
import uz.uniSport.uni_sport.dto.integration.DynamicQRCodeResponseDTO;
import uz.uniSport.uni_sport.dto.integration.DynamicQRCodeUpdateDTO;
import uz.uniSport.uni_sport.service.integration.DynamicQRCodeService;

import java.util.List;
import java.util.UUID;

@Tag(name = "QR Kodlar", description = "Dinamik QR kodlar yaratish va tekshirish")
@RestController
@RequestMapping("/api/v1/qr-codes")
@RequiredArgsConstructor
public class DynamicQRCodeController {

    private final DynamicQRCodeService service;

    @GetMapping
    public ResponseEntity<List<DynamicQRCodeResponseDTO>> getAllQRCodes() {
        return ResponseEntity.ok(service.getAllQRCodes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DynamicQRCodeResponseDTO> getQRCodeById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getQRCodeById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DynamicQRCodeResponseDTO>> getQRCodesByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(service.getQRCodesByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<DynamicQRCodeResponseDTO> createQRCode(@Valid @RequestBody DynamicQRCodeCreateDTO createDTO) {
        return new ResponseEntity<>(service.createQRCode(createDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DynamicQRCodeResponseDTO> updateQRCode(@PathVariable UUID id, @Valid @RequestBody DynamicQRCodeUpdateDTO updateDTO) {
        return ResponseEntity.ok(service.updateQRCode(id, updateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQRCode(@PathVariable UUID id) {
        service.deleteQRCode(id);
        return ResponseEntity.noContent().build();
    }
}
