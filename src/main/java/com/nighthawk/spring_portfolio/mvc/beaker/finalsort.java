
package com.nighthawk.spring_portfolio.mvc.beaker;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api/finalsort")
@CrossOrigin(origins="https://ige-csa.github.io/")

public class FinalSort {

    // Abstract base class defining the common structure for sorting algorithms
    abstract static class SortingAlgorithm {
        abstract void sort(int[] arr);
        abstract int getOperations();
        int comparisons = 0; // To count comparisons
        int iterations = 0; // Added to count iterations

        int getComparisons() {
            return comparisons;
        }

        int getIterations() { // Getter for iterations
            return iterations;
        }
    }

    // Concrete subclass inherits common behavior from abstract class while providing speific implementations for each sort method 

    class MergeSort extends SortingAlgorithm {
        private int merges = 0;

        @Override
        void sort(int[] arr) {
            int[] temp = new int[arr.length];
            mergeSort(arr, temp, 0, arr.length - 1);
        }

        private void mergeSort(int[] arr, int[] temp, int left, int right) {
            if (left < right) {
                iterations++;
                int mid = (left + right) / 2;
                mergeSort(arr, temp, left, mid);
                mergeSort(arr, temp, mid + 1, right);
                merge(arr, temp, left, mid, right);
            }
        }

        private void merge(int[] arr, int[] temp, int left, int mid, int right) {
            for (int i = left; i <= right; i++) {
                iterations++;
                temp[i] = arr[i];
            }

            int i = left;
            int j = mid + 1;
            int k = left;

            while (i <= mid && j <= right) {
                comparisons++; // Counting each comparison
                if (temp[i] <= temp[j]) {
                    arr[k] = temp[i];
                    i++;
                } else {
                    arr[k] = temp[j];
                    j++;
                }
                k++;
                merges++; // Counting merge operations
            }

            while (i <= mid) {
                arr[k] = temp[i];
                i++;
                k++;
                merges++;
            }
        }

        @Override
        int getOperations() {
            return merges;
        }
    }

    class InsertionSort extends SortingAlgorithm {
        private int swaps = 0;

        @Override
        void sort(int[] arr) {
            int n = arr.length;
            for (int i = 1; i < n; ++i) {
                iterations++; // Increment iterations
                int key = arr[i];
                int j = i - 1;

                // Adding comparison counting
                while (j >= 0 && ++comparisons > 0 && arr[j] > key) {
                    arr[j + 1] = arr[j];
                    j = j - 1;
                    swaps++;
                }
                arr[j + 1] = key;
            }
    }
        @Override
        int getOperations() {
            return swaps;
        }
    }

