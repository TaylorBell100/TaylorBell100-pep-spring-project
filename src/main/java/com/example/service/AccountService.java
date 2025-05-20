package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepo;

    public Boolean getByID(Integer id){
        return accountRepo.existsById(id);
    }

    public Account getByUsername(String name){
        return accountRepo.findByUsername(name);
    }

    public Account saveAccount(Account acc){
        return accountRepo.save(acc);
    }
}
