package com.substring.irctc.services.impl;

import com.substring.irctc.Repository.StationRepo;
import com.substring.irctc.dto.PageResponse;
import com.substring.irctc.dto.StationDto;
import com.substring.irctc.entity.Station;
import com.substring.irctc.exception.ResourceNotFoundException;
import com.substring.irctc.services.StationService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class StationServiceImpl implements StationService {

    private StationRepo stationRepo;
    private ModelMapper modelMapper;

    public StationServiceImpl(StationRepo stationRepo, ModelMapper modelMapper) {
        this.stationRepo = stationRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public StationDto createStation(StationDto stationDto)  {
        Station station = modelMapper.map(stationDto, Station.class);
        Station saved = stationRepo.save(station);
        return modelMapper.map(saved, StationDto.class);
    }

    @Override
    public PageResponse<StationDto> listStations(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.trim().toLowerCase().equals("asc") ? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Station> stations = stationRepo.findAll(pageable);
        Page<StationDto> stationDtos = stations.map(station -> modelMapper.map(station, StationDto.class));
        return  PageResponse.fromPage(stationDtos);
    }

    @Override
    public StationDto getById(Long id) {
        Station station = stationRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Station not found with id: "+id));
      return modelMapper.map(station, StationDto.class);
    }

    @Override
    public StationDto update(Long id, StationDto dto) {
        Station station = stationRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Station not found with id: "+id));
        // update details
        station.setCode(dto.getCode());
        station.setName(dto.getName());
        station.setCity(dto.getCity());
        station.setState(dto.getState());
        Station updatedStation = stationRepo.save(station);
        return modelMapper.map(updatedStation, StationDto.class);
    }

    @Override
    public void delete(Long id) {
        Station station = stationRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Station not found with id: "+id));
     stationRepo.delete(station);
    }

}
