package uz.uniSport.uni_sport.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uniSport.uni_sport.domain.auth.Permission;



@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
