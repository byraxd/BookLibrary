package com.example.demo.service;

import com.example.demo.model.Rating;

public interface RatingService {

    Rating submitRating(Long userId, Long bookId, Rating rating);
}
