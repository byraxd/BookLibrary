package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "Message")
public class Message {
    @Transient
    public static final String SEQUENCE_NAME = "message_sequence";

    @Id
    private Long id;
    private String message;
    private Instant timestamp;
    private String status;
    private Boolean delivered;
    private User user;

}
