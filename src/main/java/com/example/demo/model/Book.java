package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) && Objects.equals(title, book.title) && Objects.equals(author, book.author) && Objects.equals(publishedYear, book.publishedYear) && Objects.equals(genre, book.genre) && Objects.equals(availableCopies, book.availableCopies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, publishedYear, genre, availableCopies);
    }
}
