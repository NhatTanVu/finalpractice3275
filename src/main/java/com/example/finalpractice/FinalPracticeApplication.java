package com.example.finalpractice;

import com.example.finalpractice.entities.Student;
import com.example.finalpractice.repositories.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class FinalPracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinalPracticeApplication.class, args);
    }

//    @Bean
//    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
//        return args -> {
//            studentRepository.save(new Student(null, "Jam", new Date(), false, 1.2));
//            studentRepository.save(new Student(null, "Jen", new Date(), true, 3.2));
//            studentRepository.save(new Student(null, "Jok", new Date(), false, 1.0));
//            studentRepository.save(new Student(null, "Jos", new Date(), false, 4.2));
//        };
//    }
}
