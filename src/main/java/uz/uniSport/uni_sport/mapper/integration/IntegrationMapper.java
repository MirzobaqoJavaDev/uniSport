package uz.uniSport.uni_sport.mapper.integration;

import org.mapstruct.*;
import uz.uniSport.uni_sport.domain.integration.DynamicQRCode;
import uz.uniSport.uni_sport.dto.integration.DynamicQRCodeCreateDTO;
import uz.uniSport.uni_sport.dto.integration.DynamicQRCodeResponseDTO;
import uz.uniSport.uni_sport.dto.integration.DynamicQRCodeUpdateDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IntegrationMapper {

    @Mapping(source = "uuid", target = "id")
    @Mapping(source = "user.uuid", target = "userId")
    DynamicQRCodeResponseDTO toDto(DynamicQRCode entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "isUsed", ignore = true) // default false
    DynamicQRCode toEntity(DynamicQRCodeCreateDTO dto);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "qrData", ignore = true)
    @Mapping(target = "expiresAt", ignore = true)
    void updateEntity(DynamicQRCodeUpdateDTO dto, @MappingTarget DynamicQRCode entity);
}
