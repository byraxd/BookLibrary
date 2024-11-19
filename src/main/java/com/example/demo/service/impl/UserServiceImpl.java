package com.example.demo.service.impl;

import com.example.demo.model.BorrowRecord;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SequenceGeneratorService sequenceGenerator;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, SequenceGeneratorService sequenceGenerator) {
        this.userRepository = userRepository;
        this.sequenceGenerator = sequenceGenerator;
    }


    @Override
    public User addUser(User user) {
        Objects.requireNonNull(user, "user is null");

        User newUser = User.builder()
                .id(sequenceGenerator.generateSequence(User.SEQUENCE_NAME))
                .name(user.getName())
                .email(user.getEmail())
                .maxBorrowLimit(user.getMaxBorrowLimit())
                .borrowedBooks(user.getBorrowedBooks())
                .build();

        return userRepository.save(newUser);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("user not found"));
    }

    @Override
    public User updateUser(Long id, User user) {
        Objects.requireNonNull(user, "user is null");

        User userForUpdate = userRepository.findById(user.getId()).orElseThrow(() -> new NoSuchElementException("user not found"));

        userForUpdate.setName(user.getName());
        userForUpdate.setEmail(user.getEmail());
        userForUpdate.setMaxBorrowLimit(user.getMaxBorrowLimit());
        userForUpdate.setBorrowedBooks(user.getBorrowedBooks());

        return userRepository.save(userForUpdate);
    }

    @Override
    public void deleteUserById(Long id) {

        User userForDelete = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("user not found"));

        userRepository.delete(userForDelete);
    }

    @Override
    public Double getTotalFineAmountFromUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("user not found"));

        Double totalValue = 0.0;
        for(BorrowRecord borrowRecord: user.getBorrowedBooks()){
            totalValue += borrowRecord.getFineAmount();
        }

        return totalValue;
    }
}
