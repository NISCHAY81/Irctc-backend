package com.substring.irctc.services;

import com.substring.irctc.dto.TrainDTO;

import java.util.List;

public interface TrainsService {
    public TrainDTO create(TrainDTO train);

    public List<TrainDTO> getAllTrains();

    public TrainDTO getTrainByTrainNo(Long id);

    public TrainDTO updateTrain(Long id, TrainDTO train);

    public void deleteTrain(Long id);
}
