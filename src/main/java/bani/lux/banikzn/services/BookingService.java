package bani.lux.banikzn.services;

import bani.lux.banikzn.dto.BookingDto;
import bani.lux.banikzn.dto.BookingRequestDto;
import bani.lux.banikzn.dto.NewBookingDto;

import java.util.List;
import java.util.Map;

public interface BookingService {
    void saveBooking(NewBookingDto newBookingDto);

    List<BookingDto> getBookingData(Long id);
//    List<BookingDto>
    Map<Boolean, Integer> checkNewBooking(BookingRequestDto bookingRequestDto);
}
