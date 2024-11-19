package com.example.demo.service.impl;

import com.example.demo.exceptions.LimitOfBorrowedBookException;
import com.example.demo.model.Book;
import com.example.demo.model.BorrowRecord;
import com.example.demo.model.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.BorrowRecordRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.BorrowRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BorrowRecordServiceImpl implements BorrowRecordService {

    private final BorrowRecordRepository borrowRecordRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    public BorrowRecordServiceImpl(BorrowRecordRepository borrowRecordRepository, BookRepository bookRepository, UserRepository userRepository, SequenceGeneratorService sequenceGeneratorService) {
        this.borrowRecordRepository = borrowRecordRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public List<BorrowRecord> getAll() {
        return borrowRecordRepository.findAll();
    }

    @Override
    @Transactional
    public BorrowRecord borrowByUserId(Long userId, Long bookId) {

        Book book = bookRepository.findById(bookId).orElseThrow(() -> new NoSuchElementException("Book not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));

        if(user.getMaxBorrowLimit() <= user.getBorrowedBooks().size()) throw new LimitOfBorrowedBookException("User have max limit of borrowed books");
        if(book.getAvailableCopies() == 0) throw new LimitOfBorrowedBookException("Book has no available copies");


        Instant now = Instant.now();

        BorrowRecord newBorrowRecord = BorrowRecord
                .builder()
                .id(sequenceGeneratorService.generateSequence(BorrowRecord.SEQUENCE_NAME))
                .bookId(book.getId())
                .borrowDate(now)
                .dueDate(now.plus(2, ChronoUnit.WEEKS))
                .returned(false)
                .build();

        user.getBorrowedBooks().add(newBorrowRecord);
        book.setAvailableCopies(book.getAvailableCopies() - 1);

        return borrowRecordRepository.save(newBorrowRecord);
    }

    @Override
    @Transactional
    public BorrowRecord returnBookByUserId(Long bookId, Long userId) {

        Book book = bookRepository.findById(bookId).orElseThrow(() -> new NoSuchElementException("Book not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));

        BorrowRecord borrowRecord = borrowRecordRepository.findBorrowRecordByBookId(book.getId());

        user.getBorrowedBooks().remove(borrowRecord);

        Double fineAmount = 0.0;
        if(Instant.now().isAfter(borrowRecord.getDueDate())) {
            fineAmount = ChronoUnit.DAYS.between(Instant.now(), borrowRecord.getDueDate()) * 0.50;
        }

        borrowRecord.setReturned(true);
        borrowRecord.setFineAmount(fineAmount);
        book.setAvailableCopies(book.getAvailableCopies() + 1);



        return borrowRecordRepository.save(borrowRecord);
    }

    @Override
    public List<BorrowRecord> getBorrowRecordsByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));

        return user.getBorrowedBooks();
    }
}

