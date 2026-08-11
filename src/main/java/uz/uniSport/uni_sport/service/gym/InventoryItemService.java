package uz.uniSport.uni_sport.service.gym;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.uniSport.uni_sport.domain.gym.Facility;
import uz.uniSport.uni_sport.domain.gym.InventoryItem;
import uz.uniSport.uni_sport.dto.gym.InventoryItemCreateDTO;
import uz.uniSport.uni_sport.dto.gym.InventoryItemResponseDTO;
import uz.uniSport.uni_sport.dto.gym.InventoryItemUpdateDTO;
import uz.uniSport.uni_sport.exception.ResourceNotFoundException;
import uz.uniSport.uni_sport.mapper.gym.GymMapper;
import uz.uniSport.uni_sport.repository.gym.FacilityRepository;
import uz.uniSport.uni_sport.repository.gym.InventoryItemRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryItemService {

    private final InventoryItemRepository repository;
    private final FacilityRepository facilityRepository;
    private final GymMapper mapper;

    @Transactional(readOnly = true)
    public List<InventoryItemResponseDTO> getAllInventoryItems() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public InventoryItemResponseDTO getInventoryItemById(UUID id) {
        InventoryItem item = repository.findByUuid(id)
                .orElseThrow(() -> new ResourceNotFoundException("InventoryItem not found with id: " + id));
        return mapper.toDto(item);
    }

    @Transactional(readOnly = true)
    public List<InventoryItemResponseDTO> getInventoryItemsByFacilityId(UUID facilityId) {
        return repository.findByFacilityUuid(facilityId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public InventoryItemResponseDTO createInventoryItem(InventoryItemCreateDTO createDTO) {
        Facility facility = facilityRepository.findByUuid(createDTO.getFacilityId())
                .orElseThrow(() -> new ResourceNotFoundException("Facility not found with id: " + createDTO.getFacilityId()));

        InventoryItem item = mapper.toEntity(createDTO);
        item.setFacility(facility);
        item.setAvailableQuantity(createDTO.getTotalQuantity()); // Available quantity starts as total quantity
        item.setStatus("ACTIVE");
        
        InventoryItem saved = repository.save(item);
        return mapper.toDto(saved);
    }

    @Transactional
    public InventoryItemResponseDTO updateInventoryItem(UUID id, InventoryItemUpdateDTO updateDTO) {
        InventoryItem item = repository.findByUuid(id)
                .orElseThrow(() -> new ResourceNotFoundException("InventoryItem not found with id: " + id));
        
        Integer oldTotal = item.getTotalQuantity();
        mapper.updateEntity(updateDTO, item);
        
        // If total quantity changed, update available quantity accordingly
        if (updateDTO.getTotalQuantity() != null && !updateDTO.getTotalQuantity().equals(oldTotal)) {
            int difference = updateDTO.getTotalQuantity() - oldTotal;
            int newAvailable = item.getAvailableQuantity() + difference;
            if (newAvailable < 0) {
                throw new IllegalArgumentException("Cannot reduce total quantity below currently booked quantity");
            }
            item.setAvailableQuantity(newAvailable);
        }
        
        if (updateDTO.getStatus() != null) {
            item.setStatus(updateDTO.getStatus());
        }

        InventoryItem saved = repository.save(item);
        return mapper.toDto(saved);
    }

    @Transactional
    public void deleteInventoryItem(UUID id) {
        if (!repository.findByUuid(id).isPresent()) {
            throw new ResourceNotFoundException("InventoryItem not found with id: " + id);
        }
        repository.findByUuid(id).ifPresent(repository::delete);
    }
}
