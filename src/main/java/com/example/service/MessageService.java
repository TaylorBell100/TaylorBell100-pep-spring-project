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
    MessageRepository messageRepo;

    public Optional<Message> getById(Integer id){
        return messageRepo.findById(id);
    }

    public Message createMessage(Message message){
        return messageRepo.save(message);
    }

    public List<Message> getAllMessages(){
        return messageRepo.findAll();
    } 

    public List<Message> getAllById(Integer id){
        return messageRepo.findAllByPosted(id);
    } 

    public Integer deleteAllById(Integer id){
        return messageRepo.deleteAllById(id);
    } 

    public Integer updateMessage(Message message){
        messageRepo.save(message);
        return 1;
    }

    public List<Message> getAllByPosted(Integer id){
        return messageRepo.findAllByPosted(id);
    } 
}
