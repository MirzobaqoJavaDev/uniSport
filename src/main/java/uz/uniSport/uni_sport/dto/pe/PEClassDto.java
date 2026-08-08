package uz.uniSport.uni_sport.dto.pe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.uniSport.uni_sport.dto.auth.UserDto;

import java.util.UUID;

/**
 * Jismoniy tarbiya darslari (PE Class) uchun DTO.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PEClassDto {
    private UUID id;
    private String name;
    private UserDto instructor;
}
