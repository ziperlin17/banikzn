package bani.lux.banikzn.services.impl;

import bani.lux.banikzn.dto.BookingDto;
import bani.lux.banikzn.dto.NewBookingDto;
import bani.lux.banikzn.dto.converters.BookingConverter;
import bani.lux.banikzn.models.Booking;
import bani.lux.banikzn.repositories.BookingRepository;
import bani.lux.banikzn.services.BookingService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Builder
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingConverter bookingConverter;
    @Override
    public void saveBooking(NewBookingDto newBookingDto) {
        bookingRepository.save(Booking.builder()
                        .complex(newBookingDto.getComplex())
                        .date(LocalDate.now())
                        .cost(newBookingDto.getCost())
                        .timeStart(newBookingDto.getTimeStart())
                        .timeEnd(newBookingDto.getTimeEnd())
                .build());
    }

    @Override
    public List<BookingDto> getBookingData(Long complexId) {
        List<Booking> bookingList = bookingRepository.findAllByComplexId(complexId);
        return bookingConverter.from(bookingList);
    }

    @Override
    public Boolean checkNewBooking(NewBookingDto newBookingDto) {
        List<Booking> bookingList = bookingRepository.findAllByComplexIdAndDate(newBookingDto.getComplex().getId(), newBookingDto.getDate());
        LocalTime newBookingStartTime = newBookingDto.getTimeStart();
        LocalTime newBookingEndTime = newBookingDto.getTimeEnd();

        for (Booking booking : bookingList) {
            LocalTime existingBookingStartTime = booking.getTimeStart();
            LocalTime existingBookingEndTime = booking.getTimeEnd();
            if (newBookingStartTime.isBefore(existingBookingEndTime) && newBookingEndTime.isAfter(existingBookingStartTime)) {
                return false;
            }
        }

        return true;
    }
//     { "title": "запривачено",
//     "url": "https://google.com/",
//     "start": "2023-05-22 13:00",
//     "end": "2023-05-22 22:00" }
}
