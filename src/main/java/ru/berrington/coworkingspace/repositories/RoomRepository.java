package ru.berrington.coworkingspace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.berrington.coworkingspace.models.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

}
