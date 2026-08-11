package uz.uniSport.uni_sport.service.gym;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.uniSport.uni_sport.domain.auth.Role;
import uz.uniSport.uni_sport.domain.gym.Quota;
import uz.uniSport.uni_sport.dto.gym.QuotaCreateDTO;
import uz.uniSport.uni_sport.dto.gym.QuotaResponseDTO;
import uz.uniSport.uni_sport.dto.gym.QuotaUpdateDTO;
import uz.uniSport.uni_sport.exception.ResourceNotFoundException;
import uz.uniSport.uni_sport.mapper.gym.GymMapper;
import uz.uniSport.uni_sport.repository.auth.RoleRepository;
import uz.uniSport.uni_sport.repository.gym.QuotaRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuotaService {

    private final QuotaRepository repository;
    private final RoleRepository roleRepository;
    private final GymMapper mapper;

    @Transactional(readOnly = true)
    public List<QuotaResponseDTO> getAllQuotas() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public QuotaResponseDTO getQuotaById(UUID id) {
        Quota quota = repository.findByUuid(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quota not found with id: " + id));
        return mapper.toDto(quota);
    }

    @Transactional(readOnly = true)
    public QuotaResponseDTO getQuotaByRoleId(Long roleId) {
        Quota quota = repository.findByRoleId(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Quota not found for role id: " + roleId));
        return mapper.toDto(quota);
    }

    @Transactional
    public QuotaResponseDTO createQuota(QuotaCreateDTO createDTO) {
        Role role = roleRepository.findById(createDTO.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + createDTO.getRoleId()));

        Quota quota = mapper.toEntity(createDTO);
        quota.setRole(role);
        // Version is handled by @Version automatically upon save
        
        Quota saved = repository.save(quota);
        return mapper.toDto(saved);
    }

    @Transactional
    public QuotaResponseDTO updateQuota(UUID id, QuotaUpdateDTO updateDTO) {
        Quota quota = repository.findByUuid(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quota not found with id: " + id));
        
        mapper.updateEntity(updateDTO, quota);
        Quota saved = repository.save(quota);
        return mapper.toDto(saved);
    }

    @Transactional
    public void deleteQuota(UUID id) {
        if (!repository.findByUuid(id).isPresent()) {
            throw new ResourceNotFoundException("Quota not found with id: " + id);
        }
        repository.findByUuid(id).ifPresent(repository::delete);
    }
}
