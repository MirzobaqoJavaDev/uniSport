package uz.uniSport.uni_sport.mapper.integration;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
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

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "qrData", ignore = true)
    @Mapping(target = "expiresAt", ignore = true)
    void updateEntity(DynamicQRCodeUpdateDTO dto, @MappingTarget DynamicQRCode entity);
}
