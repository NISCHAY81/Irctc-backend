package com.substring.irctc.services;

import com.substring.irctc.dto.BookingRequest;
import com.substring.irctc.dto.BookingResponse;
import com.substring.irctc.entity.Booking;


public interface BookingService {
    BookingResponse createBooking(BookingRequest bookingRequest);
}
