package com.hackathon.devlegacy.repository;

import com.hackathon.devlegacy.model.CodeEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CodeEntryRepository extends MongoRepository<CodeEntry, String> {
    List<CodeEntry> findByUserId(String userId);

    // That's it! Spring Data MongoDB gives us save(), findAll(), findById(), etc. for free!
}