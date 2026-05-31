package com.substring.irctc.dto;

import com.substring.irctc.entity.BookingPassenger;
import com.substring.irctc.entity.CoachType;
import lombok.*;
import org.modelmapper.internal.bytebuddy.asm.Advice;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
    private  Long userId;
    private Long trainScheduleId;
    private Long trainId;
    private Long sourceStationId;
    private Long destinationStationId;
    private LocalDate journeyDate;
    private CoachType coachType;
    private List<BookingPassengerDto> passengers;
    private LocalTime startTime;
    private LocalTime endTime;
}
