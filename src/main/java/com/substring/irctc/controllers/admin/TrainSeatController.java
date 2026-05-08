package com.substring.irctc.controllers.admin;

import com.substring.irctc.dto.TrainSeatDto;
import com.substring.irctc.services.TrainSeatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/train-seats")
public class TrainSeatController {
    private TrainSeatService trainSeatService;

    public TrainSeatController(TrainSeatService trainSeatService) {
        this.trainSeatService = trainSeatService;
    }


    // create

    @PostMapping
    public ResponseEntity<TrainSeatDto> createSeat(@RequestBody  TrainSeatDto trainSeatDto) {
        TrainSeatDto createSeat = trainSeatService.createSeatInfo(trainSeatDto);
        return   ResponseEntity.status(201).body(createSeat);
    }

    // get dibba of train schedule
    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<List<TrainSeatDto>> getSeatBySchedule(@PathVariable Long scheduleId) {
        List<TrainSeatDto> seatDtos = trainSeatService.getSeatInfoByTrainId(scheduleId);
        return ResponseEntity.ok(seatDtos);
    }
    //delete seat info
    @DeleteMapping("/{seatId}")
    public ResponseEntity<Void> deleteSeat(@PathVariable Long seatId) {
        trainSeatService.deleteSeatInfo(seatId);
        return ResponseEntity.noContent().build();
    }

    //update seat info
    @PutMapping("/{seatId}")
    public ResponseEntity<TrainSeatDto> updateSeatInfo(@PathVariable Long seatId, @RequestBody  TrainSeatDto trainSeatDto) {
        TrainSeatDto updateSeat = trainSeatService.updateSeatInfo(seatId, trainSeatDto);
        return ResponseEntity.ok(updateSeat);
    }
}
