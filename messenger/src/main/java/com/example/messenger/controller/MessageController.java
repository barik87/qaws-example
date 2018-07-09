package com.example.messenger.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.messenger.entities.Message;
import com.example.messenger.storage.MessagesStorage;

@RestController
@RequestMapping("/msg")
public class MessageController {

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public ResponseEntity<String> sendMessage(@RequestBody Message message) {
        try {
            MessagesStorage.addMessage(message.getSender(), message.getText());
            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/read", method = RequestMethod.GET)
    public ResponseEntity<List<Message>> readMessages() {
        List<Message> result = MessagesStorage.getStorage();
        MessagesStorage.clearStorage();
        return ResponseEntity.ok(result);
    }
}
