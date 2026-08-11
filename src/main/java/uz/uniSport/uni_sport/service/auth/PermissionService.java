package uz.uniSport.uni_sport.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.uniSport.uni_sport.domain.auth.Permission;
import uz.uniSport.uni_sport.dto.auth.PermissionCreateDTO;
import uz.uniSport.uni_sport.dto.auth.PermissionResponseDTO;
import uz.uniSport.uni_sport.dto.auth.PermissionUpdateDTO;
import uz.uniSport.uni_sport.exception.BusinessLogicException;
import uz.uniSport.uni_sport.exception.ResourceNotFoundException;
import uz.uniSport.uni_sport.mapper.auth.AuthMapper;
import uz.uniSport.uni_sport.repository.auth.PermissionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository repository;
    private final AuthMapper mapper;

    @Transactional(readOnly = true)
    public List<PermissionResponseDTO> getAllPermissions() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PermissionResponseDTO getPermissionById(Long id) {
        Permission permission = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found with id: " + id));
        return mapper.toDto(permission);
    }

    @Transactional
    public PermissionResponseDTO createPermission(PermissionCreateDTO createDTO) {
        // Here we could add a check if a permission with the same name already exists
        Permission permission = mapper.toEntity(createDTO);
        Permission saved = repository.save(permission);
        return mapper.toDto(saved);
    }

    @Transactional
    public PermissionResponseDTO updatePermission(Long id, PermissionUpdateDTO updateDTO) {
        Permission permission = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found with id: " + id));
        mapper.updateEntity(updateDTO, permission);
        Permission saved = repository.save(permission);
        return mapper.toDto(saved);
    }

    @Transactional
    public void deletePermission(Long id) {
        if (!repository.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Permission not found with id: " + id);
        }
        repository.findById(id).ifPresent(repository::delete);
    }
}
