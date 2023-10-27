package com.techproeducation.backendproject.initialwork.service;

import com.techproeducation.backendproject.initialwork.dto.ContactMessageDTO;
import com.techproeducation.backendproject.initialwork.entity.ContactMessage;
import com.techproeducation.backendproject.initialwork.exceptions.ResourceNotFoundException;
import com.techproeducation.backendproject.initialwork.mapper.ContactMessageMapper;
import com.techproeducation.backendproject.initialwork.repository.ContactMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.xml.bind.ValidationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ContactMessageService {

    @Autowired
    private ContactMessageRepository contactMessageRepository;

    //Saving a Message
    public void saveMessage(ContactMessage contactMessage) {
        contactMessageRepository.save(contactMessage);
    }

    //Get All Messages
    public List<ContactMessage> getAllMessages() {
        return contactMessageRepository.findAll();
    }

    //Get Message By ID
    public ContactMessage getMessageById(Long id) {
        return contactMessageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No message exists with id: " + id));
    }

    //Delete Message by ID
    public void deleteMessageById(Long id) {
        ContactMessage contactMessage = getMessageById(id);
        contactMessageRepository.deleteById(id);
    }

    //Delete Message by Path
    public void deleteMessageByPath(Long id) {
        ContactMessage contactMessage = getMessageById(id);
        contactMessageRepository.deleteById(id);
    }

    //Get All Messages By Pagination
    public Page<ContactMessage> getAllMessagesWithPagination(Pageable pageable) {
        Page<ContactMessage> contactMessagePage = contactMessageRepository.findAll(pageable);

        if (contactMessagePage.isEmpty()) {
            throw new ResourceNotFoundException("No contact messages were found matching the given criteria.");
        }
        List<ContactMessage> contactMessageList = contactMessagePage.getContent();
        return contactMessagePage;
    }

    //Find Message By Date Range
    public List<ContactMessage> findMessagesByDateRange(LocalDate start, LocalDate end) {
        List<ContactMessage> betweenDateRange = contactMessageRepository.findByCreationDateTimeBetween(start.atStartOfDay(), end.atTime(LocalTime.MAX));

        if (betweenDateRange.isEmpty()) {
            throw new ResourceNotFoundException("No messages found between " + start + " and " + end);
        }
        return betweenDateRange;
    }

    //Find Message By Time Range
    public List<ContactMessage> findMessagesByTimeRange(LocalTime startTime, LocalTime endTime) {
        LocalDate currentDate = LocalDate.now();
        LocalDateTime startDateTime = LocalDateTime.of(currentDate, startTime);
        LocalDateTime endDateTime = LocalDateTime.of(currentDate, endTime);

        List<ContactMessage> betweenTime = contactMessageRepository.findByCreationDateTimeBetween(startDateTime, endDateTime);

        if (betweenTime.isEmpty()) {
            throw new ResourceNotFoundException("No messages found between " + startDateTime + " and " + endDateTime);
        }
        return betweenTime;
    }

    //Get Messages By Email
    public List<ContactMessage> findMessagesByEmail(String email) {
        return contactMessageRepository.findAllByEmail(email);
    }

    //Search By Subject
    public List<ContactMessage> searchBySubject(String subject) {
        List<ContactMessage> messageSubject = contactMessageRepository.findBySubjectContaining(subject);

        if (messageSubject.isEmpty()) {
            throw new ResourceNotFoundException("No contact messages found with the given subject: " + subject);
        }
        return messageSubject;
    }

    public ContactMessage updateMessageByID(Long id, ContactMessageDTO contactMessageDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            try {
                throw new ValidationException("Validation Error");
            } catch (ValidationException e) {
                e.printStackTrace();
            }
        }
        ContactMessage existingMessage = getMessageById(id);
        ContactMessageMapper.toEntityFromDTO(existingMessage, contactMessageDTO);
        return contactMessageRepository.save(existingMessage);
    }
}


