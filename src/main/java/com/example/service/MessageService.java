package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    AccountService accountService;

    public Message getMessageById(int id) {
        Optional<Message> exists = messageRepository.findById(id);
        if (exists.isEmpty()) return null;
        return exists.get();
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message postMessage(Message message) throws Exception {
        if (message.getMessageText().equals("") 
        || message.getMessageText().length() > 255 
        || accountService.findById(message.getPostedBy()) == null
        ) throw new IllegalArgumentException("Invalid message");
        return messageRepository.save(message);
    }
}
