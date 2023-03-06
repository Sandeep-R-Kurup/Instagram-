package com.Sandeep.Instagram.controller;

import com.Sandeep.Instagram.model.Post;
import com.Sandeep.Instagram.model.User;
import com.Sandeep.Instagram.repository.UserRepository;
import com.Sandeep.Instagram.service.PostService;
import jakarta.annotation.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
public class PostController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostService  postService;
    @PostMapping(value = "/post")
    public ResponseEntity<String> savePost(@RequestBody String postRequest){
        Post post=setPost(postRequest);
        int postId= postService.savePost(post);
        return new ResponseEntity<>(String.valueOf(postId), HttpStatus.CREATED);
    }

    private Post setPost(String postRequest) {
        JSONObject jsonObject=new JSONObject(postRequest);
        User user;
        int userId=jsonObject.getInt("userId");
        if(userRepository.findById(userId).isPresent()) {
            user = userRepository.findById(userId).get();
        }
        else {
            return null;
        }
        Post post=new Post();
        post.setUser(user);
        post.setPostData(jsonObject.getString("postData"));
        Timestamp currTime=new Timestamp(System.currentTimeMillis());
        post.setCreatedDate(currTime);
        return post;
    }
    @GetMapping(value = "/post")
    public ResponseEntity<String> getPost(@RequestParam String userId, @Nullable @RequestParam String postId){
        int uId= Integer.parseInt(userId);
        JSONArray postArray=postService.getPost(uId,postId);
        return new ResponseEntity<>(postArray.toString(),HttpStatus.OK);
    }

    @PutMapping(value = "/post/{postId}")
    public ResponseEntity<String> updatePost(@PathVariable String postId, @RequestBody String postRequest){
        Post post=setPost(postRequest);
        postService.updatePost(postId,post);
        return new ResponseEntity<>("Post updated",HttpStatus.OK);
    }
}

