package ru.berrington.coworkingspace.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.berrington.coworkingspace.models.Room;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CoworkingDTO {

    @NotEmpty(message = "Название не долнжо быть пустым")
    private String name;

    private List<Room> rooms;
}
