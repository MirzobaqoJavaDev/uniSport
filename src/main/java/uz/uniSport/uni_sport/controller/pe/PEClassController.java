package uz.uniSport.uni_sport.controller.pe;

import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.uniSport.uni_sport.dto.pe.PEClassCreateRequest;
import uz.uniSport.uni_sport.dto.pe.PEClassDto;
import uz.uniSport.uni_sport.service.pe.PEClassService;

import java.util.List;
import java.util.UUID;

@Tag(name = "Jismoniy Tarbiya Darslari", description = "Jismoniy tarbiya guruhlari va darslarni boshqarish")
@RestController
@RequestMapping("/api/v1/pe-classes")
@RequiredArgsConstructor
public class PEClassController {

    private final PEClassService peClassService;

    @PostMapping
    public ResponseEntity<PEClassDto> createClass(@RequestBody PEClassCreateRequest request) {
        PEClassDto created = peClassService.createClass(request.getName(), request.getInstructorId());
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PEClassDto>> getAllClasses() {
        return ResponseEntity.ok(peClassService.getAllClasses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PEClassDto> getClassById(@PathVariable UUID id) {
        return ResponseEntity.ok(peClassService.getClassById(id));
    }
}
