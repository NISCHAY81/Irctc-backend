package com.substring.irctc.controllers;

import com.substring.irctc.dto.*;
import com.substring.irctc.entity.ImageMetaData;
import com.substring.irctc.entity.Train;
import com.substring.irctc.entity.TrainImage;
import com.substring.irctc.services.TrainImageService;
import com.substring.irctc.services.TrainService;
import com.substring.irctc.services.impl.FileUploadServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.charset.MalformedInputException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/trains")
public class TrainController {
    @Autowired
    private FileUploadServiceImpl fileUploadService;
    @Autowired
    private TrainImageService trainImageService;
    private TrainService trainService;

    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }

    // get All
    @GetMapping
    public PageResponse<TrainDTO> allTrains(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDic
    ) {
        return trainService.all(page, size, sortBy, sortDic);
    }

    // get one details
    @GetMapping("/{trainNo}")
    public ResponseEntity<TrainDTO> getTrain(@PathVariable Long trainNo) {

        return new ResponseEntity<>(this.trainService.get(trainNo), HttpStatus.OK);
    }


    // add train
    @PostMapping
    public ResponseEntity<TrainDTO> add(@Valid @RequestBody TrainDTO trainDTO) {

        return new ResponseEntity<>(trainService.add(trainDTO), HttpStatus.CREATED);
    }


    // delete train
    @DeleteMapping("/{trainNo}")
    public void delete(@PathVariable Long trainNo) {
        this.trainService.delete(trainNo);
    }


    @PostMapping("/photo")
    public ResponseEntity<ImageMetaData> uploadTrain(@RequestParam("file") MultipartFile file) throws IOException {
        ImageMetaData imageMetaData = fileUploadService.upload(file);

        return new ResponseEntity<>(imageMetaData, HttpStatus.CREATED);
    }


    @PostMapping("/upload/{trainNo}")
    public ResponseEntity<?> uploadTrainImage(@PathVariable String trainNo, @RequestParam("image") MultipartFile image) throws IOException {
        String contentType = image.getContentType();
        System.out.println(contentType);
        if(contentType != null && contentType.toLowerCase().startsWith("image/")) {
        return new ResponseEntity<>(trainImageService.upload(image,trainNo),HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new ErrorResponse("Image not uploaded","403", false ), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{trainId}/image")
    public ResponseEntity<Resource> serveTrainImage(@PathVariable String trainId ) throws  MalformedURLException {
       TrainImageDataWithResource trainImageDataWithResource =  trainImageService.loadImageByTrainNo(trainId);
        TrainImage trainImage = trainImageDataWithResource.trainImage();
       return   ResponseEntity.ok().contentType(MediaType.parseMediaType(trainImage.getFileType()))
               .body(trainImageDataWithResource.resource());
    }

//@ExceptionHandler(NoSuchElementException.class)
//    public ErrorResponse handleException(NoSuchElementException e){
//        ErrorResponse errorResponse  = new ErrorResponse("Train not found " + e.getMessage(), "404",false);
//        return errorResponse;
//}


    //    @RequestMapping("/all")
//    @ResponseBody
//    public List<Train> listTrains() {
//        System.out.println("all trains is here");
//
//        Train train1 = new Train();
//        train1.setTrainNo("11556");
//        train1.setName("LKO-DELHI SUPERFAST");
//        train1.setCoaches(10);
//
//        Train train2 = new Train();
//        train2.setTrainNo("22667");
//        train2.setName("DELHI-LKO SUPERFAST");
//        train2.setCoaches(10);
//
//        List<Train> trains = new ArrayList<>();
//        trains.add(train1);
//        trains.add(train2);
//
//        return trains;
//    }

}
