package uz.uniSport.uni_sport.service.integration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.uniSport.uni_sport.domain.auth.User;
import uz.uniSport.uni_sport.domain.integration.DynamicQRCode;
import uz.uniSport.uni_sport.dto.integration.DynamicQRCodeCreateDTO;
import uz.uniSport.uni_sport.dto.integration.DynamicQRCodeResponseDTO;
import uz.uniSport.uni_sport.dto.integration.DynamicQRCodeUpdateDTO;
import uz.uniSport.uni_sport.exception.ResourceNotFoundException;
import uz.uniSport.uni_sport.mapper.integration.IntegrationMapper;
import uz.uniSport.uni_sport.repository.auth.UserRepository;
import uz.uniSport.uni_sport.repository.integration.DynamicQRCodeRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DynamicQRCodeService {

    private final DynamicQRCodeRepository repository;
    private final UserRepository userRepository;
    @Qualifier("integrationMapper")
    private final IntegrationMapper mapper;

    @Transactional(readOnly = true)
    public List<DynamicQRCodeResponseDTO> getAllQRCodes() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DynamicQRCodeResponseDTO getQRCodeById(UUID id) {
        DynamicQRCode qrCode = repository.findByUuid(id)
                .orElseThrow(() -> new ResourceNotFoundException("DynamicQRCode not found with id: " + id));
        return mapper.toDto(qrCode);
    }

    @Transactional(readOnly = true)
    public List<DynamicQRCodeResponseDTO> getQRCodesByUserId(UUID userId) {
        return repository.findByUserUuid(userId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public DynamicQRCodeResponseDTO createQRCode(DynamicQRCodeCreateDTO createDTO) {
        User user = userRepository.findByUuid(createDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + createDTO.getUserId()));

        DynamicQRCode qrCode = mapper.toEntity(createDTO);
        qrCode.setUser(user);
        qrCode.setIsUsed(false);

        DynamicQRCode saved = repository.save(qrCode);
        return mapper.toDto(saved);
    }

    @Transactional
    public DynamicQRCodeResponseDTO updateQRCode(UUID id, DynamicQRCodeUpdateDTO updateDTO) {
        DynamicQRCode qrCode = repository.findByUuid(id)
                .orElseThrow(() -> new ResourceNotFoundException("DynamicQRCode not found with id: " + id));

        mapper.updateEntity(updateDTO, qrCode);
        DynamicQRCode saved = repository.save(qrCode);
        return mapper.toDto(saved);
    }

    @Transactional
    public void deleteQRCode(UUID id) {
        if (!repository.findByUuid(id).isPresent()) {
            throw new ResourceNotFoundException("DynamicQRCode not found with id: " + id);
        }
        repository.findByUuid(id).ifPresent(repository::delete);
    }
}
