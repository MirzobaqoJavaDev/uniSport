package uz.uniSport.uni_sport.service.equipment;

import uz.uniSport.uni_sport.dto.equipment.RentalDto;

import java.util.List;
import java.util.UUID;

public interface RentalService {
    RentalDto rentEquipment(UUID userId, UUID equipmentId);
    RentalDto returnEquipment(UUID rentalId);
    List<RentalDto> getUserRentals(UUID userId);
}
