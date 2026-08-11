package uz.uniSport.uni_sport.mapper.pe;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import uz.uniSport.uni_sport.domain.pe.Attendance;
import uz.uniSport.uni_sport.domain.pe.ClassSchedule;
import uz.uniSport.uni_sport.domain.pe.PEClass;
import uz.uniSport.uni_sport.dto.pe.AttendanceDto;
import uz.uniSport.uni_sport.dto.pe.ClassScheduleDto;
import uz.uniSport.uni_sport.dto.pe.PEClassDto;
import uz.uniSport.uni_sport.mapper.auth.AuthMapper;

/**
 * PE Academic moduli uchun MapStruct mapper interfeysi.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {AuthMapper.class})
public interface PEMapper {
    @Mapping(source = "uuid", target = "id")
    PEClassDto toDto(PEClass peClass);

    @Mapping(source = "uuid", target = "id")
    ClassScheduleDto toDto(ClassSchedule classSchedule);

    @Mapping(source = "uuid", target = "id")
    AttendanceDto toDto(Attendance attendance);
}
