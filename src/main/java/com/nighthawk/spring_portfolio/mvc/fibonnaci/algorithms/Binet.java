package com.nighthawk.spring_portfolio.mvc.fibonnaci.algorithms;

import org.springframework.stereotype.Service;

@Service
public class Binet {

    public FibonacciResult calculate(int n) {
        long startTime = System.nanoTime();
        // Calculate the golden ratio
        double phi = (1 + Math.sqrt(5)) / 2;

        // Calculate the nth Fibonacci number using Binet's formula, rounded to the nearest integer
        int fibonacci = (int) Math.round(Math.pow(phi, n) / Math.sqrt(5));

        System.out.println("Start Time (before loop): " + startTime);

        long endTime = System.nanoTime();
        System.out.println("Start Time (after loop): " + startTime);
        System.out.println("End Time: " + endTime);
        System.out.println("Intermediate Result: " + (endTime - startTime) + " nanoseconds");

        long duration = endTime - startTime;

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
