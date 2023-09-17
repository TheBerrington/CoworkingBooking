package ru.berrington.coworkingspace.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.berrington.coworkingspace.models.Room;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Comparator;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private String owner;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startDate;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endDate;

    private Room room;

}

class startAndEndTimeComparator implements Comparator<BookingDTO>{
    
    @Override
    public int compare(BookingDTO bookingDTO1, BookingDTO bookingDTO2) {
        return bookingDTO1.getStartDate()
    }
}
