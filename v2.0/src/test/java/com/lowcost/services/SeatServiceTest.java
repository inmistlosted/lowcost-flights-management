package com.lowcost.services;

import com.lowcost.entities.Seat;
import com.lowcost.repositories.SeatRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
class SeatServiceTest {
    @Autowired
    SeatService seatService;

    @MockBean
    SeatRepository seatRepository;

    @Test
    void getSeatById() {
        Seat seatOpt = new Seat();
        Mockito.doReturn(Optional.of(seatOpt))
                .when(seatRepository)
                .findById(1);

        Seat seat = seatService.getSeatById(1);
        assertNotNull(seat);
        Mockito.verify(seatRepository, Mockito.times(1)).findById(ArgumentMatchers.anyInt());
    }

    @Test
    void getSeatByIdFail(){
        Seat seat = seatService.getSeatById(1);
        assertNull(seat);
        Mockito.verify(seatRepository, Mockito.times(1)).findById(ArgumentMatchers.anyInt());
    }

    @Test
    void makeSeatTaken() {
        seatService.makeSeatTaken(1);
        Mockito.verify(seatRepository, Mockito.times(1))
                .updateFreeStatus(ArgumentMatchers.anyBoolean(), ArgumentMatchers.anyInt());
    }
}