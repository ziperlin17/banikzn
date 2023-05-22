package bani.lux.banikzn.services;

import bani.lux.banikzn.dto.BookingDto;
import bani.lux.banikzn.dto.NewBookingDto;
import bani.lux.banikzn.dto.NewComplexDto;
import bani.lux.banikzn.models.Booking;

import java.util.List;

public interface BookingService {
    void saveBooking(NewBookingDto newBookingDto);

    List<BookingDto> getBookingData(Long id);
//    List<BookingDto>
    Boolean checkNewBooking(NewBookingDto newBookingDto);
}
