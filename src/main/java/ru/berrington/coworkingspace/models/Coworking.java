package ru.berrington.coworkingspace.models;

import lombok.*;
import ru.berrington.coworkingspace.abstractions.AbstractModel;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "coworking")
public class Coworking extends AbstractModel{

    private String name;

    @OneToMany(mappedBy = "coworking", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Room> rooms;
}