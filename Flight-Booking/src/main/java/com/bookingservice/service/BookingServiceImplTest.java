package com.bookingservice.service;

import com.bookingservice.entity.Booking;
import com.bookingservice.feign.FlightClient;
import com.bookingservice.payload.BookingResponse;
import com.bookingservice.payload.Flight;
import com.bookingservice.repository.BookingRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceImplTest {

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private FlightClient flightClient;

    private Booking booking;
    private Flight mockFlight;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        booking = new Booking();
        booking.setId(1L);
        booking.setFlightId(5L);
        booking.setCustomerName("Ashlesh");
        booking.setNumberOfAdults(2);
        booking.setNumberOfChildren(1);
        booking.setBookingTime(LocalDateTime.now());
        booking.setTotalFare(0.0);

        mockFlight = new Flight();
        mockFlight.setId(5L);
        mockFlight.setDepartureLocation("Pune");
        mockFlight.setArrivalLocation("Delhi");
        mockFlight.setAvailableSeats(50);
    }

    @Test
    void testBookFlight() {
        when(flightClient.getFlightById(5L)).thenReturn(mockFlight);
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        BookingResponse saved = bookingService.bookFlight(booking);

        assertNotNull(saved);
        assertEquals("Ashlesh", saved.getCustomerName());
        verify(flightClient, times(1)).getFlightById(5L);
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void testGetAllBookings() {
        when(bookingRepository.findAll()).thenReturn(List.of(booking));

        List<Booking> bookings = bookingService.getAllBookings();

        assertEquals(1, bookings.size());
        assertEquals("Ashlesh", bookings.get(0).getCustomerName());
        verify(bookingRepository, times(1)).findAll();
    }

    @Test
    void testGetBookingById() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        Booking result = bookingService.getBookingById(1L);

        assertNotNull(result);
        assertEquals(5L, result.getFlightId());
        verify(bookingRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateBooking() {
        Booking updatedBooking = new Booking();
        updatedBooking.setCustomerName("Updated Name");
        updatedBooking.setFlightId(5L);
        updatedBooking.setNumberOfAdults(1);
        updatedBooking.setNumberOfChildren(2);

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(any(Booking.class))).thenReturn(updatedBooking);

        Booking result = bookingService.updateBooking(1L, updatedBooking);

        assertEquals("Updated Name", result.getCustomerName());
        assertEquals(2, result.getNumberOfChildren());
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void testDeleteBooking() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        doNothing().when(bookingRepository).delete(booking);

        assertDoesNotThrow(() -> bookingService.deleteBooking(1L));
        verify(bookingRepository, times(1)).delete(booking);
    }
}
