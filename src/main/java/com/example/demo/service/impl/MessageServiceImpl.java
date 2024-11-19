package com.example.demo.service.impl;

import com.example.demo.model.Book;
import com.example.demo.model.BorrowRecord;
import com.example.demo.model.Message;
import com.example.demo.model.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.MessageRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.BorrowRecordService;
import com.example.demo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {


    private final SequenceGeneratorService sequenceGeneratorService;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final BorrowRecordService borrowRecordService;
    private final BookRepository bookRepository;

    @Autowired
    public MessageServiceImpl(SequenceGeneratorService sequenceGeneratorService, MessageRepository messageRepository, UserRepository userRepository, BorrowRecordService borrowRecordService, BookRepository bookRepository) {
        this.sequenceGeneratorService = sequenceGeneratorService;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.borrowRecordService = borrowRecordService;
        this.bookRepository = bookRepository;
    }


    @Override
    public Message sendMessage(Message message) {
        checkForNullMessage(message);

        Message newMessage = Message
                .builder()
                .id(sequenceGeneratorService.generateSequence(Message.SEQUENCE_NAME))
                .message(message.getMessage())
                .timestamp(Instant.now())
                .delivered(false)
                .user(message.getUser())
                .build();

        return messageRepository.save(newMessage);
    }

    @Override
    @Scheduled(cron = "0 0 0 * * ?")
    public List<Message> sendMessageToUsersWhereDueDateIsBefore() {
        List<BorrowRecord> borrowRecords = borrowRecordService.getAll();
        Objects.requireNonNull(borrowRecords, "Was found zero borrow Records");

        List<BorrowRecord> borrowRecordWithDueDateIsBefore = borrowRecords
                .stream()
                .filter(borrowRecord -> Instant.now().isAfter(borrowRecord.getDueDate()))
                .filter(borrowRecord -> !borrowRecord.getReturned())
                .toList();

        List<Message> messages = new ArrayList<>();
        for(BorrowRecord borrowRecord : borrowRecordWithDueDateIsBefore) {
            User user = userRepository.findUserByBorrowedBooksContaining(borrowRecord).orElseThrow(() -> new NoSuchElementException("User not found for borrow record"));
            Book book = bookRepository.findById(borrowRecord.getBookId()).orElseThrow(() -> new NoSuchElementException("Book not found"));

            Message messageToSend = Message
                    .builder()
                    .id(sequenceGeneratorService.generateSequence(Message.SEQUENCE_NAME))
                    .message("Good day, "+ user.getName()+ ",book " + book.getTitle()+ " rental is overdue," +
                            "please return book or u will pay a FineAmount for each day." +
                            "Due Date: " + borrowRecord.getDueDate() + " " +
                            "Current Fine amount: " + borrowRecord.getFineAmount())
                    .user(user)
                    .timestamp(Instant.now())

                    .build();
            messages.add(messageToSend);
        }
        return messageRepository.saveAll(messages);
    }

    @Override
    public Message updateMessage(Message message) {
        Message messageForUpdate = messageRepository.findById(message.getId()).orElseThrow(() -> new NoSuchElementException("Message not found"));

        messageForUpdate.setMessage(message.getMessage());
        messageForUpdate.setTimestamp(message.getTimestamp());
        messageForUpdate.setStatus(message.getStatus());
        messageForUpdate.setDelivered(message.getDelivered());
        messageForUpdate.setUser(message.getUser());

        return messageRepository.save(messageForUpdate);
    }

    private void checkForNullMessage(Message message) {
        Objects.requireNonNull(message, "Message must not be null");
        Objects.requireNonNull(message.getUser(), "Message user must not be null");
        Objects.requireNonNull(message.getMessage(), "Message of message must not be null");
    }
}
