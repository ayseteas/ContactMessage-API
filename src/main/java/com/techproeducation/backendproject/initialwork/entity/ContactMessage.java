package com.techproeducation.backendproject.initialwork.entity;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Entity
public class ContactMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotBlank(message = "Name Field cannot be empty or null.")
    @NotEmpty(message = "Name Field cannot be empty.")
    @NotNull(message = "Name Field cannot be null.")
    @Size(min = 2, max = 25, message = "Name Field should be between {min} and {max}.")
    @Column(nullable = false, length = 25)
    private String name;

    @Email(message = "Please provide a valid e-mail.")
    @NotBlank(message = "E-mail Field cannot be empty")
    @NotNull(message = "E-mail Field cannot be null")
    @NotEmpty(message = "E-mail Field cannot be empty")
    private String email;

    @NotNull(message = "Subject Field cannot be null")
    @NotBlank(message = "Subject Field cannot be empty or null.")
    @NotEmpty(message = "Subject Field cannot be empty")
    private String subject;

    @NotNull(message = "Message Field cannot be null")
    @NotBlank(message = "Message Field cannot be empty or null.")
    @NotEmpty(message = "Message Field cannot be empty")
    private String message;

    @Setter(AccessLevel.NONE)
    private LocalDateTime creationDateTime;

    public ContactMessage() {
        creationDateTime = LocalDateTime.now(ZoneId.of("US/Eastern"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDate = creationDateTime.format(formatter);
        creationDateTime = LocalDateTime.parse(formattedDate, formatter).withSecond(0).withNano(0);
    }

}

