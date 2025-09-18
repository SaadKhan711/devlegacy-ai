package com.hackathon.devlegacy.repository;

import com.hackathon.devlegacy.model.CodeEntry;
import org.springframework.data.jpa.repository.JpaRepository; // Import JpaRepository
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CodeEntryRepository extends JpaRepository<CodeEntry, Long> { // Updated here
    List<CodeEntry> findByUserId(String userId);
}