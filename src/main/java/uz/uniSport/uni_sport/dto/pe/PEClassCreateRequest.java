package uz.uniSport.uni_sport.dto.pe;

import lombok.Data;
import java.util.UUID;

@Data
public class PEClassCreateRequest {
    private String name;
    private UUID instructorId;
}
