package app.services;

import app.entities.Ticket;
import app.entities.User;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private UserService userService = new UserService();

    @Test
    void getUser() throws SQLException, ClassNotFoundException {
        User user = userService.getUser("qwe", "qwe");
        assertEquals(2, user.getId());
    }

    @Test
    void getUserById() throws SQLException, ClassNotFoundException {
        User user = userService.getUserById(1);
        List<Ticket> tickets = user.getTickets();
        assertEquals(1, tickets.size());
        assertEquals(3, tickets.get(0).getSeat().getNumber());
    }

    @Test
    void replenish() throws SQLException, ClassNotFoundException {
        int userId = 2;
        User user = userService.getUserById(userId);
        assertEquals(96, user.getAccount());
        userService.replenish(userId, "20");
        user = userService.getUserById(userId);
        assertEquals(116, user.getAccount());
        userService.replenish(userId, "-20");
        user = userService.getUserById(userId);
        assertEquals(96, user.getAccount());
    }
}