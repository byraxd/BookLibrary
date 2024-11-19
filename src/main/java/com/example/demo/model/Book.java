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
@Document(collection = "Book")
public class Book {

    @Transient
    public static final String SEQUENCE_NAME = "book_sequence";

    @Id
    private Long id;
    private String title;
    private String author;
    private Integer publishedYear;
    private String genre;
    private Integer availableCopies;
    private List<Mark>
}
