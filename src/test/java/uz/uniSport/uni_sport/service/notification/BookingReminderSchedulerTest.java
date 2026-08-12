package uz.uniSport.uni_sport.service.notification;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uz.uniSport.uni_sport.domain.gym.Booking;
import uz.uniSport.uni_sport.domain.notification.NotificationType;
import uz.uniSport.uni_sport.repository.gym.BookingRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingReminderSchedulerTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private BookingReminderService bookingReminderService;

    @InjectMocks
    private BookingReminderScheduler bookingReminderScheduler;

    @BeforeEach
    void setUp() {
    }

    @Test
    void processReminders_shouldProcessAllReminderTypes() {
        Booking booking24h = new Booking();
        booking24h.setUuid(UUID.randomUUID());

        Booking booking2h = new Booking();
        booking2h.setUuid(UUID.randomUUID());

        Booking booking30m = new Booking();
        booking30m.setUuid(UUID.randomUUID());

        when(bookingRepository.findEligibleBookingsForReminder(any(LocalDateTime.class), any(LocalDateTime.class), eq(NotificationType.BOOKING_REMINDER_24_HOURS)))
                .thenReturn(List.of(booking24h));

        when(bookingRepository.findEligibleBookingsForReminder(any(LocalDateTime.class), any(LocalDateTime.class), eq(NotificationType.BOOKING_REMINDER_2_HOURS)))
                .thenReturn(List.of(booking2h));

        when(bookingRepository.findEligibleBookingsForReminder(any(LocalDateTime.class), any(LocalDateTime.class), eq(NotificationType.BOOKING_REMINDER_30_MINUTES)))
                .thenReturn(List.of(booking30m));

        bookingReminderScheduler.processReminders();

        verify(bookingReminderService, times(1)).processReminder(booking24h, NotificationType.BOOKING_REMINDER_24_HOURS);
        verify(bookingReminderService, times(1)).processReminder(booking2h, NotificationType.BOOKING_REMINDER_2_HOURS);
        verify(bookingReminderService, times(1)).processReminder(booking30m, NotificationType.BOOKING_REMINDER_30_MINUTES);
    }

    @Test
    void processReminders_shouldContinueWhenOneFails() {
        Booking booking1 = new Booking();
        booking1.setUuid(UUID.randomUUID());

        Booking booking2 = new Booking();
        booking2.setUuid(UUID.randomUUID());

        when(bookingRepository.findEligibleBookingsForReminder(any(LocalDateTime.class), any(LocalDateTime.class), eq(NotificationType.BOOKING_REMINDER_24_HOURS)))
                .thenReturn(List.of(booking1, booking2));

        // Let the first one fail
        doThrow(new RuntimeException("Simulated failure")).when(bookingReminderService).processReminder(booking1, NotificationType.BOOKING_REMINDER_24_HOURS);
        
        // Ensure scheduler continues and processes the second one
        bookingReminderScheduler.processReminders();

        verify(bookingReminderService, times(1)).processReminder(booking1, NotificationType.BOOKING_REMINDER_24_HOURS);
        verify(bookingReminderService, times(1)).processReminder(booking2, NotificationType.BOOKING_REMINDER_24_HOURS);
    }
}
