package com.example.demo.repository;

import com.example.demo.model.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation, Long> {

    List<Reservation> findAllByUserIdAndActiveTrue(Long userId);
}
