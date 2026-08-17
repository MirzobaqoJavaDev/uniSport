package uz.uniSport.uni_sport.controller.loop;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "loop", description = "Dastur o`chib qolmasligi uchun api")
@RestController
@RequestMapping("/api/v1/loop")
@RequiredArgsConstructor
public class Loop {
    @GetMapping
    public ResponseEntity<String> loop() {
        return ResponseEntity.ok("loop");
    }
}
