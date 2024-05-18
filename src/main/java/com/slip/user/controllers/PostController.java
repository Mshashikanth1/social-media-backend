package com.slip.user.controllers;

import com.mongodb.internal.connection.QueryResult;
import com.slip.user.Models.Post;
import com.slip.user.dto.Post.PostAction;
import com.slip.user.service.PostService;
import com.slip.user.service.UserService;
import com.slip.user.util.AppUtils;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/post")
public class PostController {
    private final PostService postService;



    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
    }

    @PostMapping
    public Post createUserPost(@RequestBody Post post){
        final String userEmail= AppUtils.getUserEmail();
        return postService.saveOrUpdatePost(post,userEmail);
    }

    @PostMapping("/action")
    public String createPostAction(@RequestBody PostAction postAction){
        final String userEmail= AppUtils.getUserEmail();
        return postService.createPostAction(postAction,userEmail);
    }
    @GetMapping("/{id}")
    public Post getUserPost(@PathVariable Long  id){
        return postService.getPost(id);
    }
    @GetMapping()
    public List<Map<String, Object>> getUserPostsByPagination(@RequestParam int page , @RequestParam int size){
        return postService.getAllPost(page,size);
    }
    @DeleteMapping
    public String deleteUserPost(@RequestParam Long  id){
        return postService.deletePost(id);
    }


}
