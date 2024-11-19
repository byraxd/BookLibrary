package com.example.demo.repository;

import com.example.demo.model.BorrowRecord;
import com.example.demo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {
    Optional<User> findUserByBorrowedBooksContaining(BorrowRecord borrowRecord);
}
