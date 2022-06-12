package app.dao;

import app.entities.Flight;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlightDaoTest {
    private FlightDao flightDao = new FlightDao();

    @Test
    void findAll() throws SQLException, ClassNotFoundException {
        Connection conn = DBConnection.initDB();
        Statement statement = conn.createStatement();

        String sql = "select count(*) as total from flights";

        ResultSet rs = statement.executeQuery(sql);
        int count = 0;
        if (rs.next()){
            count = rs.getInt("total");
        }

        rs.close();
        conn.close();

        List<Flight> flights = flightDao.findAll();


        assertEquals(count, flights.size());
    }

    @Test
    void findOne() throws SQLException, ClassNotFoundException {
        Flight flight = flightDao.findOne(1);
        assertEquals("New York - Berlin", flight.getDirection());
    }

    @Test
    void findStartPrice() throws SQLException, ClassNotFoundException {
        double price = flightDao.findStartPrice(2);
        assertEquals(60, price);
    }
}