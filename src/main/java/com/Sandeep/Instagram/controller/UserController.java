package com.Sandeep.Instagram.controller;

import com.Sandeep.Instagram.model.User;
import com.Sandeep.Instagram.service.UserService;
import jakarta.annotation.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping(value = "/user")
    public ResponseEntity saveUser(@RequestBody String userData){
        User user= setUser(userData);
        int userId=userService.saveUser(user);
        return new ResponseEntity<>("User added successfully for id- "+userId, HttpStatus.CREATED);
    }


    @GetMapping(value = "/user")
    public ResponseEntity getUser(@Nullable @RequestParam String userId){
        JSONArray userDetails= userService.getUser(userId);
        return new ResponseEntity<>(userDetails.toString(),HttpStatus.OK);
    }
    @PutMapping(value = "/user/{userId}")
    public ResponseEntity updateUser(@PathVariable String userId, @RequestBody String userRequest){
        User newUser=setUser(userRequest);
        userService.updateUser(userId,newUser);
        return new ResponseEntity<>("user updated",HttpStatus.OK);
    }
    public User setUser(String userData) {
        JSONObject json=new JSONObject(userData);
        User user=new User();
        user.setAge(json.getInt("age"));
        user.setEmail(json.getString("email"));
        user.setFirstName(json.getString("firstName"));
        user.setLastName(json.getString("lastName"));
        user.setPhoneNumber(json.getString("phoneNumber"));
        return user;
    }
}

