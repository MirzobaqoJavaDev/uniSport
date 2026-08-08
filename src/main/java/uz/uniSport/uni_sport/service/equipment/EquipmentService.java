package uz.uniSport.uni_sport.service.equipment;

import uz.uniSport.uni_sport.dto.equipment.EquipmentDto;

import java.util.List;
import java.util.UUID;

public interface EquipmentService {
    EquipmentDto createEquipment(String name, Integer totalQuantity);
    EquipmentDto getEquipmentById(UUID id);
    List<EquipmentDto> getAllEquipment();
}
