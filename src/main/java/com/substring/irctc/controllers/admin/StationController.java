package com.substring.irctc.controllers.admin;

import com.substring.irctc.dto.PageResponse;
import com.substring.irctc.dto.StationDto;
import com.substring.irctc.services.StationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/admin/stations")
@AllArgsConstructor
public class StationController {

private StationService stationService;
    // create stations

    @PostMapping
     public ResponseEntity<StationDto> createStation(
          @Valid @RequestBody StationDto stationDto
     )  {
         StationDto dto = stationService.createStation(stationDto);
         return  new ResponseEntity<>(dto, HttpStatus.CREATED);
     }

     @GetMapping
     public PageResponse<StationDto> listStations(
             @RequestParam(value = "page", defaultValue = "0") int page,
             @RequestParam(value = "size", defaultValue = "10") int size,
             @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
             @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir

     ) {
       PageResponse<StationDto> stationsDto = stationService.listStations(page,size,sortBy,sortDir);
       return stationsDto;
     }

     @GetMapping("/{id}")
     public StationDto getById(@PathVariable Long id) {
   StationDto dto =  stationService.getById(id);
   return dto;
     }

     @PutMapping("/{id}")
     public StationDto updateStation(
          @PathVariable Long id,
          @RequestBody StationDto dto
     ) {
       StationDto stationDto = stationService.update(id,dto);
       return stationDto;
     }

     @DeleteMapping("/{id}")
     public ResponseEntity<Void> delete(@PathVariable Long id) {
        stationService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
     }
 }
