package com.example.demo.controller;

import com.example.demo.model.Reservation;
import com.example.demo.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Reservation>> getReservationsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(reservationService.findAllByUserId(userId));
    }

    @PostMapping("/reserve/{userId}/{bookId}")
    public ResponseEntity<Reservation> reserve(@PathVariable Long userId, @PathVariable Long bookId) {
        return new ResponseEntity<>(reservationService.createReservation(userId, bookId), HttpStatus.CREATED);
    }
}
