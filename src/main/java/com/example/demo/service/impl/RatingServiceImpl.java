package com.example.demo.service.impl;

import com.example.demo.model.Book;
import com.example.demo.model.Rating;
import com.example.demo.model.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.RatingRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final SequenceGeneratorService sequenceGenerator;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository, UserRepository userRepository, BookRepository bookRepository, SequenceGeneratorService sequenceGenerator) {
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.sequenceGenerator = sequenceGenerator;
    }
    @Override
    public Rating submitRating(Long userId, Long bookId, Rating rating) {
        User user = checkAndGetUserByUserId(userId);
        Book book = checkANdGetBookByBookId(bookId);
        Objects.requireNonNull(rating, "rating is null");

        Rating newRating = Rating
                .builder()
                .id(sequenceGenerator.generateSequence(Rating.SEQUENCE_NAME))
                .user(user)
                .book(book)
                .rating(rating.getRating())
                .review(rating.getReview())
                .build();

        return ratingRepository.save(newRating) ;
    }
    private User checkAndGetUserByUserId(Long userId){
        Objects.requireNonNull(userId, "User id is null");

        return userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found by following id"));
    }

    private Book checkANdGetBookByBookId(Long bookId){
        Objects.requireNonNull(bookId, "Book id is null");

        return bookRepository.findById(bookId).orElseThrow(() -> new NoSuchElementException("Book not found by following id"));
    }
}
