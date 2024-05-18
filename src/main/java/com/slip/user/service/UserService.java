package com.slip.user.service;


import com.slip.user.Models.User;
import java.util.List;



public interface UserService {
    public List<User> getAllUsers();

    public User addUsers(User user);

    User getUserByHandle(String handle);

    User getUserById(Long id);

    User getUserByEmail(String email);

    User saveUserInfo(User user);

    String followByEmailId(String loggedInUserEmail, String targetUserEmail);

    String unFollowByEmailId(String loggedInUserEmail, String targetUserEmail);

    List<User> getFollowingsOfUserWithEmailId(String targetUserEmail);

    List<User> getFollowersOfUserWithEmailId(String targetUserEmail);
}
