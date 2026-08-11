package uz.uniSport.uni_sport.mapper.equipment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import uz.uniSport.uni_sport.domain.equipment.Equipment;
import uz.uniSport.uni_sport.domain.equipment.Rental;
import uz.uniSport.uni_sport.dto.equipment.EquipmentDto;
import uz.uniSport.uni_sport.dto.equipment.RentalDto;

import uz.uniSport.uni_sport.mapper.auth.AuthMapper;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {AuthMapper.class})
public interface EquipmentMapper {

    @Mapping(source = "uuid", target = "id")
    EquipmentDto toDto(Equipment entity);

    @Mapping(source = "uuid", target = "id")
    RentalDto toDto(Rental entity);
}
