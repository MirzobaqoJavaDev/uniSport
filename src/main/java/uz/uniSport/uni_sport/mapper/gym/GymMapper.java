package uz.uniSport.uni_sport.mapper.gym;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import uz.uniSport.uni_sport.domain.gym.Booking;
import uz.uniSport.uni_sport.domain.gym.Court;
import uz.uniSport.uni_sport.domain.gym.Facility;
import uz.uniSport.uni_sport.dto.gym.BookingDto;
import uz.uniSport.uni_sport.dto.gym.CourtDto;
import uz.uniSport.uni_sport.dto.gym.FacilityDto;
import uz.uniSport.uni_sport.mapper.auth.AuthMapper;

/**
 * Gym moduli uchun MapStruct mapper interfeysi.
 * Boshqa modullar ma'lumotlari kerak bo'lsa (masalan User), AuthMapper'dan foydalanadi (uses).
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {AuthMapper.class})
public interface GymMapper {
    FacilityDto toDto(Facility facility);
    CourtDto toDto(Court court);
    BookingDto toDto(Booking booking);
}
