package uz.uniSport.uni_sport.controller.gym;

import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.uniSport.uni_sport.dto.gym.CourtCreateRequest;
import uz.uniSport.uni_sport.dto.gym.CourtDto;
import uz.uniSport.uni_sport.service.gym.CourtService;

import java.util.List;
import java.util.UUID;

@Tag(name = "Sport Kortlari", description = "Stadion va sport maydonchalarini boshqarish")
@RestController
@RequestMapping("/api/v1/courts")
@RequiredArgsConstructor
public class CourtController {

    private final CourtService courtService;

    @PostMapping
    public ResponseEntity<CourtDto> createCourt(@RequestBody CourtCreateRequest request) {
        CourtDto created = courtService.createCourt(request.getFacilityId(), request.getName());
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/facility/{facilityId}")
    public ResponseEntity<List<CourtDto>> getCourtsByFacility(@PathVariable UUID facilityId) {
        return ResponseEntity.ok(courtService.getCourtsByFacility(facilityId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourtDto> getCourtById(@PathVariable UUID id) {
        return ResponseEntity.ok(courtService.getCourtById(id));
    }
}
