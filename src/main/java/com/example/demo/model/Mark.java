package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "Mark")
public class Mark {

    @Transient
    public static final String SEQUENCE_NAME = "mark_sequence";

    @Id
    private Long id;
    private Integer markedPage;
    private User user;
    private Book book;
}
