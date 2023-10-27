package com.techproeducation.backendproject.initialwork.controller;

import com.techproeducation.backendproject.initialwork.dto.ContactMessageDTO;
import com.techproeducation.backendproject.initialwork.entity.ContactMessage;
import com.techproeducation.backendproject.initialwork.mapper.ContactMessageMapper;
import com.techproeducation.backendproject.initialwork.service.ContactMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/messages")
public class ContactMessageController {

    @Autowired
    private ContactMessageService contactMessageService;

    @PostMapping  // http://localhost:8080/messages + POST
    public ResponseEntity<Map<String, String>> sendMessage(@Valid @RequestBody ContactMessageDTO contactMessageDTO){

        ContactMessage contactMessage = ContactMessageMapper.toEntity(contactMessageDTO);
        contactMessageService.saveMessage(contactMessage);
        Map<String, String> map = new HashMap<>();
        map.put("Message", "Message saved successfully");
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @GetMapping  //http:localhost:8080/messages + GET
    public ResponseEntity<List<ContactMessage>> getAllMessages(){
        List<ContactMessage> contactMessages = contactMessageService.getAllMessages();
        return  ResponseEntity.ok(contactMessages);
    }

    @GetMapping("/{id}")  // http://localhost:8080/messages/2 + GET
    public ResponseEntity<ContactMessage> getMessageById(@PathVariable Long id){
        ContactMessage contactMessage = contactMessageService.getMessageById(id);
        return ResponseEntity.ok(contactMessage);
    }

    @GetMapping("/email") // http://localhost:8080/messages/email + GET
    public ResponseEntity<List<ContactMessage>> getMessagesByEmail(@RequestParam String email) {
        List<ContactMessage> contactMessages = contactMessageService.findMessagesByEmail(email);
        return new ResponseEntity<>(contactMessages, HttpStatus.OK);
    }

    @GetMapping("/subject")   //http://localhost:8080/messages/subject  + GET
    public ResponseEntity<List<ContactMessage>> searchBySubject(@RequestParam("subject") String subject){
        List<ContactMessage> messageSubject = contactMessageService.searchBySubject(subject);
        return ResponseEntity.ok(messageSubject);
    }

    @DeleteMapping("/query")   // http://localhost:8080/messages/query?id=2 + DELETE
    public ResponseEntity<Map<String, String>> deleteMessageById(@RequestParam("id") Long id){

        contactMessageService.deleteMessageById(id);
        Map<String, String> map = new HashMap<>();
        map.put("message", "Contact Message has been deleted successfully.");
        map.put("status", "true");

        return ResponseEntity.ok(map);
    }

    @DeleteMapping("/{id}")  // http://localhost:8080/messages/2 + DELETE
    public ResponseEntity<Map<String, String>> deleteMessageByPath(@PathVariable("id") Long id){

        contactMessageService.deleteMessageByPath(id);
        Map<String, String> map = new HashMap<>();
        map.put("message", "Contact Message has been deleted successfully.");
        map.put("status", "true");

        return ResponseEntity.ok(map);
    }

    @GetMapping("/pagination") // http://localhost:8080/messages/pagination?page=0&size=10&sort=name&direction=ASC + GET
    public ResponseEntity<Page<ContactMessage>> getAllMessagesWithPagination(
            @RequestParam("page") int page, // Page number
            @RequestParam("size") int size, // Items per page
            @RequestParam("sort") String sort, // Sort based on a data (name, price, etc.) (OPTIONAL)
            @RequestParam("direction") Sort.Direction direction // Ascending or Descending order (A-Z / Z-A, High to Low /  Low to High, etc.)
    ){
        Pageable pageable = PageRequest.of(page-1, size, Sort.by(direction, sort));

        Page<ContactMessage> pageOfMessage = contactMessageService.getAllMessagesWithPagination(pageable);

        return ResponseEntity.ok(pageOfMessage);
    }

    @GetMapping("/findByDateRange")
    public ResponseEntity<List<ContactMessage>> findMessagesByDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        List<ContactMessage> messages = contactMessageService.findMessagesByDateRange(start, end);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/findByTimeRange")
    public ResponseEntity<List<ContactMessage>> findMessagesByTimeRange(
            @RequestParam String startTime,
            @RequestParam String endTime
    ) {
        LocalTime start = LocalTime.parse(startTime);
        LocalTime end = LocalTime.parse(endTime);
        List<ContactMessage> contactMessageList = contactMessageService.findMessagesByTimeRange(start, end);
        return ResponseEntity.status(HttpStatus.OK).body(contactMessageList);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ContactMessage> updateContactMessage8(
            @PathVariable Long id,
            @Valid @RequestBody ContactMessageDTO contactMessageDTO,
            BindingResult bindingResult
    ) {
        ContactMessage updatedMessage = contactMessageService.updateMessageByID(id, contactMessageDTO, bindingResult);
        return ResponseEntity.ok(updatedMessage);
    }
}


