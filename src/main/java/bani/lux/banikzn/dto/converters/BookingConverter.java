package bani.lux.banikzn.dto.converters;

import bani.lux.banikzn.dto.BookingDto;
import bani.lux.banikzn.dto.ComplexDto;
import bani.lux.banikzn.models.Booking;
import bani.lux.banikzn.models.Complex;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookingConverter {
    public BookingDto from(Booking booking){
        String date = String.valueOf(booking.getDate());
        return BookingDto.builder()
                .start(date+" "+booking.getTimeStart().toString())
                .end(date+" "+booking.getTimeEnd().toString())
                .build();
    }
    public List<BookingDto> from(List<Booking> bookingList) {
        return bookingList.stream().map(this::from).toList();
    }
}
