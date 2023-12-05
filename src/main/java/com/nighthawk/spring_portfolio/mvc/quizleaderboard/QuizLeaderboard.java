package com.nighthawk.spring_portfolio.mvc.quizleaderboard;

import java.util.HashMap;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  // Annotations to simplify writing code (ie constructors, setters)
@NoArgsConstructor
@AllArgsConstructor
@Entity // Annotation to simplify creating an entity, which is a lightweight persistence domain object. Typically, an entity represents a table in a relational database, and each entity instance corresponds to a row in that table.
public class QuizLeaderboard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // @Column(unique=true)
    private String leaders;

    private int score;

    // starting scores

    public static HashMap<String, Integer> init() {
        HashMap<String, Integer> quizleaders = new HashMap<>();
        return quizleaders;
    }
}