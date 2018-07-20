package com.example.qaws.controller;

import com.example.qaws.storage.MessagesList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.qaws.entities.Message;

import java.util.Date;
import java.util.List;

@RestController
public class MessageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    // For SUT
    @RequestMapping(value = "messenger/msg/send", method = RequestMethod.POST)
    public ResponseEntity<String> receiveMessage(@RequestBody Message message) {
        LOGGER.info("Message received. Text: '{}', sender: '{}'.", message.getText(), message.getSender());
        message.setTime(new Date());
        MessagesList.add(message);
        return ResponseEntity.ok("OK");
    }

    @RequestMapping(value = "/messages/read", method = RequestMethod.GET)
    public ResponseEntity<List<Message>> readAllMessages() {
        return ResponseEntity.ok(MessagesList.getMessages());
    }

    @RequestMapping(value = "/messages/read/{fromTime}", method = RequestMethod.GET)
    public ResponseEntity<List<Message>> readMessagesFromTime(@PathVariable("fromTime") long fromTimeMs) {
        Date fromTime = new Date(fromTimeMs);
        return ResponseEntity.ok(MessagesList.getMessages(fromTime));
    }

}
