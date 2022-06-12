package app.dao;

import app.entities.Seat;
import app.entities.Ticket;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TicketDao {
    public List<Ticket> findTicketsOfUser(int userId) throws SQLException, ClassNotFoundException {
        Connection conn = DBConnection.initDB();
        Statement statement = conn.createStatement();

        String sql = "select * from tickets where user_id=" + userId;

        ResultSet rs = statement.executeQuery(sql);

        List<Ticket> tickets = new ArrayList<Ticket>();
        while (rs.next()){
            int id = rs.getInt("ticket_id");
            int seatId = rs.getInt("seat_id");
            boolean baggage = rs.getBoolean("baggage");
            boolean priority = rs.getBoolean("priority");
            double price = rs.getDouble("price");
            tickets.add(new Ticket(id, new Seat(seatId), baggage, priority, price));
        }

        rs.close();
        conn.close();

        return tickets;
    }

    public void insert(Ticket ticket) throws SQLException, ClassNotFoundException {
        Connection conn = DBConnection.initDB();
        Statement statement = conn.createStatement();

        String sql = "insert into tickets (seat_id, user_id, baggage, priority, price) values(" + ticket.getSeat().getId() + ", " +
                ticket.getUser().getId() + ", " + ticket.hasBaggage() + ", " + ticket.hasPriority() + ", " + ticket.getPrice() + ")";

        statement.executeUpdate(sql);
        conn.close();
    }
}
