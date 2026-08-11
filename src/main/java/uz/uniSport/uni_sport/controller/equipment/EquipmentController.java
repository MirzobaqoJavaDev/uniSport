package uz.uniSport.uni_sport.controller.equipment;

import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.uniSport.uni_sport.dto.equipment.EquipmentDto;
import uz.uniSport.uni_sport.service.equipment.EquipmentService;

import java.util.List;
import java.util.UUID;

@Tag(name = "Uskunalar", description = "Zaldagi jihozlar va trenajyorlarni ro'yxatga olish")
@RestController
@RequestMapping("/api/v1/equipments")
@RequiredArgsConstructor
public class EquipmentController {

    private final EquipmentService equipmentService;

    @PostMapping
    public ResponseEntity<EquipmentDto> createEquipment(@RequestParam String name, @RequestParam Integer totalQuantity) {
        EquipmentDto created = equipmentService.createEquipment(name, totalQuantity);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EquipmentDto>> getAllEquipment() {
        return ResponseEntity.ok(equipmentService.getAllEquipment());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipmentDto> getEquipmentById(@PathVariable UUID id) {
        return ResponseEntity.ok(equipmentService.getEquipmentById(id));
    }
}
