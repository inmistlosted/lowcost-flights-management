package com.lowcost.services;

import com.lowcost.entities.Flight;
import com.lowcost.entities.Seat;
import com.lowcost.entities.User;
import com.lowcost.repositories.FlightRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class FlightServiceTest {

    @Autowired
    FlightService flightService;

    @MockBean
    FlightRepository flightRepository;

    @Test
    void getAllFlights() throws ParseException {
        List<Flight> flights = flightService.getAllFlights();
        Mockito.verify(flightRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getFlightById() throws ParseException {
        Flight flightOpt = new Flight();
        List<Seat> seats = new ArrayList<>();
        for (int i = 0; i < 17; i++) {
            Seat seat = new Seat();
            seat.setFree(true);
            seats.add(seat);
        }
        flightOpt.setSeats(seats);
        flightOpt.setDepartureTime("02.05.2020 - 12:30");
        flightOpt.setSeatsNum(60);
        flightOpt.setStartPrice(30);

        Mockito.doReturn(Optional.of(flightOpt))
                .when(flightRepository)
                .findById(1);

        Flight flight = flightService.getFlightById(1);
        assertNotNull(flight);
        assertEquals(17, flight.getSeatsNumAvailable());
        assertEquals(55, flight.getPrice());
        Mockito.verify(flightRepository, Mockito.times(1)).findById(ArgumentMatchers.anyInt());
    }

    @Test
    void calculateSeatsNumAvailable() {
        Flight flight = new Flight();
        List<Seat> seats = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Seat seat = new Seat();
            seat.setFree(true);
            seats.add(seat);
        }
        flight.setSeats(seats);

        int freeSeats = flightService.calculateSeatsNumAvailable(flight);

        assertEquals(8, freeSeats);
    }

    @Test
    void calculateCurrPrice() throws ParseException {
        Flight flight = new Flight();
        flight.setDepartureTime("02.05.2020 - 12:30");
        flight.setSeatsNum(60);
        flight.setStartPrice(30);

        List<Seat> seats = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            Seat seat = new Seat();
            seat.setFree(true);
            seats.add(seat);
        }
        flight.setSeats(seats);

        double currPrice = flightService.calculateCurrPrice(flight);
        assertEquals(60, currPrice);
        flight.setSeatsNumAvailable(2);
        flight.setDepartureTime("24.05.2020 - 01:34");
        currPrice = flightService.calculateCurrPrice(flight);
        assertEquals(60, currPrice);
    }

    @Test
    void calcExtraPriceDate() {
        double extraPrice = flightService.calcExtraPriceDate(23);
        assertEquals(15, extraPrice);
        extraPrice = flightService.calcExtraPriceDate(11);
        assertEquals(20, extraPrice);
        extraPrice = flightService.calcExtraPriceDate(3);
        assertEquals(35, extraPrice);
    }

    @Test
    void getDepartureDateDiff() throws ParseException {
        double diff = Math.round(flightService.getDepartureDateDiff("20.06.2020 - 12:30"));
        assertEquals(37, diff);
        diff = Math.round(flightService.getDepartureDateDiff("03.07.2020 - 05:45"));
        assertEquals(50, diff);
        diff = Math.round(flightService.getDepartureDateDiff("11.10.2020 - 21:04"));
        assertEquals(150, diff);
    }

    @Test
    void calcExtraPriceSeats() {
        double extraPrice = flightService.calcExtraPriceSeats(67);
        assertEquals(0, extraPrice);
        extraPrice = flightService.calcExtraPriceSeats(13);
        assertEquals(15, extraPrice);
        extraPrice = flightService.calcExtraPriceSeats(1);
        assertEquals(25, extraPrice);
    }

    @Test
    void getFillPercent() {
        double percent = flightService.getFillPercent(15, 30);
        assertEquals(50, percent);
        percent = flightService.getFillPercent(10, 30);
        assertEquals(33, percent);
        percent = flightService.getFillPercent(28, 30);
        assertEquals(93, percent);
    }
}