package ru.berrington.coworkingspace.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.berrington.coworkingspace.abstractions.AbstractModel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "booking")
public class Booking extends AbstractModel {

    private String owner;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime;

    @ManyToOne
    private Room room;
}

