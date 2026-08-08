package uz.uniSport.uni_sport.service.equipment.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.uniSport.uni_sport.domain.auth.User;
import uz.uniSport.uni_sport.domain.equipment.Equipment;
import uz.uniSport.uni_sport.domain.equipment.Rental;
import uz.uniSport.uni_sport.dto.equipment.RentalDto;
import uz.uniSport.uni_sport.exception.BusinessLogicException;
import uz.uniSport.uni_sport.exception.ResourceNotFoundException;
import uz.uniSport.uni_sport.mapper.equipment.EquipmentMapper;
import uz.uniSport.uni_sport.repository.auth.UserRepository;
import uz.uniSport.uni_sport.repository.equipment.EquipmentRepository;
import uz.uniSport.uni_sport.repository.equipment.RentalRepository;
import uz.uniSport.uni_sport.service.equipment.RentalService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final EquipmentRepository equipmentRepository;
    private final UserRepository userRepository;
    private final EquipmentMapper equipmentMapper;

    @Override
    @Transactional
    public RentalDto rentEquipment(UUID userId, UUID equipmentId) {
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventar topilmadi: " + equipmentId));

        if (equipment.getTotalQuantity() <= 0) {
            throw new BusinessLogicException("Kechirasiz, ushbu inventar hozirda mavjud emas (tugagan).");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Foydalanuvchi topilmadi: " + userId));

        // Inventar sonini kamaytirish
        equipment.setTotalQuantity(equipment.getTotalQuantity() - 1);
        equipmentRepository.save(equipment);

        Rental rental = new Rental();
        rental.setUser(user);
        rental.setEquipment(equipment);
        rental.setRentedAt(LocalDateTime.now());
        rental.setStatus("ACTIVE");

        Rental saved = rentalRepository.save(rental);
        return equipmentMapper.toDto(saved);
    }

    @Override
    @Transactional
    public RentalDto returnEquipment(UUID rentalId) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new ResourceNotFoundException("Ijara yozuvi topilmadi: " + rentalId));

        if (!"ACTIVE".equals(rental.getStatus())) {
            throw new BusinessLogicException("Bu inventar allaqachon qaytarilgan.");
        }

        rental.setReturnedAt(LocalDateTime.now());
        rental.setStatus("RETURNED");
        
        Equipment equipment = rental.getEquipment();
        equipment.setTotalQuantity(equipment.getTotalQuantity() + 1);
        
        equipmentRepository.save(equipment);
        Rental saved = rentalRepository.save(rental);
        
        return equipmentMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RentalDto> getUserRentals(UUID userId) {
        return rentalRepository.findByUserId(userId).stream()
                .map(equipmentMapper::toDto)
                .collect(Collectors.toList());
    }
}
