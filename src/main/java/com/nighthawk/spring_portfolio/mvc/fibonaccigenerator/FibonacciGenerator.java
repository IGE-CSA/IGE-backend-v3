package com.nighthawk.spring_portfolio.mvc.fibonaccigenerator;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/fibonaccigenerator")
@CrossOrigin(origins="https://ige-csa.github.io/")

public class FibonacciGenerator {

    public static List<Long> generateFibonacciSequence(long fibonacciNumber) {
        List<Long> fibonacciSequence = new ArrayList<>();

        long a = 0, b = 1;
        while (a <= fibonacciNumber) {
            fibonacciSequence.add(a);
            long temp = a + b;
            a = b;
            b = temp;
        }

        return fibonacciSequence;
    }
}

