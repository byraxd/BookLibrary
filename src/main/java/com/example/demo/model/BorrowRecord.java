package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "BorrowRecord")
public class BorrowRecord {

    @Transient
    public static final String SEQUENCE_NAME = "borrowRecord_sequence";

    @Id
    private Long id;
    private Long bookId;
    private Instant borrowDate;
    private Instant dueDate;
    private Boolean returned;
    private Double fineAmount;
}