    class BubbleSort extends SortingAlgorithm {
        private int swaps = 0;
        @Override
        void sort(int[] arr) {
            int n = arr.length;
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    iterations++; // Increment iterations
                    // Adding comparison counting
                    if (++comparisons > 0 && arr[j] > arr[j + 1]) {
                        // Swap elements
                        int temp = arr[j];
                        arr[j] = arr[j + 1];
                        arr[j + 1] = temp;
                        swaps++;
                    }
                }
            }
        }

        @Override
        int getOperations() {
            return swaps;
        }
    }

    class SelectionSort extends SortingAlgorithm {
        private int swaps = 0;

        @Override
        void sort(int[] arr) {
            int n = arr.length;
            for (int i = 0; i < n - 1; i++) {
                int minIdx = i;
                for (int j = i + 1; j < n; j++) {
                    iterations++; // Increment iterations
                    // Adding comparison counting
                    if (++comparisons > 0 && arr[j] < arr[minIdx]) {
                        minIdx = j;
                    }
                }
                // Swap the found minimum element with the first element
                int temp = arr[minIdx];
                arr[minIdx] = arr[i];
                arr[i] = temp;
                swaps++;
            }
        }

        @Override
        int getOperations() {
            return swaps;
        }
    }

    @GetMapping("/everything")
    public Map<String, Integer> getData(@RequestParam(required = false) Integer arraySize) {

        //10000 random integers
        int size = (arraySize != null && arraySize > 0) ? arraySize : 10000;

        int[] randomArray = generateRandomArray(size);

        Map<String, Integer> algorithmData = new HashMap<>();

        algorithmData.put("mergeSort", measureSortingSpeed(new MergeSort(), randomArray.clone()));
        algorithmData.put("insertionSort", measureSortingSpeed(new InsertionSort(), randomArray.clone()));
        algorithmData.put("bubbleSort", measureSortingSpeed(new BubbleSort(), randomArray.clone()));
        algorithmData.put("selectionSort", measureSortingSpeed(new SelectionSort(), randomArray.clone()));
        algorithmData.put("mergeSwap", measureSwaps(new MergeSort(), randomArray.clone()));
        algorithmData.put("insertionSwap", measureSwaps(new InsertionSort(), randomArray.clone()));
        algorithmData.put("bubbleSwap", measureSwaps(new BubbleSort(), randomArray.clone()));
        algorithmData.put("selectionSwap", measureSwaps(new SelectionSort(), randomArray.clone()));
        algorithmData.put("mergeComp", measureComparisons(new MergeSort(), randomArray.clone()));
        algorithmData.put("insertionComp", measureComparisons(new InsertionSort(), randomArray.clone()));
        algorithmData.put("bubbleComp", measureComparisons(new BubbleSort(), randomArray.clone()));
        algorithmData.put("selectionComp", measureComparisons(new SelectionSort(), randomArray.clone()));
        algorithmData.put("mergeIterations", measureIterations(new MergeSort(), randomArray.clone()));
        algorithmData.put("insertionIterations", measureIterations(new InsertionSort(), randomArray.clone()));
        algorithmData.put("bubbleIterations", measureIterations(new BubbleSort(), randomArray.clone()));
        algorithmData.put("selectionIterations", measureIterations(new SelectionSort(), randomArray.clone()));
        

        return algorithmData;
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

    @GetMapping("/comparisons")
    public Map<String, Integer> getComparisonCounts(@RequestParam(required = false) Integer arraySize) {
        int size = (arraySize != null && arraySize > 0) ? arraySize : 10000;

        int[] randomArray = generateRandomArray(size);

        Map<String, Integer> comparisonCounts = new HashMap<>();

        comparisonCounts.put("mergeSort", measureComparisons(new MergeSort(), randomArray.clone()));
        comparisonCounts.put("insertionSort", measureComparisons(new InsertionSort(), randomArray.clone()));
        comparisonCounts.put("bubbleSort", measureComparisons(new BubbleSort(), randomArray.clone()));
        comparisonCounts.put("selectionSort", measureComparisons(new SelectionSort(), randomArray.clone()));

        return comparisonCounts;
    }

    @GetMapping("/iterations")
    public Map<String, Integer> getIterationCounts(@RequestParam(required = false) Integer arraySize) {
        int size = (arraySize != null && arraySize > 0) ? arraySize : 10000;

        int[] randomArray = generateRandomArray(size);

        Map<String, Integer> iterationCounts = new HashMap<>();

        iterationCounts.put("mergeSort", measureIterations(new MergeSort(), randomArray.clone()));
        iterationCounts.put("insertionSort", measureIterations(new InsertionSort(), randomArray.clone()));
        iterationCounts.put("bubbleSort", measureIterations(new BubbleSort(), randomArray.clone()));
        iterationCounts.put("selectionSort", measureIterations(new SelectionSort(), randomArray.clone()));

        return iterationCounts;
    }


//     //   Helper method to generate a fixed array for testing (~3000)
//     private int[] generateFixedArray() {
//         return new int[]{69,350,262,99,827,739,156,165,202,969,10,434,986,739,124,3,162,611,212,635,366,937,588,914,664,981,980,412,761,10,526,905,625,372,494,622,424,344,425,793,360,269,936,526,92,764,640,722,684,613,541,248,284,465,463,226,128,28,842,412,579,303,463,696,224,3,363,96,185,685,316,867,53,725,262,450,2,132,510,371,727,963,789,471,670,55,406,188,771,552,503,912,490,442,698,210,967,670,857,24,614,302,736,205,862,169,598,408,812,701,524,160,414,751,261,985,376,109,775,98,743,767,256,696,993,305,716,129,866,677,833,810,859,360,207,21,910,135,847,964,311,805,31,591,549,585,787,683,499,872,393,848,67,420,933,439,228,427,557,315,418,309,752,383,783,962,301,914,312,426,221,461,894,437,281,823,951,196,426,556,45,618,735,634,284,583,595,15,275,500,520,129,230,567,808,52,226,367,21,609,205,529,390,547,121,715,841,342,892,87,119,775,24,168,59,120,603,727,428,360,941,876,191,443,478,901,300,972,603,666,711,830,810,163,825,588,390,881,362,80,994,791,892,827,290,761,25,330,835,788,415,32,835,388,497,795,990,973,194,281,307,635,567,613,162,504,531,237,436,305,897,462,10,546,23,471,207,125,904,816,989,866,372,275,730,842,621,119,743,657,951,773,54,6,830,182,2,355,78,643,476,141,139,849,524,710,382,539,664,658,204,866,245,146,527,556,919,231,861,815,605,39,338,232,247,938,887,442,910,881,841,796,636,644,358,875,746,934,757,735,902,401,987,556,747,397,626,693,555,738,982,394,679,285,13,689,823,834,928,852,498,466,69,311,933,890,809,393,639,638,281,819,9,402,527,358,671,942,217,519,773,880,620,890,604,477,305,343,420,939,373,164,559,677,176,612,270,920,955,579,117,783,543,918,709,37,714,23,135,841,884,192,604,284,81,455,242,210,993,286,779,79,55,841,673,741,713,277,515,765,380,52,258,889,104,488,500,723,581,590,80,757,301,826,233,998,460,794,994,840,809,930,739,156,328,940,739,971,552,583,943,482,443,560,663,833,390,590,67,114,281,741,366,214,102,753,36,820,145,994,976,566,330,618,376,321,844,668,511,123,392,458,940,244,860,252,987,514,164,931,812,795,253,903,709,179,848,597,254,97,907,196,282,485,444,927,771,442,853,354,10,426,921,924,282,807,178,927,782,1000,340,321,775,881,513,707,2,522,946,558,185,533,708,453,359,511,657,5,768,445,832,922,226,637,406,349,788,134,184,644,126,994,976,721,342,726,404,467,880,398,403,830,597,721,951,974,306,500,497,948,810,29,637,87,496,371,152,249,716,596,44,158,919,249,606,110,28,216,936,564,686,103,325,787,609,228,660,812,154,421,129,236,671,70,532,158,410,885,537,289,723,271,884,380,301,296,834,969,929,661,907,445,895,576,856,285,196,107,602,814,444,743,67,777,144,893,698,130,327,801,51,57,766,597,953,737,750,541,345,331,636,711,484,120,559,636,685,448,981,632,537,694,581,696,664,789,239,417,447,671,74,711,747,452,70,192,852,167,65,896,334,745,64,492,339,873,838,414,149,145,784,554,625,473,671,894,975,374,241,634,770,975,256,462,268,5,304,51,57,404,849,717,977,654,643,921,809,33,314,280,431,809,230,253,537,466,344,277,966,191,279,771,893,21,19,461,344,575,499,626,63,951,806,74,331,775,854,373,356,896,828,914,749,466,78,47,793,125,112,75,574,811,974,221,920,255,587,238,47,247,123,225,917,302,386,743,87,252,595,509,649,966,388,837,722,903,896,514,999,559,219,365,328,366,443,592,429,372,96,847,605,319,254,29,187,762,716,603,585,514,446,616,295,630,573,884,626,379,859,584,691,497,883,45,654,582,618,20,66,616,718,647,931,558,882,594,276,216,765,63,887,135,405,154,113,30,834,404,708,2,736,222,65,772,881,961,316,929,889,727,905,934,13,278,183,172,249,481,900,534,959,498,540,435,723,229,612,169,303,250,985,328,183,82,57,892,769,133,326,600,205,229,569,58,811,676,467,67,716,560,204,788,632,169,639,842,935,233,579,48,728,624,630,809,299,489,209,365,433,226,16,617,640,549,359,918,556,75,699,997,807,837,873,703,393,512,279,501,726,688,490,664,556,127,599,244,958,647,828,726,785,457,637,606,80,808,832,645,614,988,188,129,389,832,102,652,408,158,802,780,254,753,652,115,595,809,11,955,229,983,115,688,830,53,406,876,402,690,329,648, 779,69,138,204,537,668,31,196,864,654,41,35,282,37,599,128,724,265,431,529,945,209,406,559,47,174,16,967,371,225,223,924,109,132,415,828,173,13,445,533,484,240,214,12,907,865,566,450,686,777,514,259,568,487,231,429,745,752,998,988,462,497,201,90,781,935,833,588,651,589,523,142,3,218,729,350,502,574,876,765,38,498,734,292,782,556,5,653,407,302,806,620,397,184,285,269,130,641,135,719,169,953,449,680,944,808,483,956,663,6,940,754,27,856,809,26,793,110,847,693,408,202,976,934,958,723,527,687,61,271,452,607,861,610,562,640,949,313,746,187,978,564,583,186,658,62,743,688,308,17,87,413,76,892,882,387,39,366,434,507,435,317,710,432,852,359,903,388,323,939,400,422,353,456,694,638,123,553,177,489,826,736,251,424,789,441,4,113,623,510,301,626,28,403,103,593,810,590,825,254,325,637,2,248,346,348,63,578,923,243,420,496,207,357,645,261,246,832,486,121,145,221,57,263,700,324,621,616,197,848,380,969,642,247,722,844,40,233,830,475,306,185,155,82,851,703,147,470,551,631,989,499,274,84,421,749,190,582,286,759,23,985,677,96,389,684,44,726,343,119,131,565,383,898,775,370,614,42,815,915,617,534,71,974,478,321,674,203,228,725,414,798,170,591,480,829,652,766,105,704,238,418,316,528,649,268,767,894,67,258,45,451,948,612,216,273,959,382,276,554,375,647,280,395,771,100,613,845,839,977,344,296,906,531,327,544,230,333,596,627,912,416,594,695,158,411,33,159,226,558,931,116,518,99,304,555,536,819,126,505,920,757,115,106,215,952,521,98,957,740,584,753,552,520,242,711,102,525,443,783,140,74,947,660,300,899,89,816,818,341,22,374,718,987,918,644,1000,108,335,101,994,199,501,172,205,446,661,315,576,256,917,602,398,481,156,884,192,157,262,563,405,149,198,409,91,46,206,303,733,846,293,601,319,360,78,961,72,85,770,744,508,671,419,545,208,68,571,560,669,739,814,195,447,605,821,454,666,200,393,902,227,542,682,926,699,840,792,585,802,960,857,822,717,399,690,311,7,624,183,625,485,36,720,278,996,550,307,609,287,29,916,468,188,546,336,643,635,879,66,886,778,60,705,547,630,1,217,120,727,941,604,356,491,182,459,326,460,909,731,513,741,675,854,914,64,538,143,751,522,702,859,10,153,794,77,863,835,506,320,587,758,570,697,440,639,511,672,762,874,592,968,984,148,117,290,543,297,929,943,97,883,748,426,482,349,241,742,471,932,887,619,340,981,681,730,55,314,933,973,390,160,114,73,322,650,354,962,893,338,373,955,229,676,701,761,561,615,127,279,464,966,836,595,655,772,379,30,679,838,608,11,803,785,438,18,473,70,656,790,881,992,807,575,163,220,946,890,266,453,32,20,75,709,65,139,983,735,991,401,689,455,328,721,795,942,219,191,476,494,954,253,622,255,396,430,667,796,972,541,381,180,15,530,342,365,820,524,910,853,526,347,133,732,281,361,92,376,79,997,310,938,980,540,577,665,239,385,146,919,211,712,706,355,801,800,567,394,791,787,466,747,270,849,176,469,855,905,284,252,193,377,283,134,363,410,950,963,161,716,368,93,428,878,472,904,995,154,548,629,295,713,50,738,80,964,831,378,866,118,144,166,797,351,244,867,423,895,579,598,692,606,888,925,104,850,971,837,873,58,696
// };     
//     }

    private int[] generateRandomArray(int size) {
        int[] randomArray = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            randomArray[i] = random.nextInt(100);
        }
        return randomArray;
    }

    // Helper method to run a sorting algorithm and measure its speed 
    private void runSortingAlgorithm(SortingAlgorithm algorithm, int[] arr) {
        algorithm.sort(arr);
    }
    // 

    private int measureSortingSpeed(SortingAlgorithm algorithm, int[] arr) {
        long startTime = System.currentTimeMillis();
        runSortingAlgorithm(algorithm, arr);
        long endTime = System.currentTimeMillis();
        return (int) (endTime - startTime);
    }

    private int measureSwaps(SortingAlgorithm algorithm, int[] arr) {
        // Create a new instance of the sorting algorithm class
        SortingAlgorithm newAlgorithmInstance;

        if (algorithm instanceof MergeSort) {
            newAlgorithmInstance = new MergeSort();
        } else if (algorithm instanceof InsertionSort) {
            newAlgorithmInstance = new InsertionSort();
        } else if (algorithm instanceof BubbleSort) {
            newAlgorithmInstance = new BubbleSort();
        } else if (algorithm instanceof SelectionSort) {
            newAlgorithmInstance = new SelectionSort();
        } else {
            throw new IllegalArgumentException("Unknown sorting algorithm");
        }

        runSortingAlgorithm(newAlgorithmInstance, arr);
        return newAlgorithmInstance.getOperations();
    }

    private int measureComparisons(SortingAlgorithm algorithm, int[] arr) {
        runSortingAlgorithm(algorithm, arr);
        return algorithm.getComparisons();
    }

    private int measureIterations(SortingAlgorithm algorithm, int[] arr) {
        runSortingAlgorithm(algorithm, arr);
        return algorithm.getIterations();
    }

}
