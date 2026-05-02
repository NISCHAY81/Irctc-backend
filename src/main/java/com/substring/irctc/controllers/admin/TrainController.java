package com.substring.irctc.controllers.admin;

import com.substring.irctc.dto.TrainDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("adminTrainController")
@RequestMapping("/admin/trains")
public class TrainController {

    public ResponseEntity<TrainDTO> createTrain(@RequestBody TrainDTO trainDTO)
    {
        return  new ResponseEntity<>(trainDTO, HttpStatus.OK);
    }
}
