package com.example.demo.service.impl;

import com.example.demo.exceptions.BookAlreadyAvailableException;
import com.example.demo.model.Book;
import com.example.demo.model.Reservation;
import com.example.demo.model.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository, UserRepository userRepository, BookRepository bookRepository, SequenceGeneratorService sequenceGeneratorService) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public List<Reservation> findAllByUserId(Long userId) {
        return reservationRepository.findAllByUserIdAndActiveTrue(userId);
    }

    @Override
    @Transactional
    public Reservation createReservation(Long userId, Long bookId) {
        Objects.requireNonNull(userId, "userId is null");
        Objects.requireNonNull(bookId, "bookId is null");

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("user not found"));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("book not found"));

        if(book.getAvailableCopies() != 0) throw new BookAlreadyAvailableException("book already available");

        Reservation reservation = Reservation
                .builder()
                .id(sequenceGeneratorService.generateSequence(Reservation.SEQUENCE_NAME))
                .userId(user.getId())
                .bookId(book.getId())
                .reservationDate(Instant.now())
                .active(true)
                .build();

        return reservationRepository.save(reservation);
    }
}
