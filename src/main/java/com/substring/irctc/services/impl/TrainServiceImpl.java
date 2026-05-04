package com.substring.irctc.services.impl;

import com.substring.irctc.Repository.StationRepo;
import com.substring.irctc.Repository.TrainRepo;
import com.substring.irctc.dto.TrainDTO;
import com.substring.irctc.entity.Station;
import com.substring.irctc.entity.Train;
import com.substring.irctc.exception.ResourceNotFoundException;
import com.substring.irctc.services.TrainsService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TrainServiceImpl implements TrainsService {

    private TrainRepo trainRepo;

    private ModelMapper modelMapper;

    private StationRepo stationRepo;


    @Override
    public TrainDTO createTrain(TrainDTO trainDto) {
      Long sid  = trainDto.getSourceStation().getId();
      Long did = trainDto.getDestinationStation().getId();
      Station sourceStation = stationRepo.findById(sid).orElseThrow(() -> new ResourceNotFoundException("Source station not found"));
      Station destinationStation = stationRepo.findById(did).orElseThrow(() -> new ResourceNotFoundException("Destination station not found"));
      Train train = modelMapper.map(trainDto, Train.class);
      train.setSourceStation( sourceStation);
      train.setDestinationStation(destinationStation);
      Train savedTrain = trainRepo.save(train);
        return  modelMapper.map(savedTrain, TrainDTO.class);
    }

    @Override
    public List<TrainDTO> getAllTrains() {
        List<Train> all = trainRepo.findAll();
        return all.stream().map(train -> modelMapper.map(train,TrainDTO.class)).toList();
    }

    @Override
    public TrainDTO getTrainByTrainNo(Long id) {
        Train train = trainRepo.findById(id).orElseThrow(() -> new RuntimeException("Train not found"));
        return modelMapper.map(train,TrainDTO.class);
    }

    @Override
    public TrainDTO updateTrain(Long id, TrainDTO trainDto) {
         Train existingTrain = trainRepo.findById(id).orElseThrow(() -> new RuntimeException("Train not found"));
         existingTrain.setName(trainDto.getName());
         existingTrain.setNumber(trainDto.getNumber());
         existingTrain.setTotalDistance(trainDto.getTotalDistance());

         //fetch source and destination
        Station sourceStation = stationRepo.findById(trainDto.getSourceStation().getId()).orElseThrow(() -> new RuntimeException("Source station not found"));
        Station destinationStation = stationRepo.findById(trainDto.getDestinationStation().getId()).orElseThrow(() -> new RuntimeException("Destination station not found"));

        existingTrain.setSourceStation(sourceStation);
        existingTrain.setDestinationStation(destinationStation);

        Train updatedTrain = trainRepo.save(existingTrain);
        return modelMapper.map(updatedTrain,TrainDTO.class);
    }

    @Override
    public void deleteTrain(Long id) {
      Train existingTrain = trainRepo.findById(id).orElseThrow(() -> new RuntimeException("Train not found"));
      trainRepo.delete(existingTrain);
    }
}
