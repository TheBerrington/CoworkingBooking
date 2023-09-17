package ru.berrington.coworkingspace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.berrington.coworkingspace.models.Coworking;

@Repository
public interface CoworkingRepository extends JpaRepository<Coworking, Long> {

}
