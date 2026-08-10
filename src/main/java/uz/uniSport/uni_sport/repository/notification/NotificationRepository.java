package uz.uniSport.uni_sport.repository.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uniSport.uni_sport.domain.notification.Notification;
import uz.uniSport.uni_sport.domain.notification.NotificationType;

import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    
    boolean existsByReferenceIdAndType(UUID referenceId, NotificationType type);
    
}
