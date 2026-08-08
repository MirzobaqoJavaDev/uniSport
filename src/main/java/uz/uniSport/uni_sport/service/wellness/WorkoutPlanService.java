package uz.uniSport.uni_sport.service.wellness;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.uniSport.uni_sport.domain.auth.User;
import uz.uniSport.uni_sport.domain.wellness.Exercise;
import uz.uniSport.uni_sport.domain.wellness.WorkoutPlan;
import uz.uniSport.uni_sport.dto.wellness.WorkoutPlanCreateDTO;
import uz.uniSport.uni_sport.dto.wellness.WorkoutPlanResponseDTO;
import uz.uniSport.uni_sport.dto.wellness.WorkoutPlanUpdateDTO;
import uz.uniSport.uni_sport.exception.ResourceNotFoundException;
import uz.uniSport.uni_sport.mapper.wellness.WellnessMapper;
import uz.uniSport.uni_sport.repository.auth.UserRepository;
import uz.uniSport.uni_sport.repository.wellness.ExerciseRepository;
import uz.uniSport.uni_sport.repository.wellness.WorkoutPlanRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutPlanService {

    private final WorkoutPlanRepository repository;
    private final UserRepository userRepository;
    private final ExerciseRepository exerciseRepository;
    private final WellnessMapper mapper;

    @Transactional(readOnly = true)
    public List<WorkoutPlanResponseDTO> getAllWorkoutPlans() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public WorkoutPlanResponseDTO getWorkoutPlanById(UUID id) {
        WorkoutPlan plan = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WorkoutPlan not found with id: " + id));
        return mapper.toDto(plan);
    }

    @Transactional(readOnly = true)
    public List<WorkoutPlanResponseDTO> getWorkoutPlansByUserId(UUID userId) {
        return repository.findByUserId(userId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public WorkoutPlanResponseDTO createWorkoutPlan(WorkoutPlanCreateDTO createDTO) {
        User user = userRepository.findById(createDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + createDTO.getUserId()));

        WorkoutPlan plan = mapper.toEntity(createDTO);
        plan.setUser(user);
        
        if (createDTO.getExerciseIds() != null && !createDTO.getExerciseIds().isEmpty()) {
            Set<Exercise> exercises = new HashSet<>(exerciseRepository.findAllById(createDTO.getExerciseIds()));
            plan.setExercises(exercises);
        }

        WorkoutPlan saved = repository.save(plan);
        return mapper.toDto(saved);
    }

    @Transactional
    public WorkoutPlanResponseDTO updateWorkoutPlan(UUID id, WorkoutPlanUpdateDTO updateDTO) {
        WorkoutPlan plan = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WorkoutPlan not found with id: " + id));

        mapper.updateEntity(updateDTO, plan);
        
        if (updateDTO.getExerciseIds() != null) {
            Set<Exercise> exercises = new HashSet<>(exerciseRepository.findAllById(updateDTO.getExerciseIds()));
            plan.setExercises(exercises);
        }

        WorkoutPlan saved = repository.save(plan);
        return mapper.toDto(saved);
    }

    @Transactional
    public void deleteWorkoutPlan(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("WorkoutPlan not found with id: " + id);
        }
        repository.deleteById(id);
    }
}
