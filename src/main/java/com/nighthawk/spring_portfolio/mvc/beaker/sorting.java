package com.nighthawk.spring_portfolio.mvc.beaker;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api/sort")
public class sorting {

    // Base class for sorting algorithms
    abstract static class SortingAlgorithm {
        abstract void sort(int[] arr);
    }

    // Implementation of Merge sort algorithm
    static class MergeSort extends SortingAlgorithm {
        @Override
        void sort(int[] arr) {
            Arrays.sort(arr);
        }
    }

    // Implementation of Insertion sort algorithm
    static class InsertionSort extends SortingAlgorithm {
        @Override
        void sort(int[] arr) {
            int n = arr.length;
            for (int i = 1; i < n; ++i) {
                int key = arr[i];
                int j = i - 1;

                // Shift elements greater than key to the right
                while (j >= 0 && arr[j] > key) {
                    arr[j + 1] = arr[j];
                    j = j - 1;
                }
                arr[j + 1] = key;
            }
        }
    }

    // Implementation of Bubble sort algorithm
    static class BubbleSort extends SortingAlgorithm {
        @Override
        void sort(int[] arr) {
            int n = arr.length;
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    // Swap if the element found is greater than the next element
                    if (arr[j] > arr[j + 1]) {
                        int temp = arr[j];
                        arr[j] = arr[j + 1];
                        arr[j + 1] = temp;
                    }
                }
            }
        }
    }

    // Implementation of Selection sort algorithm
    static class SelectionSort extends SortingAlgorithm {
        @Override
        void sort(int[] arr) {
            int n = arr.length;
            for (int i = 0; i < n - 1; i++) {
                int minIdx = i;
                for (int j = i + 1; j < n; j++) {
                    // Find the index of minimum element
                    if (arr[j] < arr[minIdx]) {
                        minIdx = j;
                    }
                }
                // Swap the found minimum element with the first element
                int temp = arr[minIdx];
                arr[minIdx] = arr[i];
                arr[i] = temp;
            }
        }
    }

    // Endpoint to measure and compare sorting algorithm speeds
    @GetMapping("/speeds")
    public Map<String, Integer> getAlgorithmSpeeds(@RequestParam(required = false) Integer arraySize) {
        // Set default array size if not provided
        int size = (arraySize != null && arraySize > 0) ? arraySize : 30000;

        // Generate a random array
        int[] randomArray = generateRandomArray(size);

        // Measure sorting speed for each algorithm and store in a map
        Map<String, Integer> algorithmSpeeds = new HashMap<>();
        algorithmSpeeds.put("mergeSort", measureSortingSpeed(new MergeSort(), randomArray.clone()));
        algorithmSpeeds.put("insertionSort", measureSortingSpeed(new InsertionSort(), randomArray.clone()));
        algorithmSpeeds.put("bubbleSort", measureSortingSpeed(new BubbleSort(), randomArray.clone()));
        algorithmSpeeds.put("selectionSort", measureSortingSpeed(new SelectionSort(), randomArray.clone()));

        return algorithmSpeeds;
    }

    // Generate an array of given size with random integers
    private int[] generateRandomArray(int size) {
        int[] randomArray = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            randomArray[i] = random.nextInt(100);
        }
        return randomArray;
    }

    // Run the sorting algorithm on the given array
    private void runSortingAlgorithm(SortingAlgorithm algorithm, int[] arr) {
        algorithm.sort(arr);
    }

    // Measure the execution time of the sorting algorithm
    private int measureSortingSpeed(SortingAlgorithm algorithm, int[] arr) {
        long startTime = System.currentTimeMillis();
        runSortingAlgorithm(algorithm, arr);
        long endTime = System.currentTimeMillis();
        return (int) (endTime - startTime);
    }
}
