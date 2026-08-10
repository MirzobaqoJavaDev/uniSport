package uz.uniSport.uni_sport.service.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uz.uniSport.uni_sport.domain.gym.Booking;
import uz.uniSport.uni_sport.domain.notification.NotificationType;
import uz.uniSport.uni_sport.repository.gym.BookingRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookingReminderScheduler {

    private final BookingRepository bookingRepository;
    private final BookingReminderService bookingReminderService;

    /**
     * Har 1 daqiqada ishga tushib, eslatma yuborilishi kerak bo'lgan bandliklarni izlaydi.
     */
    @Scheduled(fixedDelay = 60000)
    public void processReminders() {
        log.debug("Starting booking reminder scheduler check...");
        LocalDateTime now = LocalDateTime.now();

        // 1. 24 Hours Reminder
        processForType(now, now.plusHours(24), NotificationType.BOOKING_REMINDER_24_HOURS);
        
        // 2. 2 Hours Reminder
        processForType(now, now.plusHours(2), NotificationType.BOOKING_REMINDER_2_HOURS);
        
        // 3. 30 Minutes Reminder
        processForType(now, now.plusMinutes(30), NotificationType.BOOKING_REMINDER_30_MINUTES);
        
        log.debug("Booking reminder scheduler check completed.");
    }

    private void processForType(LocalDateTime now, LocalDateTime targetTime, NotificationType type) {
        List<Booking> eligibleBookings = bookingRepository.findEligibleBookingsForReminder(now, targetTime, type);
        
        for (Booking booking : eligibleBookings) {
            try {
                bookingReminderService.processReminder(booking, type);
            } catch (Exception e) {
                // Should not reach here typically due to exception handling inside processReminder, 
                // but added for absolute safety so loop continues
                log.error("Unexpected error in loop for booking {} and type {}", booking.getId(), type, e);
            }
        }
    }
}
