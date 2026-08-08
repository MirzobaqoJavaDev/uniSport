package uz.uniSport.uni_sport.repository.pe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uniSport.uni_sport.domain.pe.PEClass;

import java.util.UUID;

/**
 * Jismoniy tarbiya darslarini (PEClass) boshqarish uchun Repository.
 */
@Repository
public interface PEClassRepository extends JpaRepository<PEClass, UUID> {
}
