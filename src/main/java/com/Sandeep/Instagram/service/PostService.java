package com.Sandeep.Instagram.service;


import com.Sandeep.Instagram.model.Post;
import com.Sandeep.Instagram.model.User;
import com.Sandeep.Instagram.repository.PostRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    public int savePost(Post post){
        Post savedPost =postRepository.save(post);
        return savedPost.getPostId();
    }

    public JSONArray getPost(int userId, String postId) {
        JSONArray postArray=new JSONArray();
        if(postId!=null && postRepository.findById(Integer.valueOf(postId)).isPresent()){
            Post post=postRepository.findById(Integer.valueOf(postId)).get();
            JSONObject postObj=setPostObj(post);
            postArray.put(postObj);
        }
        else {
            List<Post> postList=postRepository.findAll();
            for(Post post:postList){
                if(post.getUser().getUserId()==userId){
                    JSONObject postObj=setPostObj(post);
                    postArray.put(postObj);
                }
                JSONObject postObj=setPostObj(post);
                postArray.put(postObj);
            }
        }
        return postArray;
    }

    private JSONObject setPostObj(Post post) {
        JSONObject postObject=new JSONObject();
        postObject.put("postId",post.getPostId());
        postObject.put("postData",post.getPostData());

        User user=post.getUser();
        JSONObject userObj=new JSONObject();
        userObj.put("userId",user.getUserId());
        userObj.put("firstName",user.getFirstName());
        userObj.put("age",user.getAge());
        postObject.put("user",userObj);
        return postObject;
    }

    public void updatePost(String postId, Post newpost) {
        if(postId!=null && postRepository.findById(Integer.valueOf(postId)).isPresent()){
            Post post=postRepository.findById(Integer.valueOf(postId)).get();
            newpost.setPostId(post.getPostId());
            newpost.setUser(post.getUser());
            Timestamp updatedTime=new Timestamp(System.currentTimeMillis());
            newpost.setUpdatedDate(updatedTime);
            newpost.setCreatedDate(post.getCreatedDate());
            postRepository.save(newpost);
        }

    }
}
