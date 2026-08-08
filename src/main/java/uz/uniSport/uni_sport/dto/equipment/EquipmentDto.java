package uz.uniSport.uni_sport.dto.equipment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Sport inventarlari (Equipment) ma'lumotlarini tashuvchi DTO.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentDto {
    private UUID id;
    private String name;
    private Integer totalQuantity;
}
