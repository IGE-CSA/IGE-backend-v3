// Import necessary packages and classes
package com.nighthawk.spring_portfolio.mvc.beaker;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

// Define a REST controller for sorting algorithms
@RestController
@RequestMapping("/api/sort")
public class sorting {

    // Define an abstract class for sorting algorithms
    abstract static class SortingAlgorithm {
        abstract void sort(int[] arr);

        abstract int getSwaps();
    }

    // Define a class for Merge Sort algorithm
    static class MergeSort extends SortingAlgorithm {
        private int swaps = 0;

        // Implement the sort method for Merge Sort
        @Override
        void sort(int[] arr) {
            // Merge Sort is not implemented explicitly, using Arrays.sort() as a placeholder
            Arrays.sort(arr);
        }

        // Implement the getSwaps method to get the number of swaps performed
        @Override
        int getSwaps() {
            return swaps;
        }
    }

    // Define a class for Insertion Sort algorithm
    static class InsertionSort extends SortingAlgorithm {
        private int swaps = 0;

        // Implement the sort method for Insertion Sort
        @Override
        void sort(int[] arr) {
            int n = arr.length;
            for (int i = 1; i < n; ++i) {
                int key = arr[i];
                int j = i - 1;

                // Move elements greater than key to one position ahead of their current position
                while (j >= 0 && arr[j] > key) {
                    arr[j + 1] = arr[j];
                    j = j - 1;
                    
                    // Increment swaps count
                    swaps++;
                }
                arr[j + 1] = key;
            }
        }

        // Implement the getSwaps method to get the number of swaps performed
        @Override
        int getSwaps() {
            return swaps;
        }
    }

    // Define a class for Bubble Sort algorithm
    static class BubbleSort extends SortingAlgorithm {
        private int swaps = 0;

        // Implement the sort method for Bubble Sort
        @Override
        void sort(int[] arr) {
            int n = arr.length;

            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    // Swap elements if they are in the wrong order
                    if (arr[j] > arr[j + 1]) {
                        // Swap elements
                        int temp = arr[j];
                        arr[j] = arr[j + 1];
                        arr[j + 1] = temp;

                        // Increment swaps count
                        swaps++;
                    }
                }
            }
        }

        // Implement the getSwaps method to get the number of swaps performed
        @Override
        int getSwaps() {
            return swaps;
        }
    }

    // Define a class for Selection Sort algorithm
    static class SelectionSort extends SortingAlgorithm {
        private int swaps = 0;

        // Implement the sort method for Selection Sort
        @Override
        void sort(int[] arr) {
            int n = arr.length;
            for (int i = 0; i < n - 1; i++) {
                int minIdx = i;
                for (int j = i + 1; j < n; j++) {
                    // Find the minimum element in the unsorted part of the array
                    if (arr[j] < arr[minIdx]) {
                        minIdx = j;
                    }
                }
                // Swap the found minimum element with the first element
                int temp = arr[minIdx];
                arr[minIdx] = arr[i];
                arr[i] = temp;

                // Increment swaps count
                swaps++;
            }
        }

        // Implement the getSwaps method to get the number of swaps performed
        @Override
        int getSwaps() {
            return swaps;
        }
    }

    // Define an endpoint to get the execution speeds of sorting algorithms
    @GetMapping("/speeds")
    public Map<String, Integer> getAlgorithmSpeeds(@RequestParam(required = false) Integer arraySize) {
        // Set default array size if not provided
        int size = (arraySize != null && arraySize > 0) ? arraySize : 30000;

        // Generate a random array of the specified size
        int[] randomArray = generateRandomArray(size);

        // Create a map to store algorithm execution speeds
        Map<String, Integer> algorithmSpeeds = new HashMap<>();

        // Measure the execution speed of each sorting algorithm
        algorithmSpeeds.put("mergeSort", measureSortingSpeed(new MergeSort(), randomArray.clone()));
        algorithmSpeeds.put("insertionSort", measureSortingSpeed(new InsertionSort(), randomArray.clone()));
        algorithmSpeeds.put("bubbleSort", measureSortingSpeed(new BubbleSort(), randomArray.clone()));
        algorithmSpeeds.put("selectionSort", measureSortingSpeed(new SelectionSort(), randomArray.clone()));

        // Return the map of algorithm execution speeds
        return algorithmSpeeds;
    }

    // Define an endpoint to get the number of swaps performed by sorting algorithms
    @GetMapping("/swaps")
    public Map<String, Integer> getSwapCounts(@RequestParam(required = false) Integer arraySize) {
        // Set default array size if not provided
        int size = (arraySize != null && arraySize > 0) ? arraySize : 30000;

        // Generate a random array of the specified size
        int[] randomArray = generateRandomArray(size);

        // Create a map to store the number of swaps for each algorithm
        Map<String, Integer> swapCounts = new HashMap<>();

        // Measure the number of swaps performed by each sorting algorithm
        swapCounts.put("mergeSort", measureSwaps(new MergeSort(), randomArray.clone()));
        swapCounts.put("insertionSort", measureSwaps(new InsertionSort(), randomArray.clone()));
        swapCounts.put("bubbleSort", measureSwaps(new BubbleSort(), randomArray.clone()));
        swapCounts.put("selectionSort", measureSwaps(new SelectionSort(), randomArray.clone()));

        // Return the map of the number of swaps for each algorithm
        return swapCounts;
    }

    // Helper method to generate a random array of a given size
    private int[] generateRandomArray(int size) {
        int[] randomArray = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            randomArray[i] = random.nextInt(100);
        }
        return randomArray;
    }

    // Helper method to run a sorting algorithm and measure its execution speed
    private void runSortingAlgorithm(SortingAlgorithm algorithm, int[] arr) {
        algorithm.sort(arr);
    }

    // Helper method to measure the execution speed of a sorting algorithm
    private int measureSortingSpeed(SortingAlgorithm algorithm, int[] arr) {
        long startTime = System.currentTimeMillis();
        runSortingAlgorithm(algorithm, arr);
        long endTime = System.currentTimeMillis();
        // Return the time taken for the algorithm to execute
        return (int) (endTime - startTime);
    }

    // Helper method to measure the number of swaps performed by a sorting algorithm
    private int measureSwaps(SortingAlgorithm algorithm, int[] arr) {
        // Run the sorting algorithm to count swaps
        runSortingAlgorithm(algorithm, arr);
        // Return the number of swaps performed by the algorithm
        return algorithm.getSwaps();
    }
}