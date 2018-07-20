package com.example.qaws.storage;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.example.qaws.entities.Message;

public class MessagesList {
    private static final int LIMIT = 200;
    private static List<Message> messages = new LinkedList<>();

    public static synchronized void add(Message message) {
        if (messages.size() == LIMIT) {
            messages.remove(LIMIT - 1);
        }
        messages.add(0, message);
    }

    public static synchronized List<Message> getMessages() {
        return messages;
    }

    public static synchronized List<Message> getMessages(Date fromTime) {
        List<Message> messagesCopy = new ArrayList<>(getMessages());
        List<Message> result = new ArrayList<>();
        for (Message message : messagesCopy) {
            if (message.getTime().before(fromTime)) {
                break;
            }
            result.add(message);
        }
        return result;
    }
}
