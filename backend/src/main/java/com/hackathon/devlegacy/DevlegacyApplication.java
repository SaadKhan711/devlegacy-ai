package com.hackathon.devlegacy;

import com.hackathon.devlegacy.model.CodeEntry;
import com.hackathon.devlegacy.repository.CodeEntryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DevlegacyApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevlegacyApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(CodeEntryRepository repository) {
        return args -> {
            // Create a sample entry
            CodeEntry testEntry = new CodeEntry();
            testEntry.setUserId("test-user-123");
            testEntry.setOriginalCode("public static void main(String[] args) {}");

            // Save it to the database
            repository.save(testEntry);


        };
    }
}