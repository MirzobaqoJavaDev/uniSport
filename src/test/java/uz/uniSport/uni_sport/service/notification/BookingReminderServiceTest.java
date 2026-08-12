package uz.uniSport.uni_sport.service.notification;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import uz.uniSport.uni_sport.domain.auth.User;
import uz.uniSport.uni_sport.domain.gym.Booking;
import uz.uniSport.uni_sport.domain.gym.Court;
import uz.uniSport.uni_sport.domain.notification.Notification;
import uz.uniSport.uni_sport.domain.notification.NotificationChannel;
import uz.uniSport.uni_sport.domain.notification.NotificationStatus;
import uz.uniSport.uni_sport.domain.notification.NotificationType;
import uz.uniSport.uni_sport.repository.notification.NotificationRepository;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingReminderServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private NotificationDeliveryService deliveryService;

    @InjectMocks
    private BookingReminderService bookingReminderService;

    @Test
    void processReminder_alreadyExists_skipsDelivery() {
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setUuid(UUID.randomUUID());

        when(notificationRepository.existsByReferenceIdAndType(booking.getId(), NotificationType.BOOKING_REMINDER_24_HOURS)).thenReturn(true);

        bookingReminderService.processReminder(booking, NotificationType.BOOKING_REMINDER_24_HOURS);

        verify(notificationRepository, never()).saveAndFlush(any());
        verify(deliveryService, never()).sendNotification(any(), any(), any());
    }

    @Test
    void processReminder_raceCondition_handlesGracefully() {
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setUuid(UUID.randomUUID());
        User user = new User();
        user.setId(1L);
        user.setUuid(UUID.randomUUID());
        booking.setUser(user);

        when(notificationRepository.existsByReferenceIdAndType(any(), any())).thenReturn(false);
        when(notificationRepository.saveAndFlush(any())).thenThrow(new DataIntegrityViolationException("Unique constraint violation"));

        bookingReminderService.processReminder(booking, NotificationType.BOOKING_REMINDER_2_HOURS);

        verify(deliveryService, never()).sendNotification(any(), any(), any());
    }

    @Test
    void processReminder_successfulDelivery() {
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setUuid(UUID.randomUUID());
        User user = new User();
        user.setId(1L);
        user.setUuid(UUID.randomUUID());
        booking.setUser(user);
        Court court = new Court();
        court.setName("Test Court");
        booking.setCourt(court);
        booking.setStartTime(LocalDateTime.now().plusHours(24));

        Notification initialSaved = new Notification();
        when(notificationRepository.existsByReferenceIdAndType(any(), any())).thenReturn(false);
        when(notificationRepository.saveAndFlush(any())).thenReturn(initialSaved);
        when(deliveryService.sendNotification(any(), any(), any())).thenReturn(true);

        bookingReminderService.processReminder(booking, NotificationType.BOOKING_REMINDER_24_HOURS);

        ArgumentCaptor<Notification> captor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationRepository).save(captor.capture());

        Notification finalNotification = captor.getValue();
        assertEquals(NotificationStatus.SENT, finalNotification.getStatus());
    }

    @Test
    void processReminder_failedDelivery() {
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setUuid(UUID.randomUUID());
        User user = new User();
        user.setId(1L);
        user.setUuid(UUID.randomUUID());
        booking.setUser(user);
        Court court = new Court();
        court.setName("Test Court");
        booking.setCourt(court);
        booking.setStartTime(LocalDateTime.now().plusHours(24));

        Notification initialSaved = new Notification();
        initialSaved.setUuid(UUID.randomUUID()); // ensure it gets to the fallback update block
        when(notificationRepository.existsByReferenceIdAndType(any(), any())).thenReturn(false);
        when(notificationRepository.saveAndFlush(any())).thenReturn(initialSaved);
        when(deliveryService.sendNotification(any(), any(), any())).thenReturn(false);

        bookingReminderService.processReminder(booking, NotificationType.BOOKING_REMINDER_24_HOURS);

        ArgumentCaptor<Notification> captor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationRepository).save(captor.capture());

        Notification finalNotification = captor.getValue();
        assertEquals(NotificationStatus.FAILED, finalNotification.getStatus());
        assertEquals("Delivery failed due to third-party error.", finalNotification.getErrorMessage());
    }
}
