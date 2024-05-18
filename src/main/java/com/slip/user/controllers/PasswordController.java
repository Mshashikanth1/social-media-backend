package com.slip.user.controllers;

import com.slip.user.service.EmailService;
import com.slip.user.service.UserService;
import com.slip.user.util.AppUtils;
import com.slip.user.Models.User;
import com.slip.user.dto.LoginRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.apache.commons.lang3.StringUtils;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/password")
public class PasswordController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;


    public PasswordController(UserService userService, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @PostMapping("/forget")
    public String UpdatePassword(@RequestBody LoginRequestDto loginRequestDto){
        User user = userService.getUserByEmail(loginRequestDto.getEmail());

        if (verifyOtp(loginRequestDto, user))
            return "Invalid Otp : Otp should be verified";

        user.setPassword(passwordEncoder.encode(loginRequestDto.getNewPassword()));
        userService.saveUserInfo(user);
        emailService.sendEmail(loginRequestDto.getEmail(),"SLYip password update","your otp is updated successfully");
        return  "password updated";
    }

    @PostMapping("/reset")
    public String  changePassword(@RequestBody LoginRequestDto loginRequestDto){
        User user = userService.getUserByEmail(loginRequestDto.getEmail());

        if (passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(loginRequestDto.getNewPassword()));
            userService.saveUserInfo(user);
            emailService.sendEmail(loginRequestDto.getEmail(),"SLYip password update","your otp is updated successfully");
            return "password updated";
        }
        return "Password did not match";
    }

    public  boolean verifyOtp(LoginRequestDto loginRequestDto, User user) {
        if(!"Verified".equals(user.getOtp()) && !StringUtils.isEmpty(user.getOtp())){
            if(user.getOtp().equals(loginRequestDto.getOtp())){
                user.setOtp("Verified");
                userService.saveUserInfo(user);
            }else return true;
        }
        return false;
    }

}
