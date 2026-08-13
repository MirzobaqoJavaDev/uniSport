package uz.uniSport.uni_sport.controller.gym;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.uniSport.uni_sport.dto.gym.InventoryItemCreateDTO;
import uz.uniSport.uni_sport.dto.gym.InventoryItemResponseDTO;
import uz.uniSport.uni_sport.dto.gym.InventoryItemUpdateDTO;
import uz.uniSport.uni_sport.service.gym.InventoryItemService;

import java.util.List;
import java.util.UUID;

@Tag(name = "Invertar", description = "Zaldagi jihozlar va sport ashyolarini hisobga olish")
@RestController
@RequestMapping("/api/v1/inventory-items")
@RequiredArgsConstructor
public class InventoryItemController {

    private final InventoryItemService service;

    @GetMapping
    @PreAuthorize("hasAuthority('EQUIPMENT_READ')")
    public ResponseEntity<List<InventoryItemResponseDTO>> getAllInventoryItems() {
        return ResponseEntity.ok(service.getAllInventoryItems());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('EQUIPMENT_READ')")
    public ResponseEntity<InventoryItemResponseDTO> getInventoryItemById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getInventoryItemById(id));
    }

    @GetMapping("/facility/{facilityId}")
    @PreAuthorize("hasAuthority('EQUIPMENT_READ')")
    public ResponseEntity<List<InventoryItemResponseDTO>> getInventoryItemsByFacilityId(@PathVariable UUID facilityId) {
        return ResponseEntity.ok(service.getInventoryItemsByFacilityId(facilityId));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('EQUIPMENT_CREATE') or hasAuthority('EQUIPMENT_MANAGE')")
    public ResponseEntity<InventoryItemResponseDTO> createInventoryItem(@Valid @RequestBody InventoryItemCreateDTO createDTO) {
        return new ResponseEntity<>(service.createInventoryItem(createDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EQUIPMENT_UPDATE') or hasAuthority('EQUIPMENT_MANAGE')")
    public ResponseEntity<InventoryItemResponseDTO> updateInventoryItem(@PathVariable UUID id, @Valid @RequestBody InventoryItemUpdateDTO updateDTO) {
        return ResponseEntity.ok(service.updateInventoryItem(id, updateDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('EQUIPMENT_DELETE') or hasAuthority('EQUIPMENT_MANAGE')")
    public ResponseEntity<Void> deleteInventoryItem(@PathVariable UUID id) {
        service.deleteInventoryItem(id);
        return ResponseEntity.noContent().build();
    }
}
