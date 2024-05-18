package com.slip.user.controllers;

import com.slip.user.Models.ChatMessage;
import com.slip.user.Models.User;
import com.slip.user.service.EmailService;
import com.slip.user.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Controller
public class ChatController {

    private final UserService userService;

    private final EmailService emailService;

    private ExecutorService executorService;

    @Value("${blocked}")
    public String blockedEmail;

    @Value("10")
    private void setPoolSize(int poolSize) {
        executorService = Executors.newFixedThreadPool(poolSize);
    }


    public ChatController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @MessageMapping("/chat.register")
    @SendTo("/topic/public")
    public ChatMessage register(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());

        CompletableFuture.runAsync(
                () -> {
                    String[] emailArray = userService.getAllUsers().stream()
                            .map(User::getEmail)
                            .filter(str->!str.equals(blockedEmail))
                            .toArray(String[]::new);
                    emailService.sendEmails(emailArray,
                            chatMessage.getSender() + " has joined SLiYp chat ", "hii , \n "
                                    + chatMessage.getSender()
                                    + " has joined in the SLYip chat want to join  https://user-service-vsp55ymczq-el.a.run.app/");
                },
                executorService
        );

        return chatMessage;
    }

    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }
}