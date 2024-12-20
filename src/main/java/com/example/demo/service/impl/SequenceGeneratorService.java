package com.example.demo.service.impl;

import com.example.demo.model.DbSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

@Service
public class SequenceGeneratorService {

    private final MongoOperations mongoOperations;

    @Autowired
    public SequenceGeneratorService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public long generateSequence(String sequenceName) {
        Query query = new Query(Criteria.where("_id").is(sequenceName));

        DbSequence dbSequence = mongoOperations.findAndModify(query,
                new Update().inc("seqNo", 1),
                options().returnNew(true).upsert(true),
                DbSequence.class);

        return !Objects.isNull(dbSequence) ? dbSequence.getSeqNo() : 1;

    }
}
