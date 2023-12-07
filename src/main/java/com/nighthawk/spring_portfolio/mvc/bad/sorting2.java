// Import necessary packages and classes
package com.nighthawk.spring_portfolio.mvc.bad;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
//import java.util.Random;

// Define a REST controller for sorting algorithms
@RestController
@RequestMapping("/api/sort2")
@CrossOrigin(origins = "https://ige-csa.github.io/")
public class sorting2 {

    // Define an abstract class for sorting algorithms
    abstract static class SortingAlgorithm {
        public int swaps;

        abstract void sort(int[] arr);

        abstract int getSwaps();
    }

    // Define a class for Quick Sort algorithm
    static class QuickSort extends SortingAlgorithm {
        private int swaps = 0;

        // Implement the sort method for Quick Sort
        @Override
        void sort(int[] arr) {
            quickSort(arr, 0, arr.length - 1);
        }

        // Implement the partition method for Quick Sort
        private int partition(int[] arr, int low, int high) {
            int pivot = arr[high];
            int i = low - 1;
            for (int j = low; j < high; j++) {
                if (arr[j] < pivot) {
                    i++;
                    // Swap elements
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;

                    // Increment swaps count
                    swaps++;
                }
            }

            // Swap the pivot element with the element at i+1
            int temp = arr[i + 1];
            arr[i + 1] = arr[high];
            arr[high] = temp;

            // Increment swaps count
            swaps++;

            return i + 1;
        }

        // Implement the recursive quickSort method for Quick Sort
        private void quickSort(int[] arr, int low, int high) {
            if (low < high) {
                int partitionIndex = partition(arr, low, high);

                quickSort(arr, low, partitionIndex - 1);
                quickSort(arr, partitionIndex + 1, high);
            }
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

    // Correct num of swaps: 
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
        // Use the predefined array 
        int[] predefinedArray = generatePredefinedArray();

        // Create a map to store algorithm execution speeds
        Map<String, Integer> algorithmSpeeds = new HashMap<>();

        // Measure the execution speed of each sorting algorithm
        algorithmSpeeds.put("quickSort", measureSortingSpeed(new QuickSort(), predefinedArray.clone()));
        algorithmSpeeds.put("insertionSort", measureSortingSpeed(new InsertionSort(), predefinedArray.clone()));
        algorithmSpeeds.put("bubbleSort", measureSortingSpeed(new BubbleSort(), predefinedArray.clone()));
        algorithmSpeeds.put("selectionSort", measureSortingSpeed(new SelectionSort(), predefinedArray.clone()));

        // Return the map of algorithm execution speeds
        return algorithmSpeeds;
    }

    // Define an endpoint to get the number of swaps performed by sorting algorithms
    @GetMapping("/swaps")
    public Map<String, Integer> getSwapCounts(@RequestParam(required = false) Integer arraySize) {
        // Use the predefined array 
        int[] predefinedArray = generatePredefinedArray();

        // Create a map to store the number of swaps for each algorithm
        Map<String, Integer> swapCounts = new HashMap<>();

        // Measure the number of swaps performed by each sorting algorithm
        swapCounts.put("quickSort", measureSwaps(new QuickSort(), predefinedArray.clone()));
        swapCounts.put("insertionSort", measureSwaps(new InsertionSort(), predefinedArray.clone()));
        swapCounts.put("bubbleSort", measureSwaps(new BubbleSort(), predefinedArray.clone()));
        swapCounts.put("selectionSort", measureSwaps(new SelectionSort(), predefinedArray.clone()));

        // Return the map of the number of swaps for each algorithm
        return swapCounts;
    }

