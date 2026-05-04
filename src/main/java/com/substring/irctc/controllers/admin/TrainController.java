package com.substring.irctc.controllers.admin;

import com.substring.irctc.dto.TrainDTO;
import com.substring.irctc.services.TrainsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("adminTrainController")
@RequestMapping("/admin/trains")
public class TrainController {

    private TrainsService trainsService;

    public TrainController(TrainsService trainsService) {
        this.trainsService = trainsService;
    }
    // create
    @PostMapping
    public ResponseEntity<TrainDTO> createTrain(@RequestBody TrainDTO trainDTO)
    {
        return  new ResponseEntity<>(trainsService.createTrain(trainDTO), HttpStatus.CREATED);
    }

    // List
    @GetMapping
    public List<TrainDTO> getTrains(){
        return trainsService.getAllTrains();
    }

    // get details
    @GetMapping("/{id}")
    public ResponseEntity<TrainDTO> getTrainById(@PathVariable("id") Long id){
      return  new ResponseEntity<>(trainsService.getTrainByTrainNo(id), HttpStatus.OK);
    }

    //update
    @PutMapping("/{id}")
    public ResponseEntity<TrainDTO> updateTrain(@PathVariable("id") Long id,@RequestBody TrainDTO trainDTO){
        return  new ResponseEntity<>(trainsService.updateTrain(id, trainDTO), HttpStatus.OK);
    }

    //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<TrainDTO> deleteTrain(@PathVariable("id") Long id){
        trainsService.deleteTrain(id);
      return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
