package ru.berrington.coworkingspace.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ru.berrington.coworkingspace.abstractions.AbstractModel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "room")
public class Room extends AbstractModel {

    private int capacity;

    @JsonIgnore
    @ManyToOne
    private Coworking coworking;
}
