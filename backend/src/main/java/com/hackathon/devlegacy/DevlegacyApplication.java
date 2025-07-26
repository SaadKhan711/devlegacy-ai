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

    // This block will run once the application has started
    // We'll use it to test our database connection
    @Bean
    CommandLineRunner runner(CodeEntryRepository repository) {
        return args -> {
            // Create a sample entry
            CodeEntry testEntry = new CodeEntry();
            testEntry.setUserId("test-user-123");
            testEntry.setOriginalCode("public static void main(String[] args) {}");

            // Save it to the database
            repository.save(testEntry);

            // Print a success message to our console
            System.out.println("******************************************");
            System.out.println("Successfully saved test entry to MongoDB!");
            System.out.println("******************************************");
        };
    }
}