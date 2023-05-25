package bani.lux.banikzn.services.impl;

import bani.lux.banikzn.dto.BookingDto;
import bani.lux.banikzn.dto.BookingRequestDto;
import bani.lux.banikzn.dto.NewBookingDto;
import bani.lux.banikzn.dto.converters.BookingConverter;
import bani.lux.banikzn.models.Booking;
import bani.lux.banikzn.repositories.BookingRepository;
import bani.lux.banikzn.repositories.ComplexRepository;
import bani.lux.banikzn.services.BookingService;
import bani.lux.banikzn.utils.OpenCloseTime;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.*;
import java.time.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Builder
public class BookingServiceImpl implements BookingService {
    private final ComplexRepository complexRepository;
    private final BookingRepository bookingRepository;
    private final BookingConverter bookingConverter;

    @Override
    public void saveBooking(NewBookingDto newBookingDto) {
        bookingRepository.save(Booking.builder()
                .complex(complexRepository.findComplexById(newBookingDto.getComplexId()).orElseThrow())
                .dateStart(newBookingDto.getDateStart())
                .dateEnd(newBookingDto.getDateEnd())
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
    public Map<Boolean, Integer> checkNewBooking(BookingRequestDto bookingRequestDto) {
        List<Booking> bookingList = bookingRepository.findAllByComplexIdAndDateStart(bookingRequestDto.getComplexId(), LocalDate.parse(bookingRequestDto.getDateStart()));
        LocalDate newBookingDateStart = LocalDate.parse(bookingRequestDto.getDateStart());
        LocalDate newBookingDateEnd = LocalDate.parse(bookingRequestDto.getDateEnd());
        Map<DayOfWeek, OpenCloseTime> workSchedule = complexRepository.findComplexById(bookingRequestDto.getComplexId()).orElseThrow().getSchedule().getWorkSchedule();
        LocalDateTime newBookingLDTStart = LocalDateTime.of(LocalDate.parse(bookingRequestDto.getDateStart()), LocalTime.parse(bookingRequestDto.getStartTime()));
        LocalDateTime newBookingLDTEnd = LocalDateTime.of(LocalDate.parse(bookingRequestDto.getDateEnd()), LocalTime.parse(bookingRequestDto.getEndTime()));
        LocalTime midnight = LocalTime.MIDNIGHT;

        int cost = 0;
        Map<Boolean, Integer> map = new HashMap<>();

        boolean isValid;
        if (workSchedule.containsKey(newBookingLDTStart.getDayOfWeek())
                && workSchedule.containsKey(newBookingLDTEnd.getDayOfWeek())) {
            if (workSchedule.get(newBookingLDTStart.getDayOfWeek()).getCloseTime().isBefore(workSchedule.get(newBookingLDTStart.getDayOfWeek()).getCloseTime())) {
                isValid = LocalDateTime.of(LocalDate.parse(bookingRequestDto.getDateStart()).plusDays(1), workSchedule.get(newBookingLDTStart.getDayOfWeek()).getCloseTime()).isAfter(newBookingLDTEnd)
                        && LocalDateTime.of(LocalDate.parse(bookingRequestDto.getDateStart()), workSchedule.get(newBookingLDTStart.getDayOfWeek()).getOpenTime()).isBefore(newBookingLDTStart);

            } else {
                isValid = LocalDateTime.of(LocalDate.parse(bookingRequestDto.getDateStart()), workSchedule.get(newBookingLDTStart.getDayOfWeek()).getOpenTime()).isBefore(newBookingLDTStart)
                        && LocalDateTime.of(LocalDate.parse(bookingRequestDto.getDateEnd()), workSchedule.get(newBookingLDTEnd.getDayOfWeek()).getCloseTime()).isAfter(newBookingLDTEnd);
            }
        } else {
            isValid = false;
        }

        if (isValid) {
            for (Booking booking : bookingList) {
                LocalDateTime existingBookingLDTStart = LocalDateTime.of(booking.getDateStart(), booking.getTimeStart());
                LocalDateTime existingBookingLDTEnd = LocalDateTime.of(booking.getDateEnd(), booking.getTimeEnd());
                if ((newBookingLDTStart.isBefore(existingBookingLDTStart) && newBookingLDTEnd.isAfter(existingBookingLDTStart))
                        || (newBookingLDTStart.isBefore(existingBookingLDTEnd) && newBookingLDTEnd.isAfter(existingBookingLDTStart))
                        || (newBookingLDTStart.isEqual(existingBookingLDTEnd)) || (newBookingLDTEnd.isEqual(existingBookingLDTStart))
                        || (newBookingLDTStart.isEqual(existingBookingLDTStart))) {
                    isValid = false;
                    break;
                }
            }
        }

        if (isValid) {
            if (newBookingDateStart.isEqual(newBookingDateEnd)) {
                cost = (Math.toIntExact(ChronoUnit.MINUTES.between(newBookingLDTStart, newBookingLDTEnd)) / 60) *
                        complexRepository.findComplexById(bookingRequestDto.getComplexId()).orElseThrow().getSchedule().getHourCost().get(newBookingDateStart.getDayOfWeek());
            } else {
                cost = (Math.toIntExact(ChronoUnit.MINUTES.between(newBookingLDTStart, LocalDateTime.of(newBookingDateStart, midnight))) / 60)
                        * complexRepository.findComplexById((bookingRequestDto.getComplexId())).orElseThrow().getSchedule().getHourCost().get(newBookingDateStart.getDayOfWeek())
                        + (Math.toIntExact(ChronoUnit.MINUTES.between(LocalDateTime.of(newBookingDateStart, midnight), newBookingLDTEnd)) / 60)
                        * (complexRepository.findComplexById((bookingRequestDto.getComplexId())).orElseThrow().getSchedule().getHourCost().get(newBookingDateEnd.getDayOfWeek()));
            }
        }
        map.put(isValid, cost);
        return map;
    }
}
