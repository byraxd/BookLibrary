package com.example.demo.repository;

import com.example.demo.model.BorrowRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowRecordRepository extends MongoRepository<BorrowRecord, Long> {

    BorrowRecord findBorrowRecordByBookId(Long bookId);
}
