package uz.uniSport.uni_sport.service.gym;

import uz.uniSport.uni_sport.dto.gym.FacilityDto;

import java.util.List;
import java.util.UUID;

public interface FacilityService {
    FacilityDto createFacility(String name, String description);
    FacilityDto getFacilityById(UUID id);
    List<FacilityDto> getAllFacilities();
}
