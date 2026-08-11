package uz.uniSport.uni_sport.controller.gym;

import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.uniSport.uni_sport.dto.gym.BookingCreateRequest;
import uz.uniSport.uni_sport.dto.gym.BookingDto;
import uz.uniSport.uni_sport.service.gym.BookingService;

import java.util.List;
import java.util.UUID;

@Tag(name = "Band qilish", description = "Sport maydonchalarini band qilish va bronlarni boshqarish")
@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingDto> bookCourt(@RequestBody BookingCreateRequest request) {
        BookingDto booking = bookingService.bookCourt(
                request.getUserId(),
                request.getCourtId(),
                request.getStartTime(),
                request.getEndTime()
        );
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingDto>> getUserBookings(@PathVariable UUID userId) {
        return ResponseEntity.ok(bookingService.getUserBookings(userId));
    }

    @DeleteMapping("/{id}/user/{userId}")
    public ResponseEntity<Void> cancelBooking(@PathVariable UUID id, @PathVariable UUID userId) {
        bookingService.cancelBooking(id, userId);
        return ResponseEntity.noContent().build();
    }
}
