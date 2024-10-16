package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity getMessagesByAccountId(@PathVariable int accountId) {
        List<Message> messages = messageService.getMessagesByAccountId(accountId);
        return ResponseEntity.status(200).body(messages);
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity patchMessageById(@RequestBody Message message, @PathVariable String messageId) {
        Integer rowsUpdated = messageService.patchMessageById(Integer.parseInt(messageId), message);
        if (rowsUpdated == null) return ResponseEntity.status(400).body("Client Error");
        return ResponseEntity.status(200).body(rowsUpdated);
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity deleteMessageById(@PathVariable String messageId) {
        Integer rowsDeleted = messageService.deleteMessageById(Integer.parseInt(messageId));
        if (rowsDeleted == null) return ResponseEntity.status(200).body("");
        return ResponseEntity.status(200).body((int) rowsDeleted);
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity getMessageById(@PathVariable String messageId) {
        Message message = messageService.getMessageById(Integer.parseInt(messageId));
        if (message == null) return ResponseEntity.status(200).body("");
        return ResponseEntity.status(200).body(message);
    }

    @GetMapping("/messages")
    public ResponseEntity getAllMessages() {
        List<Message> messages = new ArrayList<>();
        messages = messageService.getAllMessages();
        return ResponseEntity.status(200).body(messages);
    }

    @PostMapping("/messages")
    public ResponseEntity postMessage(@RequestBody Message message) {
        try {
            Message newMsg = messageService.postMessage(message);
            return ResponseEntity.status(200).body(newMsg);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Client error");
        }
    }

    // body contains account
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody Account account) {
        try {
            Account newAcc = accountService.register(account);
            return ResponseEntity.status(200).body(newAcc);
        } catch (Exception e) {
            if (e.getMessage().equals("User already exists")) {
                return ResponseEntity.status(409).body(e.getMessage());
            }
            return ResponseEntity.status(400).body("Bad Request");
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Account account) {
        try {
            Account loggedInAccount = accountService.login(account);
            return ResponseEntity.status(200).body(loggedInAccount);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
    }
}
