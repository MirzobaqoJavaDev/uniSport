package uz.uniSport.uni_sport.controller.equipment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.uniSport.uni_sport.dto.equipment.RentRequest;
import uz.uniSport.uni_sport.dto.equipment.RentalDto;
import uz.uniSport.uni_sport.service.equipment.RentalService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;

    @PostMapping
    public ResponseEntity<RentalDto> rentEquipment(@RequestBody RentRequest request) {
        RentalDto rental = rentalService.rentEquipment(request.getUserId(), request.getEquipmentId());
        return new ResponseEntity<>(rental, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<RentalDto> returnEquipment(@PathVariable UUID id) {
        return ResponseEntity.ok(rentalService.returnEquipment(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RentalDto>> getUserRentals(@PathVariable UUID userId) {
        return ResponseEntity.ok(rentalService.getUserRentals(userId));
    }
}
