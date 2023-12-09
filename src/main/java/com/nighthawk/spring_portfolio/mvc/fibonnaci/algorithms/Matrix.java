package com.nighthawk.spring_portfolio.mvc.fibonnaci.algorithms;

import org.springframework.stereotype.Service;

@Service
// MATRIX MORE EFFICIENT THAN BINET FOR LARGER VALUES OF N 
public class Matrix {

    // This method calculates the nth Fibonacci number using matrix exponentiation
    public FibonacciResult calculate(int n) {

        // time when algorithm execution begins in nanoseconds
        long startTime = System.nanoTime();

        // Perform matrix exponentiation to calculate the (n-1)th power of {{1, 1}, {1, 0}}
        int[][] result = matrixPower(new int[][]{{1, 1}, {1, 0}}, n - 1);
        
        // Extract the Fibonacci number from the result matrix
        int fibonacci = result[0][0];

        System.out.println("Start Time (before loop): " + startTime);
        
        // Duration in nanoseconds
        long endTime = System.nanoTime();
        System.out.println("Start Time (after loop): " + startTime);
        System.out.println("End Time: " + endTime);
        System.out.println("Intermediate Result: " + (endTime - startTime) + " nanoseconds");

        // Duration in nanoseconds
        long duration = endTime - startTime;

        return new FibonacciResult(fibonacci, duration);
    }

    // Helper method for matrix exponentiation
    private int[][] matrixPower(int[][] matrix, int n) {
        int[][] result = {{1, 0}, {0, 1}}; // Identity matrix
        
        // Perform matrix exponentiation using a binary exponentiation approach
        while (n > 0) {
            if (n % 2 == 1) {
                result = matrixMultiply(result, matrix);
            }
            n /= 2;
            matrix = matrixMultiply(matrix, matrix);
        }
        return result;
    }

    // Helper method for matrix multiplication
    private int[][] matrixMultiply(int[][] a, int[][] b) {
        int[][] result = new int[2][2];
        
        // Perform matrix multiplication
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return result;
    }

    // Inner class representing the result of the Fibonacci calculation
    public static class FibonacciResult {
        private final long time;
        private final int fibNum;

        // Constructor to initialize the result with the Fibonacci number and time taken
        public FibonacciResult(int fibNum, long time) {
            this.time = time;
            this.fibNum = fibNum;
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
