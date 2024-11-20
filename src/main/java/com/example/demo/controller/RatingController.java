package com.example.demo.controller;

import com.example.demo.model.Rating;
import com.example.demo.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rating")
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/{userId}/{bookId}")
    public ResponseEntity<Rating> submitRating(@PathVariable Long userId,@PathVariable  Long bookId, @RequestBody Rating rating) {
        return new ResponseEntity<>(ratingService.submitRating(userId, bookId, rating), HttpStatus.CREATED);
    }
}
