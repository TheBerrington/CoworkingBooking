package ru.berrington.coworkingspace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.berrington.coworkingspace.models.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

}
