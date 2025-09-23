package com.hackathon.devlegacy.repository;

import com.hackathon.devlegacy.model.CodeEntry;
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CodeEntryRepository extends JpaRepository<CodeEntry, Long> {
    List<CodeEntry> findByUserId(String userId);
}