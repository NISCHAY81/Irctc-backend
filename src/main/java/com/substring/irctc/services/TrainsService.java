package com.substring.irctc.services;

import com.substring.irctc.dto.TrainDTO;

import java.util.List;

public interface TrainsService {
    public TrainDTO createTrain(TrainDTO trainDto);

    public List<TrainDTO> getAllTrains();

    public TrainDTO getTrainByTrainNo(Long id);

    public TrainDTO updateTrain(Long id, TrainDTO trainDto);

    public void deleteTrain(Long id);
}
