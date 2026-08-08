package uz.uniSport.uni_sport.service.pe;

import uz.uniSport.uni_sport.dto.pe.PEClassDto;

import java.util.List;
import java.util.UUID;

public interface PEClassService {
    PEClassDto createClass(String name, UUID instructorId);
    PEClassDto getClassById(UUID id);
    List<PEClassDto> getAllClasses();
}
