package uz.uniSport.uni_sport.mapper.files;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import uz.uniSport.uni_sport.domain.files.FileMetadata;
import uz.uniSport.uni_sport.dto.files.FileMetadataDto;
import uz.uniSport.uni_sport.mapper.auth.AuthMapper;

/**
 * Files moduli uchun MapStruct mapper interfeysi.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {AuthMapper.class})
public interface FilesMapper {
    FileMetadataDto toDto(FileMetadata fileMetadata);
}
