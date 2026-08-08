package uz.uniSport.uni_sport.dto.equipment;

import lombok.Data;
import java.util.UUID;

@Data
public class RentRequest {
    private UUID userId;
    private UUID equipmentId;
}
