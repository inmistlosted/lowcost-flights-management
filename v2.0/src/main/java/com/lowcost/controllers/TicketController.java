package com.lowcost.controllers;

import com.lowcost.entities.Ticket;
import com.lowcost.entities.User;
import com.lowcost.services.TicketService;
import com.lowcost.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Map;

@Controller
public class TicketController {
    @Autowired
    TicketService ticketService;
    @Autowired
    UserService userService;


    @GetMapping("/buy")
    public String showTicketGui(Principal principal, @RequestParam Integer seatId, Map<String, Object> model) throws ParseException {
        Ticket ticket = ticketService.getTicketOnSale(seatId);
        User user = userService.getUserByUsername(principal.getName());
        model.put("ticket", ticket);
        model.put("user", user);
        return "ticket";
    }

    @PostMapping("/buy")
    public String buyTicket(@RequestParam String seatId,
                            @RequestParam String userId,
                            @RequestParam String baggage,
                            @RequestParam String priority,
                            @RequestParam String price,
                            Map<String, Object> model) throws SQLException, ClassNotFoundException {
        boolean isSold = ticketService.buyTicket(seatId, userId, baggage, priority, price);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        if (!username.equals("anonymousUser")){
            model.put("user", username);
        }
        if (!isSold){
            model.put("replMessage", "Not enough money on account. ");
            return "message";
        }
        return "redirect:/";
    }
}
