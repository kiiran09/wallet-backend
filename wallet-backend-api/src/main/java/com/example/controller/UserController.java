package com.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Transaction;
import com.example.exception.UserNotFoundException;
import com.example.model.TransactionType;
import com.example.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}/balance")
    public ResponseEntity<Double> getBalance(@PathVariable("userId") Long userId) {
        double balance = userService.getBalance(userId);
        return ResponseEntity.ok(balance);
    }

    @PostMapping("/{userId}/add-money")
    public ResponseEntity<Void> addMoney(@PathVariable("userId") Long userId, @RequestBody Double amount) {
        userService.addMoney(userId, amount);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userId}/withdraw-money")
    public ResponseEntity<Void> withdrawMoney(@PathVariable("userId") Long userId, @RequestBody Double amount) {
        userService.withdrawMoney(userId, amount);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/transactions")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable("userId") Long userId) {
        List<Transaction> transactions = userService.getTransactions(userId);
        return ResponseEntity.ok(transactions);
    }
}

