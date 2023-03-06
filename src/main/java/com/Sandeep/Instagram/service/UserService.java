package com.Sandeep.Instagram.service;

import com.Sandeep.Instagram.model.User;
import com.Sandeep.Instagram.repository.UserRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public int saveUser(User user) {
        User userobj=userRepository.save(user);
        return userobj.getUserId();
    }

    public JSONArray getUser(String userId) {
        JSONArray userArray=new JSONArray();
        if(userId!=null && userRepository.findById(Integer.valueOf(userId)).isPresent()){
            User user=userRepository.findById(Integer.valueOf(userId)).get();
            JSONObject jsonObject=setUser(user);
            userArray.put(jsonObject);
        }
        else{
            List<User> userList=userRepository.findAll();
            for(User user:userList){
                JSONObject jsonObject=setUser(user);
                userArray.put(jsonObject);
            }
        }
        return userArray;
    }

    private JSONObject setUser(User user) {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("userId",user.getUserId());
        jsonObject.put("firstName",user.getFirstName());
        jsonObject.put("lastName",user.getLastName());
        jsonObject.put("email",user.getEmail());
        jsonObject.put("age",user.getAge());
        jsonObject.put("phoneNumber",user.getPhoneNumber());
        return jsonObject;
    }

    public void updateUser(String userId, User newUser) {
        if(userRepository.findById(Integer.valueOf(userId)).isPresent()) {
            User user = userRepository.findById(Integer.valueOf(userId)).get();
            newUser.setUserId(user.getUserId());
            userRepository.save(newUser);
        }
    }
}

