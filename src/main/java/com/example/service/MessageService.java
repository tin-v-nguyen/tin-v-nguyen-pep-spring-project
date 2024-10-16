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

    public List<Message> getMessagesByAccountId(int accountId) {
        return messageRepository.findMessagesByPostedBy(accountId);
    }

    public Integer patchMessageById(int id, Message newMessage) {
        if (newMessage.getMessageText().equals("") 
        || newMessage.getMessageText().length() > 255 
        ) return null;

        Optional<Message> exists = messageRepository.findById(id);
        if (exists.isEmpty()) return null;
        Message updatedMessage = exists.get();
        updatedMessage.setMessageText(newMessage.getMessageText());
        messageRepository.save(updatedMessage);
        return 1;
    }

    public Integer deleteMessageById(int id) {
        Optional<Message> exists = messageRepository.findById(id);
        if (exists.isEmpty()) return null;
        messageRepository.deleteById(id);
        return 1;
    }

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
