package uz.uniSport.uni_sport.mapper.gym;

import org.mapstruct.*;
import uz.uniSport.uni_sport.domain.gym.Booking;
import uz.uniSport.uni_sport.domain.gym.Court;
import uz.uniSport.uni_sport.domain.gym.Facility;
import uz.uniSport.uni_sport.domain.gym.InventoryItem;
import uz.uniSport.uni_sport.domain.gym.Quota;
import uz.uniSport.uni_sport.dto.gym.BookingDto;
import uz.uniSport.uni_sport.dto.gym.CourtDto;
import uz.uniSport.uni_sport.dto.gym.FacilityDto;
import uz.uniSport.uni_sport.dto.gym.InventoryItemCreateDTO;
import uz.uniSport.uni_sport.dto.gym.InventoryItemResponseDTO;
import uz.uniSport.uni_sport.dto.gym.InventoryItemUpdateDTO;
import uz.uniSport.uni_sport.dto.gym.QuotaCreateDTO;
import uz.uniSport.uni_sport.dto.gym.QuotaResponseDTO;
import uz.uniSport.uni_sport.dto.gym.QuotaUpdateDTO;
import uz.uniSport.uni_sport.mapper.auth.AuthMapper;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {AuthMapper.class})
public interface GymMapper {
    @Mapping(source = "uuid", target = "id")
    FacilityDto toDto(Facility facility);

    @Mapping(source = "uuid", target = "id")
    CourtDto toDto(Court court);

    @Mapping(source = "uuid", target = "id")
    BookingDto toDto(Booking booking);

    @Mapping(source = "uuid", target = "id")
    @Mapping(source = "role.uuid", target = "roleUuid")
    QuotaResponseDTO toDto(Quota quota);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "version", ignore = true)
    Quota toEntity(QuotaCreateDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateEntity(QuotaUpdateDTO dto, @MappingTarget Quota quota);

    @Mapping(source = "uuid", target = "id")
    @Mapping(source = "facility.uuid", target = "facilityId")
    InventoryItemResponseDTO toDto(InventoryItem item);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "facility", ignore = true)
    @Mapping(target = "availableQuantity", ignore = true)
    @Mapping(target = "status", ignore = true)
    InventoryItem toEntity(InventoryItemCreateDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "facility", ignore = true)
    @Mapping(target = "availableQuantity", ignore = true)
    void updateEntity(InventoryItemUpdateDTO dto, @MappingTarget InventoryItem item);
}