    // Helper method to generate a predefined array 
    private int[] generatePredefinedArray() {
        // Replace the following array with your desired array 
        int[] predefinedArray = {
            774, 191, 2, 329, 703, 309, 485, 974, 267, 58, 989, 301, 406, 922, 223, 524, 981, 457, 272, 584, 560, 580, 440, 423, 976, 981, 559, 194, 49, 376, 316, 829, 389, 635, 415, 813, 663, 704, 246, 673, 974, 10, 471, 161, 923, 328, 3, 223, 512, 923, 989, 679, 884, 827, 776, 806, 265, 563, 628, 164, 831, 482, 392, 918, 381, 465, 242, 13, 984, 565, 183, 377, 166, 287, 736, 213, 563, 686, 264, 788, 875, 601, 926, 860, 292, 704, 485, 24, 940, 796, 84, 946, 593, 948, 259, 274, 281, 413, 752, 794, 675, 685, 262, 245, 15, 754, 907, 263, 508, 899, 231, 836, 297, 162, 159, 965, 27, 825, 151, 610, 561, 433, 295, 690, 272, 276, 281, 906, 874, 5, 66, 527, 682, 832, 30, 307, 377, 55, 202, 179, 351, 251, 570, 315, 741, 444, 670, 618, 614, 700, 606, 268, 157, 551, 686, 71, 438, 183, 363, 32, 185, 733, 308, 550, 926, 987, 271, 508, 396, 799, 102, 987, 530, 231, 394, 689, 259, 593, 179, 801, 202, 935, 684, 171, 508, 811, 460, 432, 369, 569, 583, 991, 731, 107, 408, 548, 883, 855, 117, 227, 102, 265, 971, 917, 619, 335, 237, 989, 23, 542, 840, 225, 622, 718, 453, 919, 899, 786, 263, 188, 625, 951, 305, 197, 746, 144, 180, 960, 738, 746, 327, 113, 811, 38, 434, 778, 458, 759, 889, 18, 404, 953, 782, 867, 793, 123, 936, 849, 636, 593, 951, 497, 781, 6, 561, 692, 230, 36, 87, 880, 92, 939, 690, 361, 139, 877, 292, 919, 94, 48, 944, 209, 105, 409, 447, 642, 635, 510, 919, 27, 857, 729, 227, 598, 15, 125, 741, 662, 744, 530, 828, 682, 588, 129, 950, 641, 171, 267, 486, 774, 7, 532, 933, 217, 680, 270, 131, 341, 561, 76, 422, 911, 70, 128, 238, 522, 418, 729, 472, 900, 527, 583, 114, 157, 292, 269, 464, 902, 451, 923, 79, 551, 513, 823, 430, 742, 384, 723, 914, 102, 780, 727, 201, 765, 268, 424, 98, 585, 989, 487, 858, 762, 103, 607, 786, 178, 812, 987, 280, 739, 317, 192, 197, 902, 349, 764, 781, 680, 653, 641, 647, 760, 420, 731, 207, 706, 844, 309, 573, 823, 571, 218, 993, 882, 295, 678, 619, 899, 670, 670, 331, 892, 978, 880, 127, 213, 360, 358, 847, 347, 432, 222, 972, 917, 741, 212, 65, 101, 534, 507, 998, 998, 495, 913, 609, 417, 161, 24, 781, 827, 218, 214, 966, 758, 906, 571, 317, 276, 926, 416, 284, 868, 348, 86, 5, 80, 212, 680, 137, 444, 538, 286, 557, 28, 633, 972, 419, 922, 47, 389, 529, 668, 420, 95, 873, 152, 333, 781, 199, 757, 604, 91, 899, 914, 985, 747, 663, 335, 794, 715, 944, 479, 381, 781, 346, 901, 570, 160, 297, 850, 839, 474, 587, 389, 764, 314, 317, 295, 755, 953, 697, 118, 502, 330, 650, 930, 368, 465, 143, 450, 891, 378, 881, 891, 134, 662, 687,
        };        
        return predefinedArray;
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
        // Reset swaps count before running the sorting algorithm
        algorithm.swaps = 0;
        
        // Run the sorting algorithm to count swaps
        runSortingAlgorithm(algorithm, arr);
        
        // Return the number of swaps performed by the algorithm
        return algorithm.getSwaps();
    }
}

