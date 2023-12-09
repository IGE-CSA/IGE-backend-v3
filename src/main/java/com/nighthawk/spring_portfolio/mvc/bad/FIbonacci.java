package com.nighthawk.spring_portfolio.mvc.bad;

import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/fibonacci")

public class FIbonacci {

    abstract static class FibonacciAlgorithm {
        abstract long fibonacci(int length);
    }

    class ForLoopFibonacci extends FibonacciAlgorithm {
        @Override
        long fibonacci(int length) {
            long a = 0, b = 1, c, i;
            for (i = 2; i < length; i++) {
                c = a + b;
                a = b;
                b = c;
            }
            return b;
        }
    }

    class WhileLoopFibonacci extends FibonacciAlgorithm {
        @Override
        long fibonacci(int length) {
            long a = 0, b = 1;
            int i = 2;
            while (i < length) {
                long temp = a + b;
                a = b;
                b = temp;
                i++;
            }
            return b;
        }
    }

    class RecursionFibonacci extends FibonacciAlgorithm {
        @Override
        long fibonacci(int length) {
            return finishRecursion(length);
        }

        private long finishRecursion(int n) {
            if (n <= 1) {
                return n;
            }
            return finishRecursion(n - 1) + finishRecursion(n - 2);
        }
    }

    class MatrixFibonacci extends FibonacciAlgorithm {
        @Override
        long fibonacci(int length) {
            return matrixRecursive(length);
        }

        private long matrixRecursive(int n) {
            if (n == 0) {
                return 0;
            }
            long a = 0, b = 1, temp;
            for (int i = 2; i <= n; i++) {
                temp = a + b;
                a = b;
                b = temp;
            }
            return a;
        }
    }

    class BinetFibonacci extends FibonacciAlgorithm {
        @Override
        long fibonacci(int length) {
            // Implement Binet Fibonacci here
            // Binet's formula: F(n) = (phi^n - (-phi)^(-n)) / sqrt(5)
            double phi = (1 + Math.sqrt(5)) / 2;
            double result = (Math.pow(phi, length) - Math.pow(-phi, -length)) / Math.sqrt(5);
            // You can choose to return the result or use it as needed
            return (long) result;
        }
    }

    @GetMapping("/speeds")
    public Map<String, Integer> getAlgorithmSpeeds(@RequestParam(required = false) Integer length) {
        int size = (length != null && length > 0) ? length : 40;

        Map<String, Integer> algorithmSpeeds = new HashMap<>();

        algorithmSpeeds.put("forLoopFibonacci", measureFibonacciTime(new ForLoopFibonacci(), size));
        algorithmSpeeds.put("whileLoopFibonacci", measureFibonacciTime(new WhileLoopFibonacci(), size));
        algorithmSpeeds.put("recursionFibonacci", measureFibonacciTime(new RecursionFibonacci(), size));
        algorithmSpeeds.put("MatrixFibonacci", measureFibonacciTime(new MatrixFibonacci(), size));
        algorithmSpeeds.put("BinetFibonacci", measureFibonacciTime(new BinetFibonacci(), size));

        return algorithmSpeeds;
    }

    private void runFibonacciAlgorithm(FibonacciAlgorithm algorithm, int size) {
        algorithm.fibonacci(size);
    }

    private int measureFibonacciTime(FibonacciAlgorithm algorithm, int size) {
    long startTime = System.nanoTime();
    runFibonacciAlgorithm(algorithm, size);
    long endTime = System.nanoTime();
    
    // Use TimeUnit.MILLISECONDS to convert nanoseconds to milliseconds
    return (int) TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
}

}