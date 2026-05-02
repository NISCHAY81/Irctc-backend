package com.substring.irctc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "train_seats")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//Dibba
public class TrainSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @ManyToOne
    @JoinColumn(name = "train_schedule_id")
    private  TrainSchedule trainSchedule;

    @Enumerated(EnumType.STRING)
    private CoachType coachType; // Enum: AC, SLEEPER, GENERAL

    private Integer totalSeats;

    private  Integer availableSeats;

    private  Integer nextToAssign = 1;

    private BigDecimal price;



}
