package bani.lux.banikzn.dto.converters;

import bani.lux.banikzn.dto.BookingDto;
import bani.lux.banikzn.dto.BookingResponseDto;
import bani.lux.banikzn.dto.ComplexDto;
import bani.lux.banikzn.dto.NewBookingDto;
import bani.lux.banikzn.models.Booking;
import bani.lux.banikzn.models.Complex;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BookingConverter {
    public BookingDto from(Booking booking){
        String dateStart = String.valueOf(booking.getDateStart());
        String dateEnd = String.valueOf(booking.getDateEnd());
        return BookingDto.builder()
                .start(dateStart+" "+booking.getTimeStart().toString())
                .end(dateEnd+" "+booking.getTimeEnd().toString())
                .build();
    }

    public NewBookingDto from(BookingResponseDto bookingResponseDto) {
        return NewBookingDto.builder()
                .complexId(bookingResponseDto.getComplexId())
                .cost(bookingResponseDto.getCost())
                .dateStart(LocalDate.parse(bookingResponseDto.getDateStart()))
                .dateEnd(LocalDate.parse(bookingResponseDto.getDateEnd()))
                .timeStart(LocalTime.parse(bookingResponseDto.getStartTime()))
                .timeEnd(LocalTime.parse(bookingResponseDto.getEndTime())).build();
    }

    public List<BookingDto> from(List<Booking> bookingList) {
        return bookingList.stream().map(this::from).toList();
    }
}
