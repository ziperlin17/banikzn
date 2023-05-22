package bani.lux.banikzn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookingDto {
    //     { "title": "запривачено",
//     "url": "https://google.com/",
//     "start": "2023-05-22 13:00",
//     "end": "2023-05-22 22:00" }
    private String start;
    private String end;

}
