package com.nighthawk.spring_portfolio.mvc.fibonnaci.algorithms;

import org.springframework.stereotype.Service;

@Service
public class Binet {

    public FibonacciResult calculate(int n) {
        long startTime = System.currentTimeMillis();

        // Calculate the golden ratio
        double phi = (1 + Math.sqrt(5)) / 2;

        // Calculate the nth Fibonacci number using Binet's formula, rounded to the nearest integer
        int fibonacci = (int) Math.round(Math.pow(phi, n) / Math.sqrt(5));

        long endTime = System.currentTimeMillis();
        System.out.println("Intermediate Result: " + (endTime - startTime) + " milliseconds");

        // Duration in milliseconds
        long duration = (endTime - startTime) / 1000000;

        return new FibonacciResult(fibonacci, duration);
    }

    // Inner class representing the result of the Fibonacci calculation
    public static class FibonacciResult {
        private final long time;
        private final int fibNum;

        // Constructor to initialize the result with the Fibonacci number and time taken
        public FibonacciResult(int fibNum, long time) {
            this.fibNum = fibNum;
            this.time = time;
        }

         // Getter method for retrieving the time taken for the calculation
        public long getTime() {
            return time;
        }

        // Getter method for retrieving the Fibonacci number
        public int getFibNum() {
            return fibNum;
        }

       
    }
}
