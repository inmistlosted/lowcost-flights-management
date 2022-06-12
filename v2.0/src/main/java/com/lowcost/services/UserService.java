package com.lowcost.services;

import com.lowcost.entities.Role;
import com.lowcost.entities.User;
import com.lowcost.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    FlightService flightService;

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User getUserById(Integer id){
        Optional<User> userOpt = userRepository.findById(id);
        return userOpt.orElse(null);
    }

    public boolean addUser(User user){
        User userFromDB = getUserByUsername(user.getUsername());

        if(userFromDB != null){
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
        return true;
    }

    public User replenishUserAccount(User user, double money){
        User currUser = userRepository.findByUsername(user.getUsername());
        currUser.setAccount(currUser.getAccount() + money);
        userRepository.updateUserAccount(currUser.getAccount(), currUser.getUsername());
        return currUser;
    }

    public User withdrawMoneyFromAccount(User user, double money){
        user.setAccount(user.getAccount() - money);
        userRepository.updateUserAccount(user.getAccount(), user.getUsername());
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s);
    }
}
