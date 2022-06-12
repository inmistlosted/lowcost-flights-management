package com.lowcost.services;

import com.lowcost.entities.Flight;
import com.lowcost.entities.Seat;
import com.lowcost.entities.Ticket;
import com.lowcost.entities.User;
import com.lowcost.repositories.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class TicketServiceTest {
    @Autowired
    TicketService ticketService;

    @MockBean
    SeatService seatService;
    @MockBean
    FlightService flightService;
    @MockBean
    UserService userService;
    @MockBean
    TicketRepository ticketRepository;

    @Test
    void getTicketOnSale() throws ParseException {
        Flight flightBefore = new Flight();
        flightBefore.setId(2);
        Seat seat = new Seat();
        seat.setSeat_id(1);
        seat.setNumber(5);
        seat.setFlight(flightBefore);

        Flight flight = new Flight();
        flight.setId(2);
        flight.setDirection("Berlin - London");
        flight.setFlightTime("3:30");


        Mockito.doReturn(seat)
                .when(seatService)
                .getSeatById(1);

        Mockito.doReturn(flight)
                .when(flightService)
                .getFlightById(2);

        Ticket ticket = ticketService.getTicketOnSale(1);

        assertNotNull(ticket);
        assertEquals(5, ticket.getSeat().getNumber());
        assertEquals("Berlin - London", ticket.getSeat().getFlight().getDirection());
        assertEquals("3:30", ticket.getSeat().getFlight().getFlightTime());

        Mockito.verify(seatService, Mockito.times(1)).getSeatById(ArgumentMatchers.anyInt());
        Mockito.verify(flightService, Mockito.times(1)).getFlightById(ArgumentMatchers.anyInt());
    }

    @Test
    void buyTicket() throws SQLException, ClassNotFoundException {
        Flight flight = new Flight();
        flight.setId(2);

        Seat seat = new Seat();
        seat.setSeat_id(1);
        seat.setNumber(5);
        seat.setFlight(flight);

        User user = new User();
        user.setId(10);
        user.setAccount(100);

        Mockito.doReturn(seat)
                .when(seatService)
                .getSeatById(1);

        Mockito.doReturn(user)
                .when(userService)
                .getUserById(10);

        boolean isSold = ticketService.buyTicket("1", "10", "Not available", "Not available", "50");

        assertTrue(isSold);

        Mockito.verify(seatService, Mockito.times(1))
                .getSeatById(ArgumentMatchers.anyInt());
        Mockito.verify(userService, Mockito.times(1))
                .getUserById(ArgumentMatchers.anyInt());
        Mockito.verify(userService, Mockito.times(1))
                .withdrawMoneyFromAccount(ArgumentMatchers.any(User.class), ArgumentMatchers.anyDouble());
        Mockito.verify(seatService, Mockito.times(1))
                .makeSeatTaken(ArgumentMatchers.anyInt());
        Mockito.verify(ticketRepository, Mockito.times(1))
                .save(ArgumentMatchers.any(Ticket.class));
    }

    @Test
    void buyTicketFail() throws SQLException, ClassNotFoundException {
        Flight flight = new Flight();
        flight.setId(2);

        Seat seat = new Seat();
        seat.setSeat_id(1);
        seat.setNumber(5);
        seat.setFlight(flight);

        User user = new User();
        user.setId(10);
        user.setAccount(10);

        Mockito.doReturn(seat)
                .when(seatService)
                .getSeatById(1);

        Mockito.doReturn(user)
                .when(userService)
                .getUserById(10);

        boolean isSold = ticketService.buyTicket("1", "10", "Not available", "Not available", "50");

        assertFalse(isSold);

        Mockito.verify(seatService, Mockito.times(1))
                .getSeatById(ArgumentMatchers.anyInt());
        Mockito.verify(userService, Mockito.times(1))
                .getUserById(ArgumentMatchers.anyInt());
        Mockito.verify(userService, Mockito.times(0))
                .withdrawMoneyFromAccount(ArgumentMatchers.any(User.class), ArgumentMatchers.anyDouble());
        Mockito.verify(seatService, Mockito.times(0))
                .makeSeatTaken(ArgumentMatchers.anyInt());
        Mockito.verify(ticketRepository, Mockito.times(0))
                .save(ArgumentMatchers.any(Ticket.class));
    }
}