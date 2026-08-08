package uz.uniSport.uni_sport.dto.gym;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.uniSport.uni_sport.dto.auth.UserDto;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Kortni band qilish (Booking) ma'lumotlarini qaytaruvchi DTO.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    private UUID id;
    private UserDto user;
    private CourtDto court;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
}
