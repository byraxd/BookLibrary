package com.example.demo.repository;

import com.example.demo.model.Mark;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkRepository extends MongoRepository<Mark, Long> {

}
