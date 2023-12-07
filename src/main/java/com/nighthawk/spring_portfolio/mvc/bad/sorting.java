// Generate Random Array, Speed always works, Swap eh 
package com.nighthawk.spring_portfolio.mvc.bad;

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

    abstract static class SortingAlgorithm {
        abstract void sort(int[] arr);

        abstract int getSwaps();
    }

    static class MergeSort extends SortingAlgorithm {
        private int swaps = 0;

        @Override
        void sort(int[] arr) {
            Arrays.sort(arr);
        }

        @Override
        int getSwaps() {
            return swaps;
        }
    }

    static class InsertionSort extends SortingAlgorithm {
        private int swaps = 0;

        @Override
        void sort(int[] arr) {
            int n = arr.length;
            for (int i = 1; i < n; ++i) {
                int key = arr[i];
                int j = i - 1;

                while (j >= 0 && arr[j] > key) {
                    arr[j + 1] = arr[j];
                    j = j - 1;
                    swaps++;
                }
                arr[j + 1] = key;
            }
        }

        @Override
        int getSwaps() {
            return swaps;
        }
    }

    static class BubbleSort extends SortingAlgorithm {
        private int swaps = 0;

        @Override
        void sort(int[] arr) {
            int n = arr.length;

            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
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

        @Override
        int getSwaps() {
            return swaps;
        }
    }

    static class SelectionSort extends SortingAlgorithm {
        private int swaps = 0;

        @Override
        void sort(int[] arr) {
            int n = arr.length;
            for (int i = 0; i < n - 1; i++) {
                int minIdx = i;
                for (int j = i + 1; j < n; j++) {
                    if (arr[j] < arr[minIdx]) {
                        minIdx = j;
                    }
                }
                int temp = arr[minIdx];
                arr[minIdx] = arr[i];
                arr[i] = temp;
                swaps++;
            }
        }

        @Override
        int getSwaps() {
            return swaps;
        }
    }

    @GetMapping("/speeds")
    public Map<String, Integer> getAlgorithmSpeeds(@RequestParam(required = false) Integer arraySize) {
        int size = (arraySize != null && arraySize > 0) ? arraySize : 10000;

        int[] randomArray = generateRandomArray(size);

        Map<String, Integer> algorithmSpeeds = new HashMap<>();

        algorithmSpeeds.put("mergeSort", measureSortingSpeed(new MergeSort(), randomArray.clone()));
        algorithmSpeeds.put("insertionSort", measureSortingSpeed(new InsertionSort(), randomArray.clone()));
        algorithmSpeeds.put("bubbleSort", measureSortingSpeed(new BubbleSort(), randomArray.clone()));
        algorithmSpeeds.put("selectionSort", measureSortingSpeed(new SelectionSort(), randomArray.clone()));

        return algorithmSpeeds;
    }

    @GetMapping("/swaps")
    public Map<String, Integer> getSwapCounts(@RequestParam(required = false) Integer arraySize) {
        int size = (arraySize != null && arraySize > 0) ? arraySize : 10000;

        int[] randomArray = generateRandomArray(size);

        Map<String, Integer> swapCounts = new HashMap<>();

        swapCounts.put("mergeSort", measureSwaps(new MergeSort(), randomArray.clone()));
        swapCounts.put("insertionSort", measureSwaps(new InsertionSort(), randomArray.clone()));
        swapCounts.put("bubbleSort", measureSwaps(new BubbleSort(), randomArray.clone()));
        swapCounts.put("selectionSort", measureSwaps(new SelectionSort(), randomArray.clone()));

        return swapCounts;
    }

    private int[] generateRandomArray(int size) {
        int[] randomArray = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            randomArray[i] = random.nextInt(100);
        }
        return randomArray;
    }

    private void runSortingAlgorithm(SortingAlgorithm algorithm, int[] arr) {
        algorithm.sort(arr);
    }

    private int measureSortingSpeed(SortingAlgorithm algorithm, int[] arr) {
        long startTime = System.currentTimeMillis();
        runSortingAlgorithm(algorithm, arr);
        long endTime = System.currentTimeMillis();
        return (int) (endTime - startTime);
    }

    private int measureSwaps(SortingAlgorithm algorithm, int[] arr) {
        runSortingAlgorithm(algorithm, arr);
        return algorithm.getSwaps();
    }
}
