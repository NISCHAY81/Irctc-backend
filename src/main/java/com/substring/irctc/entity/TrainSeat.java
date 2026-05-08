package com.substring.irctc.entity;

import com.substring.irctc.exception.ResourceNotFoundException;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    private Double price;

    private Integer trainSeatOrder;
//    private  Integer nextToAssign = 1;

    public Integer seatNumberToAssign;

    public boolean isCoachFull() {
        return availableSeats <= 0;
    }

    public boolean isSeatAvailable(int seatToBook) {
        return seatToBook <=  availableSeats;
    }



}
