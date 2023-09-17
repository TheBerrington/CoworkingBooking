package ru.berrington.coworkingspace.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.berrington.coworkingspace.dto.RoomDTO;
import ru.berrington.coworkingspace.models.Room;
import ru.berrington.coworkingspace.repositories.RoomRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    public final RoomRepository roomRepository;
    public final ModelMapper modelMapper;

    @Autowired
    public RoomService(RoomRepository roomRepository, ModelMapper modelMapper) {
        this.roomRepository = roomRepository;
        this.modelMapper = modelMapper;
    }

    public void save(Room room ){
        roomRepository.save(room);
    }
    public void delete(long id){
        roomRepository.deleteById(id);
    }
    public Optional<Room> findById(long id){
        return roomRepository.findById(id);
    }
    public List<Room> findAll(){ return roomRepository.findAll(); }
    public List<Room> findAllWithCapacity(int capacity){
        return roomRepository.findByCapacityGreaterThan(capacity);
    }

    public void update(long id, Room updatingRoom){
        updatingRoom.setId(id);
        roomRepository.save(updatingRoom);
    }

    public RoomDTO convertToRoomDTO(Room room) {
        return modelMapper.map(room,RoomDTO.class);
    }

    public Room convertToRoom(RoomDTO roomDTO) {
        return modelMapper.map(roomDTO,Room.class);
    }

}
