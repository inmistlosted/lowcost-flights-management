package com.lowcost.services;

import com.lowcost.entities.Seat;
import com.lowcost.repositories.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SeatService {
    @Autowired
    SeatRepository seatRepository;

    public Seat getSeatById(Integer seatId){
        Optional<Seat> seatOpt = seatRepository.findById(seatId);
        return seatOpt.orElse(null);
    }

    public void makeSeatTaken(Integer seatId){
        seatRepository.updateFreeStatus(false, seatId);
    }
}
