package com.substring.irctc.services.impl;

import com.substring.irctc.Repository.StationRepo;
import com.substring.irctc.Repository.TrainRepo;
import com.substring.irctc.Repository.TrainRouteRepository;
import com.substring.irctc.dto.TrainRouteDto;
import com.substring.irctc.entity.Station;
import com.substring.irctc.entity.Train;
import com.substring.irctc.entity.TrainRoute;
import com.substring.irctc.exception.ResourceNotFoundException;
import com.substring.irctc.services.TrainRRouteService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TrainRouteServiceImpl implements TrainRRouteService {
    private TrainRepo  trainRepo;
    private StationRepo stationRepo;
    private TrainRouteRepository  trainRouteRepository;
    private ModelMapper  modelMapper;

    @Override
    public TrainRouteDto addRoute(TrainRouteDto dto) {
        Train train =  trainRepo.findById(dto.getTrain().getId()).orElseThrow(()-> new ResourceNotFoundException("Train Not Found"));
         Station station = stationRepo.findById(dto.getStation().getId()).orElseThrow(()-> new ResourceNotFoundException("Station Not Found"));
         // convert
         TrainRoute trainRoute = modelMapper.map(dto, TrainRoute.class);
         trainRoute.setTrain(train);
         trainRoute.setStation(station);
         // save the trainRoute entity
        TrainRoute savedTrainRoute = trainRouteRepository.save(trainRoute);

        TrainRouteDto savedTrainRouteDto = modelMapper.map(savedTrainRoute, TrainRouteDto.class);

        return savedTrainRouteDto;
    }

    @Override
    public List<TrainRouteDto> getRoutesByTrain(Long trainId) {
        Train train =  trainRepo.findById(trainId).orElseThrow(()-> new ResourceNotFoundException("Train Not Found"));
       List<TrainRoute> trainRoutes =  trainRouteRepository.findByTrainId(trainId);
  List<TrainRouteDto> trainRouteDtos = trainRoutes.stream().map(trainRoute -> modelMapper.map(trainRoute, TrainRouteDto.class)).toList();
        return trainRouteDtos;
    }

    @Override
    public TrainRouteDto updateRoute(Long id, TrainRouteDto dto) {
        TrainRoute existingRoute = trainRouteRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Train Not Found"));
        Station station = stationRepo.findById(dto.getStation().getId()).orElseThrow(() -> new ResourceNotFoundException("Station Not Found"));
        Train train = trainRepo.findById(dto.getTrain().getId()).orElseThrow(() -> new ResourceNotFoundException("Train Not Found"));
        //update
        existingRoute.setStation(station);
        existingRoute.setTrain(train);
        existingRoute.setStationOrder(dto.getStationOrder());
        existingRoute.setArrivalTime(dto.getArrivalTime());
        existingRoute.setDepartureTime(dto.getDepartureTime());
        existingRoute.setHaltMinutes(dto.getHaltMinutes());
        existingRoute.setDistanceFromSource(dto.getDistanceFromSource());
        TrainRoute updatedTrainRoute = trainRouteRepository.save(existingRoute);
        TrainRouteDto  updatedTrainRouteDto = modelMapper.map(updatedTrainRoute, TrainRouteDto.class);
        return updatedTrainRouteDto;
    }

    @Override
    public void deleteRoute(Long id) {
        TrainRoute existingRoute = trainRouteRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Train Not Found"));
        trainRouteRepository.delete(existingRoute);
    }
}
