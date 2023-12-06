package com.nighthawk.spring_portfolio.mvc.beaker;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api/sort")
@CrossOrigin(origins="https://ige-csa.github.io/")
public class sorting {

    // Base class for sorting algorithms
    abstract static class SortingAlgorithm {
        private int swapCount; // Track the number of swaps

        abstract void sort(int[] arr);

        int getSwapCount() {
            return swapCount;
        }

        protected void incrementSwapCount() {
            swapCount++;
        }

        protected void resetSwapCount() {
            swapCount = 0;
        }
    }

    // Implementation of Merge sort algorithm 
    static class MergeSort extends SortingAlgorithm {
        private int mergeCount; // Track the number of merge operations

        @Override
        void sort(int[] arr) {
            resetSwapCount();
            resetMergeCount();
            mergeSort(arr, 0, arr.length - 1);
        }

        private void mergeSort(int[] arr, int left, int right) {
            if (left < right) {
                int mid = (left + right) / 2;
                mergeSort(arr, left, mid);
                mergeSort(arr, mid + 1, right);
                merge(arr, left, mid, right);
                incrementMergeCount();
            }
        }

        private void merge(int[] arr, int left, int mid, int right) {
            int n1 = mid - left + 1;
            int n2 = right - mid;
        
            // Create temporary arrays
            int[] leftArray = new int[n1];
            int[] rightArray = new int[n2];
        
            // Copy data to temporary arrays
            System.arraycopy(arr, left, leftArray, 0, n1);
            System.arraycopy(arr, mid + 1, rightArray, 0, n2);
        
            // Merge the temporary arrays
        
            // Initial indices of the temporary arrays
            int i = 0, j = 0;
        
            // Initial index of the merged subarray
            int k = left;
        
            while (i < n1 && j < n2) {
                if (leftArray[i] <= rightArray[j]) {
                    arr[k] = leftArray[i];
                    i++;
                } else {
                    arr[k] = rightArray[j];
                    j++;
                }
                k++;
            }
        
            // Copy remaining elements of leftArray[] if any
            while (i < n1) {
                arr[k] = leftArray[i];
                i++;
                k++;
            }
        
            // Copy remaining elements of rightArray[] if any
            while (j < n2) {
                arr[k] = rightArray[j];
                j++;
                k++;
            }
        }        
    
        int getMergeCount() {
            return mergeCount;
        }
    
        private void incrementMergeCount() {
            mergeCount++;
        }
    
        private void resetMergeCount() {
            mergeCount = 0;
        }

    }

    // Implementation of Insertion sort algorithm
    static class InsertionSort extends SortingAlgorithm {
        @Override
        void sort(int[] arr) {
            resetSwapCount();
            int n = arr.length;
            for (int i = 1; i < n; ++i) {
                int key = arr[i];
                int j = i - 1;

                // Shift elements greater than key to the right
                while (j >= 0 && arr[j] > key) {
                    arr[j + 1] = arr[j];
                    j = j - 1;
                    incrementSwapCount();
                }
                arr[j + 1] = key;
            }
        }
    }

    // Implementation of Bubble sort algorithm
    static class BubbleSort extends SortingAlgorithm {
        @Override
        void sort(int[] arr) {
            resetSwapCount();
            int n = arr.length;
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    // Swap if the element found is greater than the next element
                    if (arr[j] > arr[j + 1]) {
                        int temp = arr[j];
                        arr[j] = arr[j + 1];
                        arr[j + 1] = temp;
                        incrementSwapCount();
                    }
                }
            }
        }
    }

    // Implementation of Selection sort algorithm
    static class SelectionSort extends SortingAlgorithm {
        @Override
        void sort(int[] arr) {
            resetSwapCount();
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
                incrementSwapCount();
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

    // New endpoint to get the number of swaps for each sorting algorithm
    @GetMapping("/swaps")
    public Map<String, Integer> getAlgorithmSwaps(@RequestParam(required = false) Integer arraySize) {
        // Set default array size if not provided
        int size = (arraySize != null && arraySize > 0) ? arraySize : 30000;

        // Generate a random array
        int[] randomArray = generateRandomArray(size);

        // Get the number of swaps for each algorithm and store in a map
        Map<String, Integer> algorithmSwaps = new HashMap<>();
        algorithmSwaps.put("insertionSort", measureSwaps(new InsertionSort(), randomArray.clone()));
        algorithmSwaps.put("bubbleSort", measureSwaps(new BubbleSort(), randomArray.clone()));
        algorithmSwaps.put("selectionSort", measureSwaps(new SelectionSort(), randomArray.clone()));
        algorithmSwaps.put("mergeSort", measureMerges(new MergeSort(), randomArray.clone())); // Use measureMerges for merge sort
        return algorithmSwaps;
    }

    // Measure the number of swaps for the sorting algorithm
    private int measureSwaps(SortingAlgorithm algorithm, int[] arr) {
        runSortingAlgorithm(algorithm, arr);
        return algorithm.getSwapCount();
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

    // Measure the number of merges for the merge sort algorithm
    private int measureMerges(MergeSort mergeSort, int[] arr) {
        runSortingAlgorithm(mergeSort, arr);
        return mergeSort.getMergeCount();
    }
}

