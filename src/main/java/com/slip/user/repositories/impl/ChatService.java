package com.slip.user.repositories.impl;

import com.slip.user.config.WebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ChatService {

    private final WebSocketHandler webSocketHandler;

    @Autowired
    public ChatService(WebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    public void sendMessageFromServer(String message) {
        try {
            webSocketHandler.sendMessageToClients("Server: " + message);
        } catch (IOException e) {
            // Handle exception
        }
    }
}
