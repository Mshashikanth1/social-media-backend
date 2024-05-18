package com.slip.user.controllers;

import com.slip.user.Models.User;
import com.slip.user.dto.LoginRequestDto;
import com.slip.user.dto.LoginResponseDto;
import com.slip.user.service.EmailService;
import com.slip.user.service.UserService;
import com.slip.user.util.AppUtils;
import com.slip.user.util.JwtTokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Objects;


@RestController
@CrossOrigin
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;


    public UserController(UserService userService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getLoggedInUser(){
        final  String loggedInUserEmail=AppUtils.getUserEmail();
        return ResponseEntity.ok(userService.getUserByEmail(loggedInUserEmail));
    }
    @GetMapping("/find-profile/{email}")
    public ResponseEntity<User> getTargetUserProfile(@PathVariable String email){
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }


    @PostMapping("/register")
    public ResponseEntity<User> addUsers(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser =userService.addUsers(user);
        sendOtp(user.getEmail());
        return ResponseEntity.ok(savedUser);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        User user = userService.getUserByEmail(loginRequestDto.getEmail());

        if(Objects.isNull(user)) return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user found with email "+ loginRequestDto.getEmail());

        if (verifyOtp(loginRequestDto, user))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Otp : Otp should be verified");


//        if (passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
//            System.out.println(user.getUsername());
//
//            String jwtToken = JwtTokenUtil.generateToken(user);
//
//            emailService.sendEmail(loginRequestDto.getEmail(),"SLiYp login","Hii "+user.getName()+",\n You are logged-in SLiYp successfully");
//
//
//            return ResponseEntity.ok(LoginResponseDto.builder()
//                            .token(jwtToken)
//                            .user(user.getRef().toString())
//                            .username(user.getName())
//                            .status(true)
//                            .msg("Login successful..")
//                    .build());
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
//        }
        try {
            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    user.getEmail(),
                                    loginRequestDto.getPassword(),
                                    user.getAuthorities()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwtToken = JwtTokenUtil.generateToken(user);
            emailService.sendEmail(loginRequestDto.getEmail(), "SLiYp login", "Hii " + user.getName() + ",\n You are logged-in SLiYp successfully");
            return ResponseEntity.ok(LoginResponseDto.builder()
                    .token(jwtToken)
                    .user(user.getRef().toString())
                    .email(user.getEmail())
                    .username(user.getName())
                    .status(true)
                    .msg("Login successful..")
                    .build());
        }catch (Exception e){ return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");}
    }

    @PostMapping("/send-otp")
    public String sendOtp(@RequestParam String email){
      User user = userService.getUserByEmail(email);
      String otp = generateOTP();
      user.setOtp(otp);
      userService.saveUserInfo(user);
      emailService.sendEmail(email,"SLYip Otp verification","your otp is "+otp+ " plz verify to SLYip");
      return "Otp sent to email";
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

    public static String generateOTP()
    {
        int randomPin   =(int) (Math.random()*9000)+1000;
        return String.valueOf(randomPin);
    }
}

