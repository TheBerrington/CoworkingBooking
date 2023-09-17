package ru.berrington.coworkingspace.util.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CoworkingErrorResponse {
    private String message;
    private long timestamp;
}
