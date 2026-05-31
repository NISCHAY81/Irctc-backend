package com.substring.irctc.services;

import com.substring.irctc.dto.AvailableTrainResponse;
import com.substring.irctc.dto.TrainDTO;
import com.substring.irctc.dto.UserTrainSearchRequest;

import java.time.LocalDate;
import java.util.List;

public interface TrainsService {
    public TrainDTO createTrain(TrainDTO trainDto);

    public List<TrainDTO> getAllTrains();

    public TrainDTO getTrainByTrainNo(Long id);

    public TrainDTO updateTrain(Long id, TrainDTO trainDto);

    public void deleteTrain(Long id);

    // search train for booking
public List<AvailableTrainResponse> userTrainSearch(UserTrainSearchRequest userTrainSearchRequest);

}
