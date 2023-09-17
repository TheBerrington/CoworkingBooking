package ru.berrington.coworkingspace.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.berrington.coworkingspace.dto.CoworkingDTO;
import ru.berrington.coworkingspace.services.CoworkingService;
import ru.berrington.coworkingspace.util.exceptions.CoworkingErrorResponse;
import ru.berrington.coworkingspace.util.exceptions.CoworkingNotCreatedException;
import ru.berrington.coworkingspace.util.exceptions.CoworkingNotFoundException;
import ru.berrington.coworkingspace.util.exceptions.CoworkingNotUpdatedException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coworkings")
public class CoworkingsController {

    private final CoworkingService coworkingService;

    @GetMapping()
    public List<CoworkingDTO> getAllCoworkings(){
        return coworkingService.findAll().stream().map(b ->
                coworkingService.convertToCoworkingDTO(b)).collect(Collectors.toList());
    }

    //TODO
    @GetMapping("{id}")
    public CoworkingDTO getCoworking(@PathVariable("id") int coworkingId){
        return coworkingService.convertToCoworkingDTO(coworkingService.findById(coworkingId).orElse(null));
    }

    @ExceptionHandler
    private ResponseEntity<CoworkingErrorResponse> handlerException(CoworkingNotFoundException e){
        CoworkingErrorResponse response = new CoworkingErrorResponse(
                "Coworking with this id wasn't found!",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/new")
    public CoworkingDTO newCoworking(){
        return new CoworkingDTO();
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> createCoworking(@RequestBody CoworkingDTO coworkingDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder errorSB = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error: errors){
                errorSB.append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new CoworkingNotCreatedException(errorSB.toString());
        }
        coworkingService.save(coworkingService.convertToCoworking(coworkingDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<CoworkingErrorResponse> handlerException(CoworkingNotCreatedException e){
        CoworkingErrorResponse response = new CoworkingErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    //TODO
    @GetMapping("/{id}/edit")
    public CoworkingDTO editCoworking(@PathVariable("id") long id){
        return coworkingService.convertToCoworkingDTO(coworkingService.findById(id).orElse(null));
    }


    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> updateCoworking(@RequestBody CoworkingDTO coworkingDTO, BindingResult bindingResult, @PathVariable("id") long id){
        if(bindingResult.hasErrors()){
            StringBuilder errorSB = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error: errors){
                errorSB.append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new CoworkingNotUpdatedException(errorSB.toString());
        }
        coworkingService.update(id, coworkingService.convertToCoworking(coworkingDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") long id) {
        coworkingService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }


}
