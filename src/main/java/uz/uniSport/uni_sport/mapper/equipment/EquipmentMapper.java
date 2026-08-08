package uz.uniSport.uni_sport.mapper.equipment;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import uz.uniSport.uni_sport.domain.equipment.Equipment;
import uz.uniSport.uni_sport.domain.equipment.Rental;
import uz.uniSport.uni_sport.dto.equipment.EquipmentDto;
import uz.uniSport.uni_sport.dto.equipment.RentalDto;
import uz.uniSport.uni_sport.mapper.auth.AuthMapper;

/**
 * Equipment moduli uchun MapStruct mapper interfeysi.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {AuthMapper.class})
public interface EquipmentMapper {
    EquipmentDto toDto(Equipment equipment);
    RentalDto toDto(Rental rental);
}
