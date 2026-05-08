package com.substring.irctc.services.impl;

import com.substring.irctc.Repository.TrainScheduleRepo;
import com.substring.irctc.Repository.TrainSeatRepo;
import com.substring.irctc.dto.TrainSeatDto;
import com.substring.irctc.entity.TrainSchedule;
import com.substring.irctc.entity.TrainSeat;
import com.substring.irctc.exception.ResourceNotFoundException;
import com.substring.irctc.services.TrainSeatService;
import jakarta.transaction.Transactional;
import lombok.Synchronized;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainSeatServiceImpl implements TrainSeatService {
    private TrainSeatRepo  trainSeatRepo;

    private TrainScheduleRepo trainScheduleRepo;

    private ModelMapper modelMapper;

    public TrainSeatServiceImpl(TrainSeatRepo trainSeatRepo, TrainScheduleRepo trainScheduleRepo, ModelMapper modelMapper) {
        this.trainSeatRepo = trainSeatRepo;
        this.trainScheduleRepo = trainScheduleRepo;
        this.modelMapper = modelMapper;
    }


    @Override
    public TrainSeatDto createSeatInfo(TrainSeatDto trainSeatDto) {
       TrainSchedule trainSchedule =  trainScheduleRepo.findById(trainSeatDto.getTrainScheduleId()).orElseThrow(()-> new ResourceNotFoundException("Train Schedule not found with id " + trainSeatDto.getTrainScheduleId()));
       TrainSeat trainSeat = modelMapper.map(trainSeatDto, TrainSeat.class);
       trainSeat.setTrainSchedule(trainSchedule);
        TrainSeat savedTrainSeat = trainSeatRepo.save(trainSeat);
        return modelMapper.map(savedTrainSeat, TrainSeatDto.class);
    }

    @Override
    public List<TrainSeatDto> getSeatInfoByTrainId(Long scheduleId) {
        List<TrainSeat> trainSeats = trainSeatRepo.findByTrainScheduleId(scheduleId);
        return trainSeats.stream().map(trainSeat -> modelMapper.map(trainSeat, TrainSeatDto.class)).toList();
    }

    @Override
    public void deleteSeatInfo(Long seatId) {
    TrainSeat seat =  trainSeatRepo.findById(seatId).orElseThrow(()-> new ResourceNotFoundException("Train Seat not found with id " + seatId));
     trainSeatRepo.delete(seat);
    }

    @Override
    public TrainSeatDto updateSeatInfo(Long seatId, TrainSeatDto trainSeatDto) {
        TrainSeat seat = trainSeatRepo.findById(seatId).orElseThrow(()-> new ResourceNotFoundException("Train Seat not found with id " + seatId));
        TrainSchedule trainSchedule = trainScheduleRepo.findById(trainSeatDto.getTrainScheduleId()).orElseThrow(()-> new ResourceNotFoundException("Train Schedule not found with id " + trainSeatDto.getTrainScheduleId()));
        seat.setTrainSchedule(trainSchedule);
        seat.setCoachType(trainSeatDto.getCoachType());
        seat.setAvailableSeats(trainSeatDto.getAvailableSeats());
        seat.setPrice(trainSeatDto.getPrice());
        seat.setTotalSeats(trainSeatDto.getTotalSeats());
        seat.setTrainSeatOrder(trainSeatDto.getTrainSeatOrder());
        seat.setSeatNumberToAssign(trainSeatDto.getSeatNumberToAssign());
        TrainSeat savedTrainSeat = trainSeatRepo.save(seat);
        return modelMapper.map(savedTrainSeat, TrainSeatDto.class);
    }
    @Synchronized
    @Transactional
    public List<Integer> bookSeat (int seatToBook, Long seatId) {
        TrainSeat trainSeat = trainSeatRepo.findById(seatId).orElseThrow(()->new ResourceNotFoundException("Seat not found"));
        if(trainSeat.isSeatAvailable(seatToBook) ) {
            trainSeat.setAvailableSeats(trainSeat.getAvailableSeats() - seatToBook);
            List<Integer> bookSeats = new ArrayList<>();
            for(int i = 1; i < seatToBook; i++) {
                bookSeats.add(trainSeat.getSeatNumberToAssign());
               trainSeat.setSeatNumberToAssign(trainSeat.getSeatNumberToAssign() + 1);
            }
            trainSeatRepo.save(trainSeat);
            return bookSeats;
        } else {
            throw  new IllegalStateException("No seats available");
        }
    }
}
