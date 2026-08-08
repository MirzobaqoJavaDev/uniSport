package uz.uniSport.uni_sport.service.pe.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.uniSport.uni_sport.domain.pe.ClassSchedule;
import uz.uniSport.uni_sport.domain.pe.PEClass;
import uz.uniSport.uni_sport.dto.pe.ClassScheduleDto;
import uz.uniSport.uni_sport.exception.BusinessLogicException;
import uz.uniSport.uni_sport.exception.ResourceNotFoundException;
import uz.uniSport.uni_sport.mapper.pe.PEMapper;
import uz.uniSport.uni_sport.repository.pe.ClassScheduleRepository;
import uz.uniSport.uni_sport.repository.pe.PEClassRepository;
import uz.uniSport.uni_sport.service.pe.ClassScheduleService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassScheduleServiceImpl implements ClassScheduleService {

    private final ClassScheduleRepository scheduleRepository;
    private final PEClassRepository peClassRepository;
    private final PEMapper peMapper;

    @Override
    @Transactional
    public ClassScheduleDto createSchedule(UUID classId, LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime.isAfter(endTime)) {
            throw new BusinessLogicException("Boshlanish vaqti tugash vaqtidan oldin bo'lishi kerak.");
        }

        PEClass peClass = peClassRepository.findById(classId)
                .orElseThrow(() -> new ResourceNotFoundException("Dars topilmadi: " + classId));

        ClassSchedule schedule = new ClassSchedule();
        schedule.setPeClass(peClass);
        schedule.setStartTime(startTime);
        schedule.setEndTime(endTime);

        ClassSchedule saved = scheduleRepository.save(schedule);
        return peMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassScheduleDto> getSchedulesBetween(LocalDateTime start, LocalDateTime end) {
        return scheduleRepository.findByStartTimeBetween(start, end).stream()
                .map(peMapper::toDto)
                .collect(Collectors.toList());
    }
}
