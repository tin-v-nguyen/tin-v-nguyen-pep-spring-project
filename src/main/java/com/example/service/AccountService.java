package com.example.service;

import java.util.Optional;

import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.message.AuthException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account register(Account account) throws Exception {
        String username = account.getUsername();
        if (username.equals("") || username == null) throw new IllegalArgumentException("Invalid Username");
        if (account.getPassword().length() < 4) throw new IllegalArgumentException("Invalid Password");
        Optional<Account> exists = accountRepository.findByUsername(account.getUsername());
        if (exists.isPresent()) throw new IllegalArgumentException("User already exists");

        return accountRepository.save(account);
    }

    public Account login(Account account) throws Exception {
        Optional<Account> exists = accountRepository.findByUsername(account.getUsername());
        if (exists.isPresent() && exists.get().getPassword().equals(account.getPassword())) {
            return exists.get();
        } else {
            throw new AuthException("Unauthorized");
        }
    }

    public Account findById(int id) {
        Optional<Account> exists = accountRepository.findById(id);
        return exists.get();
    }
}
