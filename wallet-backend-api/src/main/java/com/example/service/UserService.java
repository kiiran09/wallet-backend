package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.example.repository.TransactionRepository;

import com.example.entity.Transaction;
import com.example.entity.User;
import com.example.exception.InsufficientBalanceException;
import com.example.exception.UserNotFoundException;
import com.example.model.TransactionType;
import com.example.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public UserService(UserRepository userRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    public double getBalance(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return user.getBalance();
    }

    public void addMoney(Long userId, double amount) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        user.setBalance(user.getBalance() + amount);
        userRepository.save(user);
        Transaction transaction = new Transaction(userId, amount, TransactionType.CREDIT);
        transactionRepository.save(transaction);
    }

    public void withdrawMoney(Long userId, double amount) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        double currentBalance = user.getBalance();
        if (currentBalance >= amount) {
            user.setBalance(currentBalance - amount);
            userRepository.save(user);
            Transaction transaction = new Transaction(userId, amount, TransactionType.DEBIT);
            transactionRepository.save(transaction);
        } else {
            throw new InsufficientBalanceException(userId);
        }
    }

    public List<Transaction> getTransactions(Long userId) {
        return transactionRepository.findByUserId(userId);
    }
}
