package com.example.demo.service.impl;


import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private SequenceGeneratorService sequenceGenerator;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void addBook_test(){
        Book book = Book.builder()
                .title("title")
                .author("author")
                .publishedYear(2000)
                .genre("genre")
                .build();

        when(bookService.addBook(book)).thenReturn(book);

        Assertions.assertEquals(book, bookService.addBook(book));
    }

    @Test
    void getAllBooks_test(){
        Book book = Book.builder()
                .title("title")
                .author("author")
                .publishedYear(2000)
                .genre("genre")
                .build();
        Book book1 = Book.builder()
                .title("title1")
                .author("author1")
                .publishedYear(20001)
                .genre("genre1")
                .build();

        when(bookService.getAllBooks()).thenReturn(List.of(book, book1));

        Assertions.assertEquals(List.of(book, book1), bookService.getAllBooks());
    }

    @Test
    void updateBook_test(){
        Book book = Book.builder()
                .title("title")
                .author("author")
                .publishedYear(2000)
                .genre("genre")
                .build();

        Long id = 1L;
        Book bookForUpdate = Book.builder()

                .id(id)
                .title("1")
                .author("1")
                .publishedYear(1)
                .genre("1")
                .build();

        when(bookRepository.findById(id)).thenReturn(Optional.of(bookForUpdate));
        when(bookService.updateBook(id, book)).thenReturn(book);

        Assertions.assertEquals(book, bookService.updateBook(id, book));
    }
}
