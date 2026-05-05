package com.substring.irctc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainScheduleDto {
    private Long id;
    private Long trainId;
    private LocalDateTime runDate;
    private Integer availableSeats;

}
