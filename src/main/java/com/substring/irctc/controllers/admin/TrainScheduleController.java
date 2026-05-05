package com.substring.irctc.controllers.admin;

import com.substring.irctc.dto.TrainScheduleDto;
import com.substring.irctc.entity.Train;
import com.substring.irctc.services.TrainScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin/train-schedules")
public class TrainScheduleController {

    private TrainScheduleService trainScheduleService;

    public TrainScheduleController(TrainScheduleService trainScheduleService) {
        this.trainScheduleService = trainScheduleService;
    }

    @PostMapping
    public ResponseEntity<TrainScheduleDto> createTrainSchedule(@RequestBody TrainScheduleDto trainScheduleDto) {
      TrainScheduleDto createSchedule  = trainScheduleService.createSchedule(trainScheduleDto);
      return new ResponseEntity<>(createSchedule, HttpStatus.CREATED);
    }


    // get train schedules by train ID
     @GetMapping("/train/{trainId}")
    public List<TrainScheduleDto> getTrainScheduleByTrainId(@PathVariable Long trainId) {
        return trainScheduleService.findByTrainScheduleId(trainId);
     }

     // delete Train
    @DeleteMapping("/{trainScheduleId}")
    public ResponseEntity<TrainScheduleDto> deleteTrainSchedule(@PathVariable Long trainScheduleId) {
         trainScheduleService.deleteTrainScheduleById(trainScheduleId);
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //update Train Schedule
    @PutMapping("/{trainScheduleId}")
    public ResponseEntity<TrainScheduleDto>  updateTrainSchedule(@PathVariable Long trainScheduleId, @RequestBody TrainScheduleDto trainScheduleDto) {
        TrainScheduleDto updateSchedule = trainScheduleService.updateTrainSchedule(trainScheduleId, trainScheduleDto);
        return new ResponseEntity<>(updateSchedule, HttpStatus.OK);
    }
}
