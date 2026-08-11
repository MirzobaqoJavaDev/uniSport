package uz.uniSport.uni_sport.mapper.ai;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import uz.uniSport.uni_sport.domain.ai.AIRequestLog;
import uz.uniSport.uni_sport.domain.ai.PromptTemplate;
import uz.uniSport.uni_sport.domain.ai.TokenUsageLog;
import uz.uniSport.uni_sport.dto.ai.AIRequestLogDto;
import uz.uniSport.uni_sport.dto.ai.PromptTemplateDto;
import uz.uniSport.uni_sport.dto.ai.TokenUsageLogCreateDTO;
import uz.uniSport.uni_sport.dto.ai.TokenUsageLogResponseDTO;

import uz.uniSport.uni_sport.mapper.auth.AuthMapper;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {AuthMapper.class})
public interface AIMapper {

    @Mapping(source = "uuid", target = "id")
    PromptTemplateDto toDto(PromptTemplate entity);

    @Mapping(source = "uuid", target = "id")
    AIRequestLogDto toDto(AIRequestLog entity);

    @Mapping(source = "uuid", target = "id")
    @Mapping(source = "user.uuid", target = "userId")
    TokenUsageLogResponseDTO toDto(TokenUsageLog entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "user", ignore = true)
    TokenUsageLog toEntity(TokenUsageLogCreateDTO dto);
}
