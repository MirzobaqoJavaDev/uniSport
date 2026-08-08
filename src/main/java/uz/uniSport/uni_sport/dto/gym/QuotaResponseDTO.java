package uz.uniSport.uni_sport.dto.gym;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class QuotaResponseDTO {
    private UUID id;
    private Long roleId;
    private Integer maxActiveBookings;
    private Integer maxBookingsPerWeek;
    private Integer version;
}
