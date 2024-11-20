package com.example.demo.service.impl;

import com.example.demo.model.Book;
import com.example.demo.model.Rating;
import com.example.demo.repository.BookRepository;
import com.example.demo.response.RatingResponse;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final SequenceGeneratorService sequenceGenerator;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, SequenceGeneratorService sequenceGenerator) {
        this.bookRepository = bookRepository;
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public Book addBook(Book book) {
        Objects.requireNonNull(book, "book is null");

        Book newBook = Book.builder()
                .id(sequenceGenerator.generateSequence(Book.SEQUENCE_NAME))
                .title(book.getTitle())
                .author(book.getAuthor())
                .publishedYear(book.getPublishedYear())
                .genre(book.getGenre())
                .build();

        return bookRepository.save(newBook);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Book not found"));
    }

    @Override
    public Book updateBook(Long id, Book book) {
        Objects.requireNonNull(book, "book is null");

        Book bookForUpdate = bookRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Book not found"));

        bookForUpdate.setTitle(book.getTitle());
        bookForUpdate.setAuthor(book.getAuthor());
        bookForUpdate.setPublishedYear(book.getPublishedYear());
        bookForUpdate.setGenre(book.getGenre());

        return bookRepository.save(bookForUpdate);
    }

    @Override
    public void deleteBook(Long id) {
        Book bookForDelete = bookRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Book not found"));

        bookRepository.delete(bookForDelete);
    }

    @Override
    public Boolean isAvailable(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Book not found"));

        return book.getAvailableCopies() > 0;
    }

    @Override
    public RatingResponse getAverageRatingWithAllReview(Long id) {
        Objects.requireNonNull(id, "id is null");
        Book book = bookRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Book not found"));

        RatingResponse ratingResponse = RatingResponse
                .builder()
                .averageRating(book.getRatings().stream().mapToInt(Rating::getRating).average().orElse(0.0))
                .reviews(book.getRatings().stream().map(Rating::getReview).toList())
                .build();

        return ratingResponse;
    }

}
