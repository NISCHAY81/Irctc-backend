package com.substring.irctc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingPassengerDto {
    private  Long id;
    private  String name;
    private Integer age;
    private String gender;
    private  String seatNumber;
    private Long coachId;
}
