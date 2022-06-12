package app.dao;

import app.entities.User;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {
    private UserDao userDao = new UserDao();

    @Test
    void findOneById() throws SQLException, ClassNotFoundException {
        User user = userDao.findOneById(1);
        assertEquals("34535345", user.getPhone());
    }

    @Test
    void findOne() throws SQLException, ClassNotFoundException {
        User user = userDao.findOne("qwe", "qwe");
        assertEquals(2, user.getId());
    }

    @Test
    void updateAccount() throws SQLException, ClassNotFoundException {
        int userId = 2;
        int newAccount = 123;
        userDao.updateAccount(userId, newAccount);
        User user = userDao.findOneById(userId);
        assertEquals(newAccount, user.getAccount());
        newAccount = 96;
        userDao.updateAccount(userId, newAccount);
        user = userDao.findOneById(userId);
        assertEquals(newAccount, user.getAccount());
    }
}