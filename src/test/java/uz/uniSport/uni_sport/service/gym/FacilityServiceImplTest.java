package uz.uniSport.uni_sport.service.gym;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uz.uniSport.uni_sport.domain.gym.Facility;
import uz.uniSport.uni_sport.dto.gym.FacilityDto;
import uz.uniSport.uni_sport.exception.ResourceNotFoundException;
import uz.uniSport.uni_sport.mapper.gym.GymMapper;
import uz.uniSport.uni_sport.repository.gym.FacilityRepository;
import uz.uniSport.uni_sport.service.gym.impl.FacilityServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacilityServiceImplTest {

    @Mock
    private FacilityRepository facilityRepository;

    @Mock
    private GymMapper gymMapper;

    @InjectMocks
    private FacilityServiceImpl facilityService;

    private Facility facility;
    private FacilityDto facilityDto;
    private UUID facilityId;

    @BeforeEach
    void setUp() {
        facilityId = UUID.randomUUID();
        
        facility = new Facility();
        facility.setUuid(facilityId);
        facility.setName("Asosiy Sport Zali");
        facility.setDescription("Katta sport majmuasi");

        facilityDto = new FacilityDto();
        facilityDto.setId(facilityId);
        // facilityId
        facilityDto.setName("Asosiy Sport Zali");
        facilityDto.setDescription("Katta sport majmuasi");
    }

    @Test
    void createFacility_Success() {
        // Arrange
        when(facilityRepository.save(any(Facility.class))).thenReturn(facility);
        when(gymMapper.toDto(facility)).thenReturn(facilityDto);

        // Act
        FacilityDto result = facilityService.createFacility("Asosiy Sport Zali", "Katta sport majmuasi");

        // Assert
        assertNotNull(result);
        assertEquals("Asosiy Sport Zali", result.getName());
        verify(facilityRepository, times(1)).save(any(Facility.class));
    }

    @Test
    void getFacilityById_Success() {
        // Arrange
        when(facilityRepository.findByUuid(facilityId)).thenReturn(Optional.of(facility));
        when(gymMapper.toDto(facility)).thenReturn(facilityDto);

        // Act
        FacilityDto result = facilityService.getFacilityById(facilityId);

        // Assert
        assertNotNull(result);
        assertEquals(facilityId, result.getId());
    }

    @Test
    void getFacilityById_NotFound_ThrowsException() {
        // Arrange
        UUID wrongId = UUID.randomUUID();
        when(facilityRepository.findByUuid(wrongId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> facilityService.getFacilityById(wrongId));
    }

    @Test
    void getAllFacilities_Success() {
        // Arrange
        when(facilityRepository.findAll()).thenReturn(List.of(facility));
        when(gymMapper.toDto(facility)).thenReturn(facilityDto);

        // Act
        List<FacilityDto> results = facilityService.getAllFacilities();

        // Assert
        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals("Asosiy Sport Zali", results.get(0).getName());
    }
}
