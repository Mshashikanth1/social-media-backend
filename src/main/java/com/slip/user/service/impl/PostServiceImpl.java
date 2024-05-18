package com.slip.user.service.impl;


import com.slip.user.Models.Post;
import com.slip.user.dto.Post.PostAction;
import com.slip.user.repositories.PostRepository;
import com.slip.user.service.PostService;
import com.slip.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserService userService;

    public PostServiceImpl(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }
    @Override
    public Post saveOrUpdatePost(Post post,String userEmail){

        if(post.getPostRef()==null ) {
            post.setPostRef(UUID.randomUUID().toString());
        }
        Post createdPost = postRepository.save(post);
        postRepository.createRelation(userEmail,createdPost.getPostRef());
        return createdPost;
    }
    @Override
    public Post getPost(Long id){
        return postRepository.findById(id).orElseThrow();
    }
    @Override
    public List<Map<String, Object>> getAllPost(int offset , int limit){
       // return postRepository.findAll( PageRequest.of(offset,limit));
        return  postRepository.getPosts(offset,limit);
    }
    @Override
    public String deletePost(Long id){
        postRepository.deleteById(id);
        return "User Post Deleted";
    }
    @Override
    public String createPostAction(PostAction postAction, String userEmail){
        String res;
       switch (postAction.getPostActionType()){
           case LIKE:
               res= postRepository.createLikeRelation(postAction.getPostRef(),userEmail);
               break;
           case SHARE:
                res=postRepository.createShareRelation(postAction.getPostRef(),userEmail);
               break;
           case COMMENT:
               res= postRepository.createCommentRelation(postAction.getPostRef(),postAction.getCommentText(),userEmail);
               break;
           case UNLIKE:
               res= postRepository.DeleteLikeRelation(postAction.getPostRef(),userEmail);
               break;
           case UNCOMMENT:
                res=postRepository.DeleteCommentRelation(postAction.getPostRef(),userEmail);
               break;
           default:
               res= "Post Action Not Found";
       }
       return res;
    }
}
