package uz.uniSport.uni_sport.dto.gym;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class BookingCreateRequest {
    private UUID userId;
    private UUID courtId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
