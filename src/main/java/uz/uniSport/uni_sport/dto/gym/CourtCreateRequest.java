package uz.uniSport.uni_sport.dto.gym;

import lombok.Data;
import java.util.UUID;

@Data
public class CourtCreateRequest {
    private UUID facilityId;
    private String name;
}
