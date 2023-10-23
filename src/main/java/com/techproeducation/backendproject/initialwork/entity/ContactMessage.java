package com.techproeducation.backendproject.initialwork.entity;


import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ContactMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private String email;

    private String subject;

    private String message;

    //private LocalDateTime localDateTime = LocalDateTime.now();

    @Setter(AccessLevel.NONE)
    private String creationDateTime;

    @PrePersist
    private void setDateTime(){
        ZoneId zoneid = ZoneId.of("America/New_York");
        LocalDateTime creationDate = LocalDateTime.now(zoneid);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        creationDateTime = creationDate.format(dateTimeFormatter);
    }

}

