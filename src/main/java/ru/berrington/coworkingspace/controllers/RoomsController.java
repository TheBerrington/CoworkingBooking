package ru.berrington.coworkingspace.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.berrington.coworkingspace.dto.BookingDTO;
import ru.berrington.coworkingspace.dto.RoomDTO;
import ru.berrington.coworkingspace.models.Room;
import ru.berrington.coworkingspace.services.BookingService;
import ru.berrington.coworkingspace.services.RoomService;
import ru.berrington.coworkingspace.util.exceptions.RoomErrorResponse;
import ru.berrington.coworkingspace.util.exceptions.RoomNotCreatedException;
import ru.berrington.coworkingspace.util.exceptions.RoomNotFoundException;
import ru.berrington.coworkingspace.util.exceptions.RoomNotUpdatedException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomsController {

    private final RoomService roomService;
    private final BookingService bookingService;

    @GetMapping()
    public List<RoomDTO> getAllRooms(){
        return roomService.findAll().stream().map(b ->
                roomService.convertToRoomDTO(b)).collect(Collectors.toList());
    }

    //TODO
    @GetMapping("{id}")
    public RoomDTO getRoom(@PathVariable("id") int roomId){
        return roomService.convertToRoomDTO(roomService.findById(roomId).orElse(null));
    }

    @ExceptionHandler
    private ResponseEntity<RoomErrorResponse> handlerException(RoomNotFoundException e){
        RoomErrorResponse response = new RoomErrorResponse(
                "Room with this id wasn't found!",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/new")
    public RoomDTO newRoom(){
        return new RoomDTO();
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> createRoom(@RequestBody RoomDTO roomDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder errorSB = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error: errors){
                errorSB.append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new RoomNotCreatedException(errorSB.toString());
        }
        roomService.save(roomService.convertToRoom(roomDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<RoomErrorResponse> handlerException(RoomNotCreatedException e){
        RoomErrorResponse response = new RoomErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    //TODO
    @GetMapping("/{id}/edit")
    public RoomDTO editRoom(@PathVariable("id") long id){
        return roomService.convertToRoomDTO(roomService.findById(id).orElse(null));
    }


    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> updateRoom(@RequestBody RoomDTO roomDTO, BindingResult bindingResult, @PathVariable("id") long id){
        if(bindingResult.hasErrors()){
            StringBuilder errorSB = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error: errors){
                errorSB.append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new RoomNotUpdatedException(errorSB.toString());
        }
        roomService.update(id, roomService.convertToRoom(roomDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") long id) {
        roomService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @GetMapping("/free")
    public List<RoomDTO> getAllFreeRooms(@RequestParam(value = "capacity", required = false) Integer capacity,
                                               @RequestParam(value = "startTime") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime startTime,
                                               @RequestParam(value = "endTime") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime endTime) {
        List<Room> freeRooms;
        if(capacity != null){
            freeRooms = roomService.findAllWithCapacity(capacity);
        } else{
            freeRooms = roomService.findAll();
        }

        List<BookingDTO> bookedSlots = bookingService.findAll().stream().map(b
                -> bookingService.convertToBookingDTO(b)).collect(Collectors.toList());
        for(BookingDTO b: bookedSlots){
            if(((startTime.isEqual(b.getStartTime()) || startTime.isAfter(b.getStartTime())) && startTime.isBefore(b.getEndTime())) || ((endTime.isEqual(b.getEndTime()) || endTime.isBefore(b.getEndTime())) && endTime.isAfter(b.getStartTime()))){
                freeRooms.removeIf(room -> room.getId() == b.getRoom().getId());
            }
        }
        return freeRooms.stream().map(b -> roomService.convertToRoomDTO(b)).collect(Collectors.toList());
    }


}
