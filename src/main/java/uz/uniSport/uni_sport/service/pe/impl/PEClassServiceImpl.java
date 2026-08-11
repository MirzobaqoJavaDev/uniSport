package uz.uniSport.uni_sport.service.pe.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.uniSport.uni_sport.domain.auth.User;
import uz.uniSport.uni_sport.domain.pe.PEClass;
import uz.uniSport.uni_sport.dto.pe.PEClassDto;
import uz.uniSport.uni_sport.exception.ResourceNotFoundException;
import uz.uniSport.uni_sport.mapper.pe.PEMapper;
import uz.uniSport.uni_sport.repository.auth.UserRepository;
import uz.uniSport.uni_sport.repository.pe.PEClassRepository;
import uz.uniSport.uni_sport.service.pe.PEClassService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PEClassServiceImpl implements PEClassService {

    private final PEClassRepository peClassRepository;
    private final UserRepository userRepository;
    private final PEMapper peMapper;

    @Override
    @Transactional
    public PEClassDto createClass(String name, UUID instructorId) {
        User instructor = userRepository.findByUuid(instructorId)
                .orElseThrow(() -> new ResourceNotFoundException("O'qituvchi topilmadi: " + instructorId));

        PEClass peClass = new PEClass();
        peClass.setName(name);
        peClass.setInstructor(instructor);

        PEClass saved = peClassRepository.save(peClass);
        return peMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PEClassDto getClassById(UUID id) {
        PEClass peClass = peClassRepository.findByUuid(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jismoniy tarbiya darsi topilmadi: " + id));
        return peMapper.toDto(peClass);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PEClassDto> getAllClasses() {
        return peClassRepository.findAll().stream()
                .map(peMapper::toDto)
                .collect(Collectors.toList());
    }
}
