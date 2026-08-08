package uz.uniSport.uni_sport.service.gym.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.uniSport.uni_sport.domain.gym.Court;
import uz.uniSport.uni_sport.domain.gym.Facility;
import uz.uniSport.uni_sport.dto.gym.CourtDto;
import uz.uniSport.uni_sport.exception.ResourceNotFoundException;
import uz.uniSport.uni_sport.mapper.gym.GymMapper;
import uz.uniSport.uni_sport.repository.gym.CourtRepository;
import uz.uniSport.uni_sport.repository.gym.FacilityRepository;
import uz.uniSport.uni_sport.service.gym.CourtService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourtServiceImpl implements CourtService {

    private final CourtRepository courtRepository;
    private final FacilityRepository facilityRepository;
    private final GymMapper gymMapper;

    @Override
    @Transactional
    public CourtDto createCourt(UUID facilityId, String name) {
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new ResourceNotFoundException("Sport inshooti topilmadi: " + facilityId));

        Court court = new Court();
        court.setName(name);
        court.setFacility(facility);
        
        Court saved = courtRepository.save(court);
        return gymMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourtDto> getCourtsByFacility(UUID facilityId) {
        return courtRepository.findByFacilityId(facilityId).stream()
                .map(gymMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CourtDto getCourtById(UUID id) {
        Court court = courtRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kort topilmadi: " + id));
        return gymMapper.toDto(court);
    }
}
