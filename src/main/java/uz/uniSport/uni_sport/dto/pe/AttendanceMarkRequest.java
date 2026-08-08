package uz.uniSport.uni_sport.dto.pe;

import lombok.Data;
import java.util.UUID;

@Data
public class AttendanceMarkRequest {
    private UUID userId;
    private UUID scheduleId;
    private String status;
}
