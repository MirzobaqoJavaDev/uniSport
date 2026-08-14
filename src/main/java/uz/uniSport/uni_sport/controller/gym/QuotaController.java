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

    @GetMapping("/{uuid}")
    @PreAuthorize("hasAuthority('QUOTA_READ')")
    public ResponseEntity<QuotaResponseDTO> getQuotaById(@PathVariable UUID uuid) {
        return ResponseEntity.ok(service.getQuotaById(uuid));
    }

    @GetMapping("/role/{roleUuid}")
    @PreAuthorize("hasAuthority('QUOTA_READ')")
    public ResponseEntity<QuotaResponseDTO> getQuotaByRoleId(@PathVariable UUID roleUuid) {
        return ResponseEntity.ok(service.getQuotaByRoleId(roleUuid));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('QUOTA_MANAGE')")
    public ResponseEntity<QuotaResponseDTO> createQuota(@Valid @RequestBody QuotaCreateDTO createDTO) {
        return new ResponseEntity<>(service.createQuota(createDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}")
    @PreAuthorize("hasAuthority('QUOTA_MANAGE')")
    public ResponseEntity<QuotaResponseDTO> updateQuota(@PathVariable UUID uuid, @Valid @RequestBody QuotaUpdateDTO updateDTO) {
        return ResponseEntity.ok(service.updateQuota(uuid, updateDTO));
    }

    @DeleteMapping("/{uuid}")
    @PreAuthorize("hasAuthority('QUOTA_MANAGE')")
    public ResponseEntity<Void> deleteQuota(@PathVariable UUID uuid) {
        service.deleteQuota(uuid);
        return ResponseEntity.noContent().build();
    }
}
