package ru.berrington.coworkingspace.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.berrington.coworkingspace.dto.BookingDTO;
import ru.berrington.coworkingspace.services.BookingService;
import ru.berrington.coworkingspace.services.RoomService;
import ru.berrington.coworkingspace.util.exceptions.BookingErrorResponse;
import ru.berrington.coworkingspace.util.exceptions.BookingNotCreatedException;
import ru.berrington.coworkingspace.util.exceptions.BookingNotFoundException;
import ru.berrington.coworkingspace.util.exceptions.BookingNotUpdatedException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class BookingsController {

    private final BookingService bookingService;
    private final RoomService roomService;

    @GetMapping()
    public List<BookingDTO> getAllBookings() {
        return bookingService.findAll().stream().map(b ->
                bookingService.convertToBookingDTO(b)).collect(Collectors.toList());
    }

    //TODO
    @GetMapping("{id}")
    public BookingDTO getBooking(@PathVariable("id") int bookingId) {
        return bookingService.convertToBookingDTO(bookingService.findById(bookingId).orElse(null));
    }

    @ExceptionHandler
    private ResponseEntity<BookingErrorResponse> handlerException(BookingNotFoundException e) {
        BookingErrorResponse response = new BookingErrorResponse(
                "Booking with this id wasn't found!",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/new")
    public BookingDTO newBooking() {
        return new BookingDTO();
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> createBooking(@RequestBody BookingDTO bookingDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorSB = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorSB.append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new BookingNotCreatedException(errorSB.toString());
        }
        bookingService.save(bookingService.convertToBooking(bookingDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<BookingErrorResponse> handlerException(BookingNotCreatedException e) {
        BookingErrorResponse response = new BookingErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    //TODO
    @GetMapping("/{id}/edit")
    public BookingDTO editBooking(@PathVariable("id") long id) {
        return bookingService.convertToBookingDTO(bookingService.findById(id).orElse(null));
    }


    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> updateBooking(@RequestBody BookingDTO bookingDTO, BindingResult bindingResult, @PathVariable("id") long id) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorSB = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorSB.append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new BookingNotUpdatedException(errorSB.toString());
        }
        bookingService.update(id, bookingService.convertToBooking(bookingDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") long id) {
        bookingService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
