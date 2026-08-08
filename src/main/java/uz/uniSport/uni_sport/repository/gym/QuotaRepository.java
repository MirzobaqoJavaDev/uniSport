package uz.uniSport.uni_sport.repository.gym;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uniSport.uni_sport.domain.gym.Quota;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuotaRepository extends JpaRepository<Quota, UUID> {
    Optional<Quota> findByRoleId(Long roleId);
}
