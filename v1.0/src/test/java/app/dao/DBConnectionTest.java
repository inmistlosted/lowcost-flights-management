package app.dao;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class DBConnectionTest {

    @Test
    void initDB() throws SQLException, ClassNotFoundException {
        Connection conn = DBConnection.initDB();
        Statement statement = conn.createStatement();

        String sql = "select * from flights";

        ResultSet rs = statement.executeQuery(sql);

        assertTrue(rs.next());

        rs.close();
        conn.close();
    }
}