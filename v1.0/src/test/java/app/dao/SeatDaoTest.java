package app.dao;

import app.entities.Seat;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SeatDaoTest {
    private SeatDao seatDao = new SeatDao();

    @Test
    void findSeatsOfFlight() throws SQLException, ClassNotFoundException {
        int flightId = 3;

        Connection conn = DBConnection.initDB();
        Statement statement = conn.createStatement();

        String sql = "select seats from flights where flight_id=" + flightId;

        ResultSet rs = statement.executeQuery(sql);
        int seatsNum = 0;
        if (rs.next()){
            seatsNum = rs.getInt("seats");
        }

        rs.close();
        conn.close();

        List<Seat> seats = seatDao.findSeatsOfFlight(flightId);

        assertEquals(seatsNum, seats.size());
    }

    @Test
    void findOne() throws SQLException, ClassNotFoundException {
        Seat seat = seatDao.findOne(23);
        assertEquals(3, seat.getNumber());
    }

    @Test
    void findNumOfFreeSeats() throws SQLException, ClassNotFoundException {
        int numOfFreeSeats = seatDao.findNumOfFreeSeats(2);
        assertEquals(15, numOfFreeSeats);
    }

    @Test
    void updateFreeStatus() throws SQLException, ClassNotFoundException {
        int seatId = 1;
        seatDao.updateFreeStatus(seatId, false);
        Seat seat = seatDao.findOne(seatId);
        assertFalse(seat.isFree());
        seatDao.updateFreeStatus(seatId, true);
        seat = seatDao.findOne(seatId);
        assertTrue(seat.isFree());
    }
}