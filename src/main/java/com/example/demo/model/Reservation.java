package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "reservation")
public class Reservation {

    @Transient
    public static final String SEQUENCE_NAME = "reservation_sequence";

    @Id
    private Long id;
    private Long userId;
    private Long bookId;
    private Instant reservationDate;
    private Boolean active;


}
