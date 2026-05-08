package com.substring.irctc.services;

import com.substring.irctc.dto.TrainSeatDto;

import java.util.List;

public interface TrainSeatService {
    TrainSeatDto createSeatInfo(TrainSeatDto trainSeatDto);

    List<TrainSeatDto> getSeatInfoByTrainId(Long scheduleId);

    void deleteSeatInfo(Long seatId);

    TrainSeatDto updateSeatInfo(Long seatId, TrainSeatDto trainSeatDto);

    List<Integer> bookSeat (int seatBook, Long seatId);

}
