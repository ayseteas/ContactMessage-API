package com.techproeducation.backendproject.initialwork.controller;

import com.techproeducation.backendproject.initialwork.entity.ContactMessage;
import com.techproeducation.backendproject.initialwork.service.ContactMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
public class ContactMessageController {

    @Autowired
    private ContactMessageService contactMessageService;

    @PostMapping
    public ResponseEntity<String> sendMessage(@RequestBody ContactMessage contactMessage){

       contactMessageService.saveMessage(contactMessage);

       String message = "message saved successfully";
       return new ResponseEntity<>(message, HttpStatus.CREATED);

    }
}
