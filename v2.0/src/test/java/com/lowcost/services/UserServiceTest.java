package com.lowcost.services;

import com.lowcost.entities.Role;
import com.lowcost.entities.User;
import com.lowcost.repositories.UserRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {
    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @Test
    void getUserByUsername() {
        Mockito.doReturn(new User())
                .when(userRepository)
                .findByUsername("Alex");
        User user = userService.getUserByUsername("Alex");
        assertNotNull(user);
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(ArgumentMatchers.anyString());
    }

    @Test
    void getUserByUsernameFail() {
        User user = userService.getUserByUsername("Alex");
        assertNull(user);
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(ArgumentMatchers.anyString());
    }

    @Test
    void getUserById() {
        User userOpt = new User();
        Mockito.doReturn(Optional.of(userOpt))
                .when(userRepository)
                .findById(1);
        User user = userService.getUserById(1);
        assertNotNull(user);
        Mockito.verify(userRepository, Mockito.times(1)).findById(ArgumentMatchers.anyInt());
    }

    @Test
    void getUserByIdFail() {
        User user = userService.getUserById(1);
        assertNull(user);
        Mockito.verify(userRepository, Mockito.times(1)).findById(ArgumentMatchers.anyInt());
    }

    @Test
    void addUser() {
        User user = new User();
        boolean isDone = userService.addUser(user);
        assertTrue(isDone);
        assertTrue(CoreMatchers.is(user.getRoles()).matches(Collections.singleton(Role.USER)));
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    void addUserFail(){
        User user = new User();
        user.setUsername("Alex");

        Mockito.doReturn(new User())
                .when(userRepository)
                .findByUsername("Alex");

        boolean isDone = userService.addUser(user);
        assertFalse(isDone);
        Mockito.verify(userRepository, Mockito.times(0)).save(ArgumentMatchers.any(User.class));
    }

    @Test
    void replenishUserAccount() {
        User user = new User();
        user.setAccount(10);
        user.setUsername("Alex");

        Mockito.doReturn(user)
                .when(userRepository)
                .findByUsername("Alex");

        User currUser = userService.replenishUserAccount(user, 10);
        assertEquals(20, currUser.getAccount());
        Mockito.verify(userRepository, Mockito.times(1))
                .findByUsername(ArgumentMatchers.anyString());
        Mockito.verify(userRepository, Mockito.times(1))
                .updateUserAccount(ArgumentMatchers.anyDouble(), ArgumentMatchers.anyString());
    }

    @Test
    void withdrawMoneyFromAccount() {
        User user = new User();
        user.setAccount(30);
        user.setUsername("Alex");

        User currUser = userService.withdrawMoneyFromAccount(user, 15);
        assertEquals(15, currUser.getAccount());
        Mockito.verify(userRepository, Mockito.times(1))
                .updateUserAccount(ArgumentMatchers.anyDouble(), ArgumentMatchers.anyString());
    }
}