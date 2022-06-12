package com.lowcost.controllers;

import com.lowcost.entities.User;
import com.lowcost.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/cabinet")
    public String showCabinet(Principal principal, Map<String, Object> model) {
        User currUser = userService.getUserByUsername(principal.getName());
        System.out.println(currUser);
        model.put("user", currUser);
        return "cabinet";
    }

    @GetMapping("/replenish")
    public String showReplenishForm(Map<String, Object> model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        if (!username.equals("anonymousUser")){
            model.put("user", username);
        }
        return "replenish";
    }

    @PostMapping("/replenish")
    public String replenish(Principal principal, @RequestParam String money, Map<String, Object> model){
        User user = userService.getUserByUsername(principal.getName());
        User currUser = userService.replenishUserAccount(user, Double.parseDouble(money));
        model.put("user", currUser);
        return "cabinet";
    }
}
