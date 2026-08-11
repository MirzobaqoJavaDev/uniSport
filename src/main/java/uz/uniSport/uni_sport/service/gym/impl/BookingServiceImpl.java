package uz.uniSport.uni_sport.service.gym.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.uniSport.uni_sport.domain.auth.User;
import uz.uniSport.uni_sport.domain.gym.Booking;
import uz.uniSport.uni_sport.domain.gym.Court;
import uz.uniSport.uni_sport.dto.gym.BookingDto;
import uz.uniSport.uni_sport.exception.BusinessLogicException;
import uz.uniSport.uni_sport.exception.ResourceNotFoundException;
import uz.uniSport.uni_sport.mapper.gym.GymMapper;
import uz.uniSport.uni_sport.repository.auth.UserRepository;
import uz.uniSport.uni_sport.repository.gym.BookingRepository;
import uz.uniSport.uni_sport.repository.gym.CourtRepository;
import uz.uniSport.uni_sport.service.gym.BookingService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final CourtRepository courtRepository;
    private final UserRepository userRepository;
    private final GymMapper gymMapper;

    @Override
    @Transactional
    public BookingDto bookCourt(UUID userId, UUID courtId, LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime.isAfter(endTime) || startTime.isEqual(endTime)) {
            throw new BusinessLogicException("Boshlanish vaqti tugash vaqtidan oldin bo'lishi kerak.");
        }

        User user = userRepository.findByUuid(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Foydalanuvchi topilmadi: " + userId));
        
        Court court = courtRepository.findByUuid(courtId)
                .orElseThrow(() -> new ResourceNotFoundException("Kort topilmadi: " + courtId));

        // Boshqa bandlik bilan ustma-ust tushishini (overlap) tekshirish
        boolean isBooked = bookingRepository.isCourtBooked(court.getId(), startTime, endTime);
        if (isBooked) {
            throw new BusinessLogicException("Kechirasiz, tanlangan vaqtda kort allaqachon band qilingan.");
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setCourt(court);
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);
        booking.setStatus("ACTIVE");

        Booking saved = bookingRepository.save(booking);
        return gymMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingDto> getUserBookings(UUID userId) {
        return bookingRepository.findByUserUuid(userId).stream()
                .map(gymMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void cancelBooking(UUID bookingId, UUID userId) {
        Booking booking = bookingRepository.findByUuid(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Bandlik topilmadi: " + bookingId));
        
        // Faqat o'zining bandligini bekor qila oladi
        if (!booking.getUser().getUuid().equals(userId)) {
            throw new BusinessLogicException("Siz faqat o'zingizning bandligingizni bekor qila olasiz.");
        }

        booking.setStatus("CANCELLED");
        bookingRepository.save(booking);
    }
}
