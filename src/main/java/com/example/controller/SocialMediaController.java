package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    AccountService accountService;

    @Autowired
    MessageService messageService;

    @GetMapping("accounts/{id}/messages")
    public ResponseEntity<List<Message>> getMessageByAccount(@PathVariable Integer id){
        List<Message> messages = messageService.getAllByPosted(id);
        return new ResponseEntity<>(messages, HttpStatus.OK);

    } 

    @PatchMapping("/messages/{id}")
    public ResponseEntity<Integer> updateMessage(@PathVariable Integer id, @RequestBody Message message){
        Optional<Message> temp = messageService.getById(id);
        if (!temp.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        
        if (message.getMessageText().length()> 255 || message.getMessageText() == ""){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Integer rows = messageService.updateMessage(message);
        return new ResponseEntity<>(rows, HttpStatus.OK);
    } 

    @DeleteMapping("/messages/{id}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable Integer id){
        List<Message> message = messageService.getAllById(id);
        if (message.isEmpty())
            return new ResponseEntity<>(HttpStatus.OK);
            

        messageService.deleteAllById(id);
        Integer temp = message.size();
        return new ResponseEntity<>(temp, HttpStatus.OK);
    } 

    @GetMapping("/messages/{id}")
    public ResponseEntity<Optional<Message>> getMessageById(@PathVariable Integer id){
        Optional<Message> message = messageService.getById(id);
        if (message.isPresent())
            return new ResponseEntity<>(message, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.OK);
    } 
    
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        List<Message> messages = messageService.getAllMessages();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    } 

    @PostMapping("/messages")
    public ResponseEntity<Message> newMessage(@RequestBody Message message){
        if (message.getMessageText().length()> 255 || message.getMessageText() == ""){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (!accountService.getByID(message.getPostedBy())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Message createdMessage = messageService.createMessage(message);
        return new ResponseEntity<>(createdMessage, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Account> registerUser(@RequestBody Account account){
        Account temp = accountService.getByUsername(account.getUsername());
        if (temp != null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Account savedAccount = accountService.saveAccount(account);
        return new ResponseEntity<>(savedAccount, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Account> loginUser(@RequestBody Account account){
        Account temp = accountService.getByUsername(account.getUsername());
        if (temp == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (account.getPassword().equals(temp.getPassword())){
            return new ResponseEntity<>(temp, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}
