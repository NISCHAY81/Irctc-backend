package com.substring.irctc.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserTrainSearchRequest {
    private  Long sourceStationId;
    private  Long destinationStationId;
    private LocalDate journeyDate;
}
