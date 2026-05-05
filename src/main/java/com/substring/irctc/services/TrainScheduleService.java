package com.substring.irctc.services;

import com.substring.irctc.dto.TrainScheduleDto;
import com.substring.irctc.entity.TrainSchedule;

import java.util.List;

public interface TrainScheduleService {
    TrainScheduleDto createSchedule(TrainScheduleDto trainScheduleDto);

    List<TrainScheduleDto> findByTrainScheduleId(Long trainId);

    void deleteTrainScheduleById(Long trainScheduleId);

    TrainScheduleDto updateTrainSchedule(Long trainScheduleId, TrainScheduleDto trainScheduleDto);
}
