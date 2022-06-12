package app.services;

import app.entities.Flight;
import app.entities.Seat;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlightServiceTest {
    private FlightService flightService = new FlightService();

    @Test
    void getAllFlights() throws ParseException, SQLException, ClassNotFoundException {
        List<Flight> flights = flightService.getAllFlights();
        assertEquals(3, flights.size());
        assertEquals("Paris - Warsaw", flights.get(1).getDirection());
    }

    @Test
    void getFlight() throws ParseException, SQLException, ClassNotFoundException {
        Flight flight = flightService.getFlight(1);
        List<Seat> seats = flight.getSeats();
        assertEquals(20, seats.size());
        assertEquals(17, flight.getSeatsNumAvailable());
        assertEquals(160, flight.getPrice());
    }

    @Test
    void getSeat() throws SQLException, ClassNotFoundException {
        Seat seat = flightService.getSeat(48);
        assertEquals(12, seat.getNumber());
    }

    @Test
    void calculateCurrPrice() throws ParseException {
        Flight flight = new Flight(12, "Kharkiv-Lwiw", "02.05.2020 - 12:30", "0:45", 60, 30);
        flight.setSeatsNumAvailable(13);
        double currPrice = flightService.calculateCurrPrice(flight);
        assertEquals(60, currPrice);
        flight.setSeatsNumAvailable(2);
        flight.setDepartureTime("24.05.2020 - 01:34");
        currPrice = flightService.calculateCurrPrice(flight);
        assertEquals(75, currPrice);
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