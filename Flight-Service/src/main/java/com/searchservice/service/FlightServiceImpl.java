package com.searchservice.service;

import com.searchservice.entities.Flight;
import com.searchservice.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Override
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    @Override
    public List<Flight> getFlightsByRoute(String from, String to) {
        return flightRepository.findByDepartureLocationAndArrivalLocation(from, to);
    }

    @Override
    public Flight addFlight(Flight flight) {
        // Check status based on departureTime
        if (flight.getDepartureTime().isAfter(LocalDateTime.now().plusDays(2))) {
            flight.setStatus("ACTIVE");
        } else {
            flight.setStatus("NOT_ACTIVE");
        }
        return flightRepository.save(flight);
    }

    @Override
    public Flight getFlightById(Long id) {
        return flightRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Flight not found with id: " + id));
    }

    @Override
    public Flight updateFlight(Long id, Flight flight) {
        Flight existing = getFlightById(id);
        existing.setFlightNumber(flight.getFlightNumber());
        existing.setDepartureLocation(flight.getDepartureLocation());
        existing.setArrivalLocation(flight.getArrivalLocation());
        existing.setDepartureTime(flight.getDepartureTime());
        existing.setArrivalTime(flight.getArrivalTime());
        existing.setAvailableSeats(flight.getAvailableSeats());

        // Re-calculate status based on updated date
        if (flight.getDepartureTime().isAfter(LocalDateTime.now().plusDays(2))) {
            existing.setStatus("ACTIVE");
        } else {
            existing.setStatus("NOT_ACTIVE");
        }

        return flightRepository.save(existing);
    }


    @Override
    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }

}
