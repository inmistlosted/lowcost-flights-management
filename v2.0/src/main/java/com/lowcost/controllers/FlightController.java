package com.lowcost.controllers;

import com.lowcost.entities.Flight;
import com.lowcost.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Controller
public class FlightController {
    @Autowired
    FlightService flightService;

    @GetMapping("/")
    public String showFlights(Map<String, Object> model) throws ParseException {
        List<Flight> flights = flightService.getAllFlights();
        model.put("flights", flights);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        if (!username.equals("anonymousUser")){
            model.put("user", username);
        }

        return "index";
    }

    @GetMapping("/flights")
    public String showFlight(@RequestParam Integer flightId, Map<String, Object> model) throws ParseException {
        Flight flight = flightService.getFlightById(flightId);
        System.out.println(flight.getSeats().size() + " - size");
        model.put("flight", flight);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        if (!username.equals("anonymousUser")){
            model.put("user", username);
        }

        return "flight";
    }
}
