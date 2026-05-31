package com.substring.irctc.services.impl;

import com.substring.irctc.Repository.StationRepo;
import com.substring.irctc.Repository.TrainRepo;
import com.substring.irctc.Repository.TrainScheduleRepo;
import com.substring.irctc.dto.AvailableTrainResponse;
import com.substring.irctc.dto.TrainDTO;
import com.substring.irctc.dto.UserTrainSearchRequest;
import com.substring.irctc.entity.*;
import com.substring.irctc.exception.ResourceNotFoundException;
import com.substring.irctc.services.TrainsService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class TrainServiceImpl implements TrainsService {

    private TrainRepo trainRepo;

    private ModelMapper modelMapper;

    private StationRepo stationRepo;

    private TrainScheduleRepo trainScheduleRepo;

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
        Train existingTrain = trainRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Train not found"));

        existingTrain.setName(trainDto.getName());
        existingTrain.setNumber(trainDto.getNumber());
        existingTrain.setTotalDistance(trainDto.getTotalDistance());

        // Null check before calling .getId()
        if (trainDto.getSourceStation() == null || trainDto.getSourceStation().getId() == null) {
            throw new IllegalArgumentException("Source station id is required");
        }
        if (trainDto.getDestinationStation() == null || trainDto.getDestinationStation().getId() == null) {
            throw new IllegalArgumentException("Destination station id is required");
        }

        Station sourceStation = stationRepo.findById(trainDto.getSourceStation().getId())
                .orElseThrow(() -> new RuntimeException("Source station not found"));
        Station destinationStation = stationRepo.findById(trainDto.getDestinationStation().getId())
                .orElseThrow(() -> new RuntimeException("Destination station not found"));

        existingTrain.setSourceStation(sourceStation);
        existingTrain.setDestinationStation(destinationStation);

        Train updatedTrain = trainRepo.save(existingTrain);
        return modelMapper.map(updatedTrain, TrainDTO.class);
    }

    @Override
    public void deleteTrain(Long id) {
      Train existingTrain = trainRepo.findById(id).orElseThrow(() -> new RuntimeException("Train not found"));
      trainRepo.delete(existingTrain);
    }

    // This method is for user to search for trains on specific date from source to destination with available seats
    @Override
    public List<AvailableTrainResponse> userTrainSearch(UserTrainSearchRequest request) {
        List<Train> matchedTrains = trainRepo.findTrainBySourceAndDestinationInOrder(
                request.getSourceStationId(), request.getDestinationStationId());

        List<Train> validTrains = new ArrayList<>();

        for (Train train : matchedTrains) {
            Integer sourceStationOrder = null;
            Integer destinationStationOrder = null;

            for (TrainRoute trainRoute : train.getRoutes()) {
                if (trainRoute.getStation().getId().equals(request.getSourceStationId())) {
                    sourceStationOrder = trainRoute.getStationOrder();
                } else if (trainRoute.getStation().getId().equals(request.getDestinationStationId())) {
                    destinationStationOrder = trainRoute.getStationOrder();
                }
            }

            boolean runOnThatDay = train.getSchedules().stream()
                    .anyMatch(sch -> sch.getRunDate().equals(request.getJourneyDate()));

            if (sourceStationOrder != null && destinationStationOrder != null
                    && sourceStationOrder < destinationStationOrder && runOnThatDay) {
                validTrains.add(train);
            }
        }

        List<AvailableTrainResponse> responseList = new ArrayList<>();

        for (Train train : validTrains) {
            TrainSchedule trainSchedule = train.getSchedules().stream()
                    .filter(sch -> sch.getRunDate().equals(request.getJourneyDate()))
                    .findFirst().orElse(null);

            if (trainSchedule == null) continue;

            TrainRoute sourceRoute = train.getRoutes().stream()
                    .filter(r -> r.getStation().getId().equals(request.getSourceStationId()))
                    .findFirst().orElse(null);

            TrainRoute destinationRoute = train.getRoutes().stream()
                    .filter(r -> r.getStation().getId().equals(request.getDestinationStationId()))
                    .findFirst().orElse(null);

            if (sourceRoute == null || destinationRoute == null) continue;

            Map<CoachType, Integer> seatMaps = new HashMap<>();
            Map<CoachType, Double> priceMaps = new HashMap<>();

            for (TrainSeat trainSeat : trainSchedule.getTrainSeats()) {
                seatMaps.merge(trainSeat.getCoachType(), trainSeat.getAvailableSeats(), Integer::sum);
                priceMaps.putIfAbsent(trainSeat.getCoachType(), trainSeat.getPrice());
            }

            AvailableTrainResponse availableTrainResponse = AvailableTrainResponse.builder()
                    .trainId(train.getId())
                    .trainNumber(train.getNumber())
                    .trainName(train.getName())          // ← ADD THIS
                    .departureTime(sourceRoute.getDepartureTime())
                    .arrivalTime(destinationRoute.getArrivalTime())  // ← also fix this
                    .availableSeats(seatMaps)
                    .priceByCoach(priceMaps)
                    .scheduledDate(trainSchedule.getRunDate())
                    .trainScheduleId(trainSchedule.getId())
                    .build();

            responseList.add(availableTrainResponse);
        }

        return responseList;
    }

}
