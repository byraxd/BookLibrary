package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "User")
public class User {

    @Transient
    public static final String SEQUENCE_NAME = "user_sequence";

    @Id
    private Long id;
    private String name;
    private String email;
    private Integer maxBorrowLimit;
    private List<BorrowRecord> borrowedBooks;

}
