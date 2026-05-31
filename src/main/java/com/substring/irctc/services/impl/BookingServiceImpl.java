package com.substring.irctc.services.impl;

import com.substring.irctc.Repository.*;
import com.substring.irctc.dto.BookingPassengerDto;
import com.substring.irctc.dto.BookingRequest;
import com.substring.irctc.dto.BookingResponse;
import com.substring.irctc.dto.StationDto;
import com.substring.irctc.entity.*;
import com.substring.irctc.exception.ResourceNotFoundException;
import com.substring.irctc.services.BookingService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BookingServiceImpl implements BookingService {

    private BookingRepo bookingRepo;
    private BookingPassengerRepo bookingPassengerRepo;
    private UserRepo userRepo;
    private TrainScheduleRepo trainScheduleRepo;
    private TrainRepo trainRepo;
    private StationRepo stationRepo;
    private TrainSeatRepo trainSeatRepo;
    private ModelMapper modelMapper;

    public BookingServiceImpl(BookingRepo bookingRepo, BookingPassengerRepo bookingPassengerRepo, UserRepo userRepo, TrainScheduleRepo trainScheduleRepo, TrainRepo trainRepo, StationRepo stationRepo, TrainSeatRepo trainSeatRepo, ModelMapper modelMapper) {
        this.bookingRepo = bookingRepo;
        this.bookingPassengerRepo = bookingPassengerRepo;
        this.userRepo = userRepo;
        this.trainScheduleRepo = trainScheduleRepo;
        this.trainRepo = trainRepo;
        this.stationRepo = stationRepo;
        this.trainSeatRepo = trainSeatRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public synchronized BookingResponse createBooking(BookingRequest bookingRequest) {

        User user = userRepo.findById(bookingRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found " + bookingRequest.getUserId()));

        TrainSchedule trainSchedule = trainScheduleRepo.findById(bookingRequest.getTrainScheduleId())
                .orElseThrow(() -> new ResourceNotFoundException("Train Schedule not found " + bookingRequest.getTrainScheduleId()));

        Station sourceStation = stationRepo.findById(bookingRequest.getSourceStationId())
                .orElseThrow(() -> new ResourceNotFoundException("Source Station not found " + bookingRequest.getSourceStationId()));

        Station destinationStation = stationRepo.findById(bookingRequest.getDestinationStationId())
                .orElseThrow(() -> new ResourceNotFoundException("Destination Station not found " + bookingRequest.getDestinationStationId()));

        // Get and sort coaches by order
        List<TrainSeat> coaches = trainSchedule.getTrainSeats();
        //it will sort coaches in trainSeatOrder
        coaches.sort((s1, s2) -> s1.getTrainSeatOrder() - s2.getTrainSeatOrder());

        // Filter by requested coach type
        List<TrainSeat> selectedCoaches = coaches.stream()
                .filter(coach -> coach.getCoachType() == bookingRequest.getCoachType())
                .toList();

        //total number od requested seat
        int totalRequestedSeat = bookingRequest.getPassengers().size();

        // Find a coach with enough available seats
        TrainSeat coachBookSeat = null;


        for (TrainSeat coach : selectedCoaches) {
            if (coach.isSeatAvailable(totalRequestedSeat)) {
                coachBookSeat = coach;
                break;
            }
        }

        if (coachBookSeat == null) {
            throw new IllegalStateException("No seats available in this type of coach");
        }

        // Create Booking
        Booking booking = new Booking();
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        booking.setUser(user);
        booking.setTrainSchedule(trainSchedule);
        booking.setSourceStation(sourceStation);
        booking.setDestinationStation(destinationStation);
        booking.setCreatedAt(LocalDateTime.now());
        booking.setJourneyDate(trainSchedule.getRunDate());
        booking.setPnr((long)(Math.random() * 9_000_000_000L) + 1_000_000_000L);
        booking.setTotalFare(new BigDecimal(totalRequestedSeat * coachBookSeat.getPrice()));

        // Create Payment
        Payment payment = new Payment();
        payment.setAmount(booking.getTotalFare());
        payment.setPaymentStatus(PaymentStatus.NOT_PAID);
        payment.setBooking(booking);
        booking.setPayment(payment);

        // Convert and add passengers
        // Convert and add passengers
        List<BookingPassenger> bookingPassengers = new ArrayList<>();
        for (BookingPassengerDto bookingPassengerDto : bookingRequest.getPassengers()) {
            BookingPassenger passenger = modelMapper.map(bookingPassengerDto, BookingPassenger.class);
            passenger.setBooking(booking);
            passenger.setTrainSeat(coachBookSeat);
            passenger.setSeatNumber(coachBookSeat.getSeatNumberToAssign() + "");  // ← getter not method call
            coachBookSeat.setSeatNumberToAssign(coachBookSeat.getSeatNumberToAssign() + 1); // ← increment for next passenger

            bookingPassengers.add(passenger);  //  was missing, passenger never added to list
        }
        booking.setPassengers(bookingPassengers);
// Deduct booked seats from TrainSeat
        coachBookSeat.setAvailableSeats(coachBookSeat.getAvailableSeats() - totalRequestedSeat);

// ✅ Also deduct from TrainSchedule total available seats
        trainSchedule.setAvailableSeats(trainSchedule.getAvailableSeats() - totalRequestedSeat);

// Save and return
        Booking savedBooking = bookingRepo.save(booking);
        trainSeatRepo.save(coachBookSeat);
        trainScheduleRepo.save(trainSchedule); // ✅ ADD THIS
        BookingResponse bookingResponse = new BookingResponse();
        bookingResponse.setBookingId(savedBooking.getId());
        bookingResponse.setPnr(savedBooking.getPnr());
        bookingResponse.setTotalPrice(savedBooking.getTotalFare());
        bookingResponse.setBookingStatus(savedBooking.getBookingStatus());
        bookingResponse.setSourceStation(modelMapper.map(sourceStation, StationDto.class));
        bookingResponse.setDestinationStation(modelMapper.map(destinationStation, StationDto.class));
        bookingResponse.setJourneyDate(savedBooking.getJourneyDate());
        bookingResponse.setPaymentStatus(savedBooking.getPayment().getPaymentStatus());
        bookingResponse.setPassengers(
                savedBooking.getPassengers().stream()
                        .map(p -> {
                         BookingPassengerDto bookingPassengerDto =   modelMapper.map(p, BookingPassengerDto.class);
                         bookingPassengerDto.setCoachId(p.getTrainSeat().getId());
                         return bookingPassengerDto;
                        })
                        .toList()
        );

     TrainRoute sourceRoute =  trainSchedule.getTrain().getRoutes().stream().filter(route->route.getStation().getId().equals(sourceStation.getId())).findFirst().get();
     bookingResponse.setDepartureTime(sourceRoute.getDepartureTime());
     bookingResponse.setArrivalTime(sourceRoute.getArrivalTime());

     return  bookingResponse;
    }


}