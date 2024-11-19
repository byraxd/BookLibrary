package com.example.demo.service;

import com.example.demo.model.Reservation;

import java.util.List;

public interface ReservationService {

    List<Reservation> findAllByUserId(Long userId);
    Reservation createReservation(Long userId, Long bookId);
}
