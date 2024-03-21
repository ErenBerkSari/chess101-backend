package com.example.chess_demo.controllers;

import com.example.chess_demo.entities.User;
import com.example.chess_demo.repos.UserRepository;
import com.example.chess_demo.services.UserServices;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserServices userServices;

    public UserController(UserServices userServices){
        this.userServices = userServices;
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userServices.getAllUsers();
    }

    @PostMapping
    public  User createUser(@RequestBody User newUser)
    {
        return userServices.saveOneUser(newUser);
    }

    @GetMapping("/{userId}")
    public User getOneUser(@PathVariable Long userId)
    {
        //custom exception
        return userServices.getOneUser(userId);
    }

    @PutMapping("/{userId}")
    public User updateOneUser(@PathVariable Long userId, @RequestBody User newUser)
    {
        return userServices.updateOneUser(userId,newUser);
    }

    @DeleteMapping("/{userId}")
    public void deleteOneUser(@PathVariable Long userId)
    {
        userServices.deleteById(userId);
    }

}
