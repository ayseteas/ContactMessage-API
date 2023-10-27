package com.techproeducation.backendproject.initialwork.mapper;

import com.techproeducation.backendproject.initialwork.dto.ContactMessageDTO;
import com.techproeducation.backendproject.initialwork.entity.ContactMessage;

public class ContactMessageMapper {

        public static void toEntityFromDTO(ContactMessage contactMessage, ContactMessageDTO dto) {
            if (dto.getName() != null) {
                contactMessage.setName(dto.getName());
            }
            if (dto.getEmail() != null) {
                contactMessage.setEmail(dto.getEmail());
            }
            if (dto.getSubject() != null) {
                contactMessage.setSubject(dto.getSubject());
            }
            if (dto.getMessage() != null) {
                contactMessage.setMessage(dto.getMessage());
            }
        }

        public static ContactMessageDTO toDTO(ContactMessage contactMessage) {
            ContactMessageDTO contactMessageDTO = new ContactMessageDTO();
            contactMessageDTO.setName(contactMessage.getName());
            contactMessageDTO.setEmail(contactMessage.getEmail());
            contactMessageDTO.setSubject(contactMessage.getSubject());
            contactMessageDTO.setMessage(contactMessage.getMessage());
            return contactMessageDTO;
        }

        public static ContactMessage toEntity(ContactMessageDTO contactMessageDTO) {
            ContactMessage contactMessage = new ContactMessage();
            contactMessage.setName(contactMessageDTO.getName());
            contactMessage.setEmail(contactMessageDTO.getEmail());
            contactMessage.setSubject(contactMessageDTO.getSubject());
            contactMessage.setMessage(contactMessageDTO.getMessage());
            return contactMessage;
        }
    }












