package uz.uniSport.uni_sport.controller.gym;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.uniSport.uni_sport.dto.gym.QuotaCreateDTO;
import uz.uniSport.uni_sport.dto.gym.QuotaResponseDTO;
import uz.uniSport.uni_sport.dto.gym.QuotaUpdateDTO;
import uz.uniSport.uni_sport.service.gym.QuotaService;

import java.util.List;
import java.util.UUID;

@Tag(name = "Kvotalar", description = "Zal yoki darslarga qatnashuvchilar soni chegaralarini boshqarish")
@RestController
@RequestMapping("/api/v1/quotas")
@RequiredArgsConstructor
public class QuotaController {

    private final QuotaService service;

    @GetMapping
    @PreAuthorize("hasAuthority('QUOTA_READ')")
    public ResponseEntity<List<QuotaResponseDTO>> getAllQuotas() {
        return ResponseEntity.ok(service.getAllQuotas());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('QUOTA_READ')")
    public ResponseEntity<QuotaResponseDTO> getQuotaById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getQuotaById(id));
    }

    @GetMapping("/role/{roleId}")
    @PreAuthorize("hasAuthority('QUOTA_READ')")
    public ResponseEntity<QuotaResponseDTO> getQuotaByRoleId(@PathVariable UUID roleId) {
        return ResponseEntity.ok(service.getQuotaByRoleId(roleId));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('QUOTA_MANAGE')")
    public ResponseEntity<QuotaResponseDTO> createQuota(@Valid @RequestBody QuotaCreateDTO createDTO) {
        return new ResponseEntity<>(service.createQuota(createDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('QUOTA_MANAGE')")
    public ResponseEntity<QuotaResponseDTO> updateQuota(@PathVariable UUID id, @Valid @RequestBody QuotaUpdateDTO updateDTO) {
        return ResponseEntity.ok(service.updateQuota(id, updateDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('QUOTA_MANAGE')")
    public ResponseEntity<Void> deleteQuota(@PathVariable UUID id) {
        service.deleteQuota(id);
        return ResponseEntity.noContent().build();
    }
}
