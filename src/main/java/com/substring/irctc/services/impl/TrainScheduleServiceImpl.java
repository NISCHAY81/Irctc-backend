package com.substring.irctc.services.impl;

import com.substring.irctc.Repository.TrainRepo;
import com.substring.irctc.Repository.TrainScheduleRepo;
import com.substring.irctc.dto.TrainScheduleDto;
import com.substring.irctc.entity.Train;
import com.substring.irctc.entity.TrainSchedule;
import com.substring.irctc.exception.ResourceNotFoundException;
import com.substring.irctc.services.TrainScheduleService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TrainScheduleServiceImpl implements TrainScheduleService {
    private TrainRepo trainRepo;
    private TrainScheduleRepo trainScheduleRepo;
    private ModelMapper modelMapper;
    @Override
    public TrainScheduleDto createSchedule(TrainScheduleDto trainScheduleDto) {
      Train train = trainRepo.findById(trainScheduleDto.getTrainId()).orElseThrow(() -> new ResourceNotFoundException("Train Schedule Not Found"));
     TrainSchedule trainSchedule =  modelMapper.map(trainScheduleDto, TrainSchedule.class);
     trainSchedule.setTrain(train);
    TrainSchedule savedSchedule = trainScheduleRepo.save(trainSchedule);
        return modelMapper.map(savedSchedule, TrainScheduleDto.class);
    }

    @Override
    public List<TrainScheduleDto> findByTrainScheduleId(Long trainId) {
        List<TrainSchedule> trainSchedules = trainScheduleRepo.findByTrainId(trainId);
        return trainSchedules.stream().map(trainSchedule -> modelMapper.map(trainSchedule,TrainScheduleDto.class)).toList();
    }


    @Override
    public void deleteTrainScheduleById(Long trainScheduleId) {
        TrainSchedule trainSchedule = trainScheduleRepo.findById(trainScheduleId).orElseThrow(() -> new ResourceNotFoundException("Train Schedule Not Found"));
        trainScheduleRepo.delete(trainSchedule);

    }

    @Override
    public TrainScheduleDto updateTrainSchedule(Long trainScheduleId, TrainScheduleDto trainScheduleDto) {
        TrainSchedule trainSchedule = trainScheduleRepo.findById(trainScheduleId).orElseThrow(() -> new ResourceNotFoundException("Train Schedule Not Found"));
        Train train = trainRepo.findById(trainScheduleDto.getTrainId()).orElseThrow(()-> new ResourceNotFoundException("Train Schedule Not Found"));
        trainSchedule.setTrain(train);
       trainSchedule.setAvailableSeats(trainScheduleDto.getAvailableSeats());
       trainSchedule.setRunDate(trainScheduleDto.getRunDate());
       TrainSchedule savedSchedule = trainScheduleRepo.save(trainSchedule);
        return modelMapper.map(savedSchedule, TrainScheduleDto.class);
    }
}
