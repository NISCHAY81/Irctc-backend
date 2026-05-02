package com.substring.irctc.services;

import com.substring.irctc.dto.PageResponse;
import com.substring.irctc.dto.StationDto;

import java.io.IOException;

public interface StationService {
    StationDto createStation(StationDto stationDto) ;

    PageResponse<StationDto> listStations( int page, int size, String sortBy, String sortDir) ;

    StationDto getById(Long id);

    StationDto update(Long id, StationDto dto);

    void delete(Long id);
}
