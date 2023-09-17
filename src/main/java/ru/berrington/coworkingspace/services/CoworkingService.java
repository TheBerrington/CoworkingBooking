package ru.berrington.coworkingspace.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.berrington.coworkingspace.dto.CoworkingDTO;
import ru.berrington.coworkingspace.models.Coworking;
import ru.berrington.coworkingspace.repositories.CoworkingRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CoworkingService {
    public final CoworkingRepository coworkingRepository;
    public final ModelMapper modelMapper;

    @Autowired
    public CoworkingService(CoworkingRepository coworkingRepository, ModelMapper modelMapper) {
        this.coworkingRepository = coworkingRepository;
        this.modelMapper = modelMapper;
    }

    public void save(Coworking coworking ){
        coworkingRepository.save(coworking);
    }
    public void delete(long id){
        coworkingRepository.deleteById(id);
    }
    public Optional<Coworking> findById(long id){
        return coworkingRepository.findById(id);
    }
    public List<Coworking> findAll(){
        return coworkingRepository.findAll();
    }

    public void update(long id, Coworking updatingCoworking){
        updatingCoworking.setId(id);
        coworkingRepository.save(updatingCoworking);
    }

    public CoworkingDTO convertToCoworkingDTO(Coworking coworking) {
        return modelMapper.map(coworking,CoworkingDTO.class);
    }

    public Coworking convertToCoworking(CoworkingDTO coworkingDTO) {
        return modelMapper.map(coworkingDTO,Coworking.class);
    }

}
