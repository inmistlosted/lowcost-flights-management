package com.lowcost.controllers;

import com.lowcost.entities.User;
import com.lowcost.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class AccountController {
    @Autowired
    UserService userService;

    @GetMapping("/registration")
    public String showRegistrationGui(){
        return "register";
    }

    @PostMapping("/registration")
    public String registerUser(User user, Map<String, Object> model){
        boolean isAdded = userService.addUser(user);
        if (!isAdded){
            model.put("message", "Username \"" + user.getUsername() + "\" already exists");
            return "register";
        }

        return "redirect:/login";
    }

}
