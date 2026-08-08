package uz.uniSport.uni_sport.repository.integration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uniSport.uni_sport.domain.integration.DynamicQRCode;

import java.util.List;
import java.util.UUID;

@Repository
public interface DynamicQRCodeRepository extends JpaRepository<DynamicQRCode, UUID> {
    List<DynamicQRCode> findByUserId(UUID userId);
}
