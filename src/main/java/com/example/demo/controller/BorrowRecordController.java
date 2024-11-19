package com.example.demo.controller;

import com.example.demo.model.BorrowRecord;
import com.example.demo.service.BorrowRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrowRecords")
public class BorrowRecordController {

    private final BorrowRecordService borrowRecordService;

    @Autowired
    public BorrowRecordController(BorrowRecordService borrowRecordService) {
        this.borrowRecordService = borrowRecordService;
    }

    @PostMapping("/borrow/{userId}/{bookId}")
    public ResponseEntity<BorrowRecord> borrow(@PathVariable Long userId, @PathVariable Long bookId) {
        return ResponseEntity.ok(borrowRecordService.borrowByUserId(userId, bookId));
    }

    @PostMapping("/return/{userId}/{bookId}")
    public ResponseEntity<BorrowRecord> returnBorrowedBook(@PathVariable Long userId, @PathVariable Long bookId) {
        return ResponseEntity.ok(borrowRecordService.returnBookByUserId(bookId, userId));
    }

    @GetMapping("/user/{userId}/borrowed")
    public ResponseEntity<List<BorrowRecord>> getAllBorrowRecords(@PathVariable Long userId) {
        return ResponseEntity.ok(borrowRecordService.getBorrowRecordsByUserId(userId));
    }
}
