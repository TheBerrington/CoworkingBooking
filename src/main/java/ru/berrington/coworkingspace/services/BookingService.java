package ru.berrington.coworkingspace.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.berrington.coworkingspace.dto.BookingDTO;
import ru.berrington.coworkingspace.models.Booking;
import ru.berrington.coworkingspace.repositories.BookingRepository;
import ru.berrington.coworkingspace.util.exceptions.BadStartAndEndTimeException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    public final BookingRepository bookingRepository;
    public final ModelMapper modelMapper;

    @Autowired
    public BookingService(BookingRepository bookingRepository, ModelMapper modelMapper) {
        this.bookingRepository = bookingRepository;
        this.modelMapper = modelMapper;
    }

    public void save(Booking booking ){
        LocalDateTime from = booking.getStartTime();
        LocalDateTime until = booking.getEndTime();
        if (until.isBefore(from) || until.isEqual(from)) {
            throw new BadStartAndEndTimeException("Время бронирование до не может быть позже времени бронирования после");
        }

        if (until.getMinute() % 30 != 0 || from.getMinute() % 30 != 0) {
            throw new BadStartAndEndTimeException("Шаг бронирования должен составлять 30 минут");
        }

        bookingRepository.save(booking);
    }

    public void delete(long id){
        bookingRepository.deleteById(id);
    }
    public Optional<Booking> findById(long id){
        return bookingRepository.findById(id);
    }
    public List<Booking> findAll(){
        return bookingRepository.findAll();
    }

    public void update(long id, Booking updatingBooking){
        updatingBooking.setId(id);
        bookingRepository.save(updatingBooking);
    }

    public BookingDTO convertToBookingDTO(Booking booking) {
        return modelMapper.map(booking,BookingDTO.class);
    }

    public Booking convertToBooking(BookingDTO bookingDTO) {
        return modelMapper.map(bookingDTO,Booking.class);
    }

}
