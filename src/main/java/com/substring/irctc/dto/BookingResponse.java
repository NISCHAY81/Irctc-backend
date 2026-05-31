package com.substring.irctc.dto;

import com.substring.irctc.entity.BookingStatus;
import com.substring.irctc.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
    private Long bookingId;
    private LocalDate journeyDate;
    private  LocalTime departureTime;
    private LocalTime arrivalTime;
    private  StationDto sourceStation;
    private StationDto destinationStation;
    private Long  pnr;
    private BigDecimal totalPrice;
    private BookingStatus bookingStatus;
    private PaymentStatus  paymentStatus;
    private List<BookingPassengerDto> passengers;
}
