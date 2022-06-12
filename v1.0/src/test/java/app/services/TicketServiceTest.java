package app.services;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class TicketServiceTest {
    private TicketService ticketService = new TicketService();

    @Test
    void buyTicket() throws SQLException, ClassNotFoundException {
        boolean isBought = ticketService.buyTicket("12", "2", "Available", "Not available", "1000");
        assertFalse(isBought);
    }
}