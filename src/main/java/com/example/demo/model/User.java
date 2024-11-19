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
    private List<Mark> marks;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(maxBorrowLimit, user.maxBorrowLimit) && Objects.equals(borrowedBooks, user.borrowedBooks) && Objects.equals(marks, user.marks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, maxBorrowLimit, borrowedBooks, marks);
    }
}
