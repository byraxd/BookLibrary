package com.example.demo.service.impl;

import com.example.demo.model.Book;
import com.example.demo.model.Mark;
import com.example.demo.model.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.MarkRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.MarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class MarkServiceImpl implements MarkService {

    private final MarkRepository markRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final SequenceGeneratorService sequenceGenerator;

    @Autowired
    public MarkServiceImpl(MarkRepository markRepository, BookRepository bookRepository, UserRepository userRepository, SequenceGeneratorService sequenceGenerator) {
        this.markRepository = markRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public List<Mark> getAll() {
        return markRepository.findAll();
    }

    @Override
    public Mark addMark(Mark mark) {
        checkMark(mark);

        Mark newMark = Mark
                .builder()
                .id(sequenceGenerator.generateSequence(Mark.SEQUENCE_NAME))
                .markedPage(mark.getMarkedPage())
                .book(mark.getBook())
                .user(mark.getUser())
                .build();

        return markRepository.save(newMark);
    }

    @Override
    public Mark updateMark(Mark mark) {
        Objects.requireNonNull(mark, "mark is null");
        Mark markForUpdate = markRepository.findById(mark.getId()).orElseThrow(() -> new NoSuchElementException("Mark not found"));

        markForUpdate.setMarkedPage(mark.getMarkedPage());
        markForUpdate.setBook(mark.getBook());
        markForUpdate.setUser(mark.getUser());

        return markRepository.save(markForUpdate);
    }

    @Override
    public void deleteMark(Long markId) {
        Objects.requireNonNull(markId, "mark is null");
        Mark mark = markRepository.findById(markId).orElseThrow(() -> new NoSuchElementException("Mark not found"));

        markRepository.deleteById(markId);
    }

    private void checkMark(Mark mark) {
        Objects.requireNonNull(mark, "mark is null");

        Book book = bookRepository.findById(mark.getBook().getId()).orElseThrow(() -> new NoSuchElementException("book not found"));
        User user = userRepository.findById(mark.getUser().getId()).orElseThrow(() -> new NoSuchElementException("user not found"));

        if (!book.equals(mark.getBook())) throw new IllegalArgumentException("book is not the same as book in mark");
        if (!user.equals(mark.getUser())) throw new IllegalArgumentException("user is not the same as user in mark");

    }
}
