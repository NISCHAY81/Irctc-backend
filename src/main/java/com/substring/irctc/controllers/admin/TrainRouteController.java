package com.substring.irctc.controllers.admin;

import com.substring.irctc.dto.TrainRouteDto;
import com.substring.irctc.services.TrainRRouteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/train-routes")
@AllArgsConstructor
public class TrainRouteController {
    private TrainRRouteService trainRRouteService;

    @PostMapping
    public ResponseEntity<TrainRouteDto> createTrainRoute( @RequestBody TrainRouteDto trainRouteDto){
        TrainRouteDto createRoute = trainRRouteService.addRoute(trainRouteDto);
        return  ResponseEntity.status(201).body(createRoute);
    }

    @GetMapping("/trains/{trainId}")
    public ResponseEntity<List<TrainRouteDto>> getTrainRoute(@PathVariable("trainId") Long trainId){
        List<TrainRouteDto> routes = trainRRouteService.getRoutesByTrain(trainId);
        return new ResponseEntity<>(routes, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainRouteDto> updateTrainRoute(@PathVariable("id") Long id, @RequestBody TrainRouteDto trainRouteDto){
        TrainRouteDto updateRoute = trainRRouteService.updateRoute(id, trainRouteDto);
        return  ResponseEntity.status(200).body(updateRoute);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainRoute(@PathVariable("id") Long id){
        trainRRouteService.deleteRoute(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
