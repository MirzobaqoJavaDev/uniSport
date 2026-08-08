package uz.uniSport.uni_sport.service.gym.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.uniSport.uni_sport.domain.gym.Facility;
import uz.uniSport.uni_sport.dto.gym.FacilityDto;
import uz.uniSport.uni_sport.exception.ResourceNotFoundException;
import uz.uniSport.uni_sport.mapper.gym.GymMapper;
import uz.uniSport.uni_sport.repository.gym.FacilityRepository;
import uz.uniSport.uni_sport.service.gym.FacilityService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FacilityServiceImpl implements FacilityService {

    private final FacilityRepository facilityRepository;
    private final GymMapper gymMapper;

    @Override
    @Transactional
    public FacilityDto createFacility(String name, String description) {
        Facility facility = new Facility();
        facility.setName(name);
        facility.setDescription(description);
        Facility saved = facilityRepository.save(facility);
        return gymMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public FacilityDto getFacilityById(UUID id) {
        Facility facility = facilityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sport inshooti topilmadi: " + id));
        return gymMapper.toDto(facility);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FacilityDto> getAllFacilities() {
        return facilityRepository.findAll().stream()
                .map(gymMapper::toDto)
                .collect(Collectors.toList());
    }
}
