package uz.uniSport.uni_sport.service.gym;

import uz.uniSport.uni_sport.dto.gym.BookingDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface BookingService {
    BookingDto bookCourt(UUID userId, UUID courtId, LocalDateTime startTime, LocalDateTime endTime);
    List<BookingDto> getUserBookings(UUID userId);
    void cancelBooking(UUID bookingId, UUID userId);
}
