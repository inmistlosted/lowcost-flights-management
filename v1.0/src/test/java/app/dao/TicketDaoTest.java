package app.dao;

import app.entities.Ticket;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TicketDaoTest {
    private TicketDao ticketDao = new TicketDao();

    @Test
    void findTicketsOfUser() throws SQLException, ClassNotFoundException {
        List<Ticket> tickets = ticketDao.findTicketsOfUser(1);
        assertEquals(1, tickets.size());
    }
}