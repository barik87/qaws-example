package com.example.messenger.storage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.messenger.entities.Message;

public class MessagesStorage {
    private static List<Message> storage = new ArrayList<>();

    public static void addMessage(String sender, String text) {
        Message message = new Message();
        message.setTime(new Date());
        message.setSender(sender);
        message.setText(text);
        storage.add(message);
    }

    public static List<Message> getStorage() {
        List<Message> copy = new ArrayList<>(storage);
        return copy;
    }

    public static void clearStorage() {
        storage.clear();
    }
}
