package com.lowcost.services;

import com.lowcost.entities.Flight;
import com.lowcost.entities.Seat;
import com.lowcost.repositories.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FlightService {
    @Autowired
    FlightRepository flightRepository;

    public List<Flight> getAllFlights() throws ParseException {
        List<Flight> flights = (List<Flight>) flightRepository.findAll();
        for(Flight flight : flights){
            flight.setSeatsNumAvailable(calculateSeatsNumAvailable(flight));
            flight.setPrice(calculateCurrPrice(flight));
        }
        return flights;
    }

    public Flight getFlightById(Integer flightId) throws ParseException {
        Optional<Flight> optFlight = flightRepository.findById(flightId);
        Flight flight = optFlight.orElse(null);
        if(flight != null){
            int seatsNum = calculateSeatsNumAvailable(flight);
            flight.setSeatsNumAvailable(seatsNum);
            flight.setPrice(calculateCurrPrice(flight));
        }
        return flight;
    }

    public int calculateSeatsNumAvailable(Flight flight){
        int count = 0;
        for(Seat seat : flight.getSeats()){
            if (seat.isFree()) count++;
        }
        return count;
    }

    public double calculateCurrPrice(Flight flight) throws ParseException {
        double currPrice = flight.getStartPrice();
        currPrice += calcExtraPriceSeats(getFillPercent(calculateSeatsNumAvailable(flight), flight.getSeatsNum()));
        currPrice += calcExtraPriceDate(getDepartureDateDiff(flight.getDepartureTime()));
        return currPrice;
    }

    public double calcExtraPriceDate(double diff){
        if (diff <= 90 && diff > 30){
            return 10;
        } else if (diff <= 30 && diff > 14){
            return  15;
        } else if (diff <= 14 && diff > 7){
            return  20;
        } else if (diff <= 7 && diff > 3){
            return  25;
        } else if (diff <= 3){
            return 35;
        } else {
            return 0;
        }
    }

    public double getDepartureDateDiff(String departureDateStr) throws ParseException {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy - HH:mm");
        Date currDate = new Date();
        Date departureDate = df.parse(departureDateStr);

        long diff = Math.abs(departureDate.getTime() - currDate.getTime());
        return (double) diff / 1000 / 60 / 60 / 24;
    }

    public double calcExtraPriceSeats(double percent){
        if (percent <= 50 && percent > 25){
            return 5;
        } else if (percent <= 25 && percent > 15){
            return  10;
        } else if (percent <= 15 && percent > 5){
            return  15;
        } else if (percent <= 5){
            return  25;
        } else {
            return 0;
        }
    }

    public double getFillPercent(int freeSeatsNum, int allSeatsNum){
        return Math.round((double) freeSeatsNum * 100 / allSeatsNum);
    }
}
