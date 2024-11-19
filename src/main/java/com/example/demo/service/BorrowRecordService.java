package com.example.demo.service;

import com.example.demo.model.BorrowRecord;
import com.example.demo.repository.BorrowRecordRepository;

import java.util.List;

public interface BorrowRecordService {
    List<BorrowRecord> getAll();
    BorrowRecord borrowByUserId(Long userId, Long bookId);
    BorrowRecord returnBookByUserId(Long bookId, Long userId);
    List<BorrowRecord> getBorrowRecordsByUserId(Long userId);
}
