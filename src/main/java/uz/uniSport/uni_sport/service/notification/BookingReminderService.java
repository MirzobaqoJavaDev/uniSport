package uz.uniSport.uni_sport.service.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import uz.uniSport.uni_sport.domain.gym.Booking;
import uz.uniSport.uni_sport.domain.notification.Notification;
import uz.uniSport.uni_sport.domain.notification.NotificationChannel;
import uz.uniSport.uni_sport.domain.notification.NotificationStatus;
import uz.uniSport.uni_sport.domain.notification.NotificationType;
import uz.uniSport.uni_sport.repository.notification.NotificationRepository;

import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingReminderService {

    private final NotificationRepository notificationRepository;
    private final NotificationDeliveryService deliveryService;

    /**
     * Bitta bandlik uchun alohida tranzaksiyada eslatma yuborish mantiqi.
     * Bu orqali 100 ta bandlikdan 1 tasi xato bersa ham, qolgan 99 tasi muvaffaqiyatli ishlaydi.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void processReminder(Booking booking, NotificationType type) {
        if (notificationRepository.existsByReferenceIdAndType(booking.getId(), type)) {
            log.info("Reminder {} already exists for booking {}, skipping.", type, booking.getId());
            return;
        }

        Notification notification = new Notification();
        notification.setRecipient(booking.getUser());
        notification.setReferenceId(booking.getId());
        notification.setType(type);
        notification.setChannel(NotificationChannel.PUSH); // Default to PUSH, could be configured
        notification.setStatus(NotificationStatus.PENDING);

        try {
            // First save to obtain DB lock via Unique Constraint
            // If another scheduler thread is processing this exact booking + type, it will throw DataIntegrityViolationException
            notification = notificationRepository.saveAndFlush(notification);
            
            String timeStr = booking.getStartTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
            String message = String.format("Eslatma: Sizning %s dagi mashg'ulotingiz tez orada boshlanadi (%s).", 
                                           booking.getCourt().getName(), timeStr);

            boolean success = deliveryService.sendNotification(booking.getUser().getId().toString(), message, notification.getChannel());
            
            if (success) {
                notification.setStatus(NotificationStatus.SENT);
            } else {
                notification.setStatus(NotificationStatus.FAILED);
                notification.setErrorMessage("Delivery failed due to third-party error.");
            }
            
            notificationRepository.save(notification);
            
        } catch (DataIntegrityViolationException e) {
            log.warn("Race condition prevented: Reminder {} for booking {} is already being processed.", type, booking.getId());
        } catch (Exception e) {
            log.error("Failed to process reminder {} for booking {}", type, booking.getId(), e);
            if (notification.getId() != null) {
                notification.setStatus(NotificationStatus.FAILED);
                notification.setErrorMessage(e.getMessage());
                notificationRepository.save(notification);
            } else {
                throw e; // Rethrow if it wasn't even saved initially
            }
        }
    }
}
