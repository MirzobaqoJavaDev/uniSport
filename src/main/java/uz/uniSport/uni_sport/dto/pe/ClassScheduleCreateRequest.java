package uz.uniSport.uni_sport.dto.pe;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ClassScheduleCreateRequest {
    private UUID peClassId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
