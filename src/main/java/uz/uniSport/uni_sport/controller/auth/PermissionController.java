package uz.uniSport.uni_sport.controller.auth;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.uniSport.uni_sport.dto.auth.PermissionCreateDTO;
import uz.uniSport.uni_sport.dto.auth.PermissionResponseDTO;
import uz.uniSport.uni_sport.dto.auth.PermissionUpdateDTO;
import uz.uniSport.uni_sport.service.auth.PermissionService;

import java.util.List;
import java.util.UUID;

@Tag(name = "Huquqlar", description = "Tizimdagi rol va huquqlarni boshqarish")
@RestController
@RequestMapping("/api/v1/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService service;

    @GetMapping
    @PreAuthorize("hasAuthority('PERMISSION_READ')")
    public ResponseEntity<List<PermissionResponseDTO>> getAllPermissions() {
        return ResponseEntity.ok(service.getAllPermissions());
    }

    @GetMapping("/{uuid}")
    @PreAuthorize("hasAuthority('PERMISSION_READ')")
    public ResponseEntity<PermissionResponseDTO> getPermissionById(@PathVariable UUID uuid) {
        return ResponseEntity.ok(service.getPermissionById(uuid));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('PERMISSION_MANAGE')")
    public ResponseEntity<PermissionResponseDTO> createPermission(@Valid @RequestBody PermissionCreateDTO createDTO) {
        return new ResponseEntity<>(service.createPermission(createDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}")
    @PreAuthorize("hasAuthority('PERMISSION_MANAGE')")
    public ResponseEntity<PermissionResponseDTO> updatePermission(@PathVariable UUID uuid, @Valid @RequestBody PermissionUpdateDTO updateDTO) {
        return ResponseEntity.ok(service.updatePermission(uuid, updateDTO));
    }

    @DeleteMapping("/{uuid}")
    @PreAuthorize("hasAuthority('PERMISSION_MANAGE')")
    public ResponseEntity<Void> deletePermission(@PathVariable UUID uuid) {
        service.deletePermission(uuid);
        return ResponseEntity.noContent().build();
    }
}
