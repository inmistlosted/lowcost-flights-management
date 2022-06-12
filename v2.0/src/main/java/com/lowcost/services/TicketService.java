package com.lowcost.services;

import com.lowcost.entities.Seat;
import com.lowcost.entities.Ticket;
import com.lowcost.entities.User;
import com.lowcost.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.text.ParseException;

@Service
public class TicketService {
    @Autowired
    SeatService seatService;
    @Autowired
    FlightService flightService;
    @Autowired
    UserService userService;
    @Autowired
    TicketRepository ticketRepository;

    public Ticket getTicketOnSale(Integer seatId) throws ParseException {
        Seat seat = seatService.getSeatById(seatId);
        seat.setFlight(flightService.getFlightById(seat.getFlight().getId()));
        return new Ticket(seat);
    }

    public boolean buyTicket(String seatIdStr, String userIdStr, String baggageStr, String priorityStr, String priceStr) throws SQLException, ClassNotFoundException {
        int seatId = Integer.parseInt(seatIdStr);
        int userId = Integer.parseInt(userIdStr);
        boolean baggage = false;
        boolean priority = false;
        double price = Double.parseDouble(priceStr);

        if (baggageStr.equals("Available")){
            baggage = true;
        }
        if (priorityStr.equals("Available")){
            priority = true;
        }
        Seat seat = seatService.getSeatById(seatId);
        User user = userService.getUserById(userId);
        if (user.getAccount() < price) return false;

        Ticket ticket = new Ticket(baggage, priority, price, seat, userId);
        user = userService.withdrawMoneyFromAccount(user, price);
        seatService.makeSeatTaken(seatId);
        ticketRepository.save(ticket);
        return true;
    }
}
