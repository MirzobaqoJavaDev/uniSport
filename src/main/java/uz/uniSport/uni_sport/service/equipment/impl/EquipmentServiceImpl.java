package uz.uniSport.uni_sport.service.equipment.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.uniSport.uni_sport.domain.equipment.Equipment;
import uz.uniSport.uni_sport.dto.equipment.EquipmentDto;
import uz.uniSport.uni_sport.exception.ResourceNotFoundException;
import uz.uniSport.uni_sport.mapper.equipment.EquipmentMapper;
import uz.uniSport.uni_sport.repository.equipment.EquipmentRepository;
import uz.uniSport.uni_sport.service.equipment.EquipmentService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final EquipmentMapper equipmentMapper;

    @Override
    @Transactional
    public EquipmentDto createEquipment(String name, Integer totalQuantity) {
        Equipment equipment = new Equipment();
        equipment.setName(name);
        equipment.setTotalQuantity(totalQuantity);
        
        Equipment saved = equipmentRepository.save(equipment);
        return equipmentMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public EquipmentDto getEquipmentById(UUID id) {
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventar topilmadi: " + id));
        return equipmentMapper.toDto(equipment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EquipmentDto> getAllEquipment() {
        return equipmentRepository.findAll().stream()
                .map(equipmentMapper::toDto)
                .collect(Collectors.toList());
    }
}
