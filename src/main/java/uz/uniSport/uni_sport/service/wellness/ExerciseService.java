package uz.uniSport.uni_sport.service.wellness;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.uniSport.uni_sport.domain.wellness.Exercise;
import uz.uniSport.uni_sport.dto.wellness.ExerciseCreateDTO;
import uz.uniSport.uni_sport.dto.wellness.ExerciseResponseDTO;
import uz.uniSport.uni_sport.dto.wellness.ExerciseUpdateDTO;
import uz.uniSport.uni_sport.exception.ResourceNotFoundException;
import uz.uniSport.uni_sport.mapper.wellness.WellnessMapper;
import uz.uniSport.uni_sport.repository.wellness.ExerciseRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository repository;
    private final WellnessMapper mapper;

    @Transactional(readOnly = true)
    public List<ExerciseResponseDTO> getAllExercises() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ExerciseResponseDTO getExerciseById(UUID id) {
        Exercise exercise = repository.findByUuid(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise not found with id: " + id));
        return mapper.toDto(exercise);
    }

    @Transactional
    public ExerciseResponseDTO createExercise(ExerciseCreateDTO createDTO) {
        Exercise exercise = mapper.toEntity(createDTO);
        Exercise saved = repository.save(exercise);
        return mapper.toDto(saved);
    }

    @Transactional
    public ExerciseResponseDTO updateExercise(UUID id, ExerciseUpdateDTO updateDTO) {
        Exercise exercise = repository.findByUuid(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise not found with id: " + id));
        
        mapper.updateEntity(updateDTO, exercise);
        Exercise saved = repository.save(exercise);
        return mapper.toDto(saved);
    }

    @Transactional
    public void deleteExercise(UUID id) {
        if (!repository.findByUuid(id).isPresent()) {
            throw new ResourceNotFoundException("Exercise not found with id: " + id);
        }
        repository.findByUuid(id).ifPresent(repository::delete);
    }
}
