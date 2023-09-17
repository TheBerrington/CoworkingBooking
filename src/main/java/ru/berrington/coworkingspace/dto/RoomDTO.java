package ru.berrington.coworkingspace.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.berrington.coworkingspace.models.Coworking;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {

    @Min(value = 1, message = "Комната должна вмещать более 1 человека")
    @Max(value = 20, message = "Комната не должна вмещать более 20 людей")
    private int capacity;
    private Coworking coworking;
}
