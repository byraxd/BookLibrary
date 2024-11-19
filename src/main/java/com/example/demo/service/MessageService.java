package com.example.demo.service;

import com.example.demo.model.Message;

import java.util.List;
import java.util.Optional;

public interface MessageService {
    Message sendMessage(Message message);
    List<Message> sendMessageToUsersWhereDueDateIsBefore();
    Message updateMessage(Message message);
}
