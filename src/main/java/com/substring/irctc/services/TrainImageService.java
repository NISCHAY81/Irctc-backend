package com.substring.irctc.services;

import com.substring.irctc.Repository.TrainImageRepo;
import com.substring.irctc.Repository.TrainRepo;
import com.substring.irctc.dto.TrainImageDataWithResource;
import com.substring.irctc.dto.TrainImageResponse;
import com.substring.irctc.entity.Train;
import com.substring.irctc.entity.TrainImage;
import com.substring.irctc.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class TrainImageService {

    @Value("${train.image.folder.path}")
    private  String folderPath;
    @Autowired
    private TrainImageRepo  trainImageRepo;
    @Autowired
    private TrainRepo trainRepo;
    public TrainImageResponse upload(MultipartFile file, Long trainNo) throws IOException {

       Train train =  trainRepo.findById(trainNo).orElseThrow(()-> new ResourceNotFoundException("Train not found!!"));
        // checking and creating folder
        if(!Files.exists(Paths.get(folderPath))) {
            Files.createDirectories(Paths.get(folderPath));
        }
          String fullFileName = folderPath + UUID.randomUUID() + "_" + file.getOriginalFilename();

        Files.copy(file.getInputStream(), Paths.get(fullFileName), StandardCopyOption.REPLACE_EXISTING);

        System.out.println("Uploaded file: ");

        TrainImage  trainImage = new TrainImage();
        trainImage.setFilename(fullFileName);
        trainImage.setFileType(file.getContentType());
        trainImage.setSize(file.getSize());
        trainImage.setTrain(train);
        train.setTrainImage(trainImage);
       Train savedTrain =  trainRepo.save(train);

       return  TrainImageResponse.from(savedTrain.getTrainImage(),"https://localhost:8080", savedTrain.getNumber());

    }

    public TrainImageDataWithResource loadImageByTrainNo(Long trainId) throws  MalformedURLException {
      Train train =   trainRepo.findById(trainId).orElseThrow(()-> new ResourceNotFoundException("Train not found!!"));
        TrainImage trainImage = train.getTrainImage();
        if(trainImage == null) {
            throw  new ResourceNotFoundException("Train not found!!");
        }
       Path path = Paths.get(trainImage.getFilename());

        if(Files.notExists(path)) {
            throw  new ResourceNotFoundException("Train not found!!");
        }

    UrlResource urlResource = new UrlResource(path.toUri());
        TrainImageDataWithResource trainImageDataWithResource = new TrainImageDataWithResource(trainImage,urlResource);
        return trainImageDataWithResource;
    }
}
