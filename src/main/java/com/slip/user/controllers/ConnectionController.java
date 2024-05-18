package com.slip.user.controllers;

import com.slip.user.Models.User;
import com.slip.user.service.UserService;
import com.slip.user.util.AppUtils;
import com.slip.user.util.JwtTokenUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/connect")
public class ConnectionController {
    private final UserService userService;

    public ConnectionController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/followers")
    public ResponseEntity<List<User>> getFollowersOfUserWithEmail(@RequestParam String targetUserEmail){
        return ResponseEntity.ok(userService.getFollowersOfUserWithEmailId(targetUserEmail));
    }
    @GetMapping("/followings")
    public ResponseEntity<List<User>> getFollowingsOfUserWithEmail(@RequestParam String targetUserEmail){
        return ResponseEntity.ok(userService.getFollowingsOfUserWithEmailId(targetUserEmail));
    }

    @PostMapping("/follow")
    public ResponseEntity<String> followUser(@RequestParam String targetUserEmail){
        final String loggedInUserEmail= AppUtils.getUserEmail();
        return ResponseEntity.ok(userService.followByEmailId(loggedInUserEmail,targetUserEmail));
    }
    @PostMapping("/unfollow")
    public ResponseEntity<String> unfollowUser(@RequestParam String targetUserEmail){
        final String loggedInUserEmail= AppUtils.getUserEmail();
        return ResponseEntity.ok(userService.unFollowByEmailId(loggedInUserEmail,targetUserEmail));
    }
}
