package com.substring.irctc.services;

import com.substring.irctc.Repository.TrainRepo;
import com.substring.irctc.dto.PageResponse;
import com.substring.irctc.dto.TrainDTO;
import com.substring.irctc.entity.Train;
import com.substring.irctc.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

@Service
public class  TrainService {

    private ModelMapper modelMapper;
    private TrainRepo trainRepo;

    public TrainService(ModelMapper modelMapper, TrainRepo trainRepo) {
        this.modelMapper = modelMapper;
        this.trainRepo = trainRepo;
    }



    public TrainDTO add(TrainDTO trainDTO) {

        //  covert krna padega dto to entity
//        Train train = new Train();
//        train.setTrainNo(trainDTO.getTrainNo());
//        train.setName(trainDTO.getName());
//        train.setRouteName(trainDTO.getRouteName());
       Train train  =  modelMapper.map(trainDTO, Train.class);

        Train savedTrain =  trainRepo.save(train);
        // convert entity into dto

//        TrainDTO dto = new TrainDTO();
//        dto.setTrainNo(savedTrain.getTrainNo());
//        dto.setName(savedTrain.getName());
//        dto.setRouteName(savedTrain.getRouteName());

        TrainDTO dto =  modelMapper.map(savedTrain, TrainDTO.class);

        return dto;
    }

    public PageResponse<TrainDTO> all(int page, int size, String sortBy, String sortDir) {
        //kuch aisa krna hai ki pagination implemnet ho jaye
         // sorting
        Sort sort = sortBy.trim().toLowerCase().equals("asc") ? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);



        // db se data fetch karne ka logic: we can get train from distance
       Page<Train> trainPage = trainRepo.findAll(pageable);

       Page<TrainDTO> trainDTOPage = trainPage.map(train -> modelMapper.map(train, TrainDTO.class));
       return PageResponse.fromPage(trainDTOPage);


//       List<Train> all = trainPage.getContent();
//
//
//       // list of train to list of train dtos
//      List<TrainDTO> trainDTOS =   all.stream().map(train ->  modelMapper.map(train, TrainDTO.class)).toList();
//      return  trainDTOS;

//        return  trainPage.map(train -> modelMapper.map(train, TrainDTO.class));
    }

    public TrainDTO get(Long number) {
   Train  train = trainRepo.findById(number).orElseThrow(()->new ResourceNotFoundException("Train Not Found"));
        return  modelMapper.map(train, TrainDTO.class);
    }

    public void  delete(Long number) {
        Train  train = trainRepo.findById(number).orElseThrow(()->new ResourceNotFoundException("Train Not Found"));
        trainRepo.delete(train);
    }
}
