package com.substring.irctc.services;

import com.substring.irctc.dto.TrainRouteDto;
import com.substring.irctc.entity.TrainRoute;

import java.util.List;

public interface TrainRRouteService {
    TrainRouteDto addRoute(TrainRouteDto dto);


    //get train routes by train id
    List<TrainRouteDto> getRoutesByTrain(Long trainId);

    // update train route
    TrainRouteDto updateRoute(Long id, TrainRouteDto dto);

    //delete train route
    void deleteRoute(Long id);
}
