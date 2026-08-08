package uz.uniSport.uni_sport.mapper.ai;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import uz.uniSport.uni_sport.domain.ai.AIRequestLog;
import uz.uniSport.uni_sport.domain.ai.PromptTemplate;
import uz.uniSport.uni_sport.domain.ai.PromptVersion;
import uz.uniSport.uni_sport.dto.ai.AIRequestLogDto;
import uz.uniSport.uni_sport.dto.ai.PromptTemplateDto;
import uz.uniSport.uni_sport.dto.ai.PromptVersionDto;
import uz.uniSport.uni_sport.mapper.auth.AuthMapper;

/**
 * AI moduli uchun MapStruct mapper interfeysi.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {AuthMapper.class})
public interface AIMapper {
    PromptTemplateDto toDto(PromptTemplate template);
    PromptVersionDto toDto(PromptVersion version);
    AIRequestLogDto toDto(AIRequestLog log);
}
