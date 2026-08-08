package uz.uniSport.uni_sport.service.gym;

import uz.uniSport.uni_sport.dto.gym.CourtDto;

import java.util.List;
import java.util.UUID;

public interface CourtService {
    CourtDto createCourt(UUID facilityId, String name);
    List<CourtDto> getCourtsByFacility(UUID facilityId);
    CourtDto getCourtById(UUID id);
}
