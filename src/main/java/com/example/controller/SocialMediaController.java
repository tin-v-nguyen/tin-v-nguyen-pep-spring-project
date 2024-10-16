package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.service.AccountService;

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
