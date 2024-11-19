package com.example.demo.service;

import com.example.demo.model.Book;

import java.util.List;

public interface BookService {
    Book addBook(Book book);
    List<Book> getAllBooks();
    Book getBookById(Long id);
    Book updateBook(Long id, Book book);
    void deleteBook(Long id);
    Boolean isAvailable(Long id);
}
