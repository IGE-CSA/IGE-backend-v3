// Predefined array, Speed works, Swap works except for merge 

package com.nighthawk.spring_portfolio.mvc.bad;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/sortS")

public class sortingS {

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

        // Replace the random array with a fixed array of your choosing
        int[] fixedArray = generateFixedArray();

        Map<String, Integer> algorithmSpeeds = new HashMap<>();

        algorithmSpeeds.put("mergeSort", measureSortingSpeed(new MergeSort(), fixedArray.clone()));
        algorithmSpeeds.put("insertionSort", measureSortingSpeed(new InsertionSort(), fixedArray.clone()));
        algorithmSpeeds.put("bubbleSort", measureSortingSpeed(new BubbleSort(), fixedArray.clone()));
        algorithmSpeeds.put("selectionSort", measureSortingSpeed(new SelectionSort(), fixedArray.clone()));

        return algorithmSpeeds;
    }

    @GetMapping("/swaps")
    public Map<String, Integer> getSwapCounts(@RequestParam(required = false) Integer arraySize) {

        // Replace the random array with a fixed array of your choosing
        int[] fixedArray = generateFixedArray();

        Map<String, Integer> swapCounts = new HashMap<>();

        swapCounts.put("mergeSort", measureSwaps(new MergeSort(), fixedArray.clone()));
        swapCounts.put("insertionSort", measureSwaps(new InsertionSort(), fixedArray.clone()));
        swapCounts.put("bubbleSort", measureSwaps(new BubbleSort(), fixedArray.clone()));
        swapCounts.put("selectionSort", measureSwaps(new SelectionSort(), fixedArray.clone()));

        return swapCounts;
    }


    // need LARGE array to show accurate speed calculations 
    private int[] generateFixedArray() {
        return new int[]{69,350,262,99,827,739,156,165,202,969,10,434,986,739,124,3,162,611,212,635,366,937,588,914,664,981,980,412,761,10,526,905,625,372,494,622,424,344,425,793,360,269,936,526,92,764,640,722,684,613,541,248,284,465,463,226,128,28,842,412,579,303,463,696,224,3,363,96,185,685,316,867,53,725,262,450,2,132,510,371,727,963,789,471,670,55,406,188,771,552,503,912,490,442,698,210,967,670,857,24,614,302,736,205,862,169,598,408,812,701,524,160,414,751,261,985,376,109,775,98,743,767,256,696,993,305,716,129,866,677,833,810,859,360,207,21,910,135,847,964,311,805,31,591,549,585,787,683,499,872,393,848,67,420,933,439,228,427,557,315,418,309,752,383,783,962,301,914,312,426,221,461,894,437,281,823,951,196,426,556,45,618,735,634,284,583,595,15,275,500,520,129,230,567,808,52,226,367,21,609,205,529,390,547,121,715,841,342,892,87,119,775,24,168,59,120,603,727,428,360,941,876,191,443,478,901,300,972,603,666,711,830,810,163,825,588,390,881,362,80,994,791,892,827,290,761,25,330,835,788,415,32,835,388,497,795,990,973,194,281,307,635,567,613,162,504,531,237,436,305,897,462,10,546,23,471,207,125,904,816,989,866,372,275,730,842,621,119,743,657,951,773,54,6,830,182,2,355,78,643,476,141,139,849,524,710,382,539,664,658,204,866,245,146,527,556,919,231,861,815,605,39,338,232,247,938,887,442,910,881,841,796,636,644,358,875,746,934,757,735,902,401,987,556,747,397,626,693,555,738,982,394,679,285,13,689,823,834,928,852,498,466,69,311,933,890,809,393,639,638,281,819,9,402,527,358,671,942,217,519,773,880,620,890,604,477,305,343,420,939,373,164,559,677,176,612,270,920,955,579,117,783,543,918,709,37,714,23,135,841,884,192,604,284,81,455,242,210,993,286,779,79,55,841,673,741,713,277,515,765,380,52,258,889,104,488,500,723,581,590,80,757,301,826,233,998,460,794,994,840,809,930,739,156,328,940,739,971,552,583,943,482,443,560,663,833,390,590,67,114,281,741,366,214,102,753,36,820,145,994,976,566,330,618,376,321,844,668,511,123,392,458,940,244,860,252,987,514,164,931,812,795,253,903,709,179,848,597,254,97,907,196,282,485,444,927,771,442,853,354,10,426,921,924,282,807,178,927,782,1000,340,321,775,881,513,707,2,522,946,558,185,533,708,453,359,511,657,5,768,445,832,922,226,637,406,349,788,134,184,644,126,994,976,721,342,726,404,467,880,398,403,830,597,721,951,974,306,500,497,948,810,29,637,87,496,371,152,249,716,596,44,158,919,249,606,110,28,216,936,564,686,103,325,787,609,228,660,812,154,421,129,236,671,70,532,158,410,885,537,289,723,271,884,380,301,296,834,969,929,661,907,445,895,576,856,285,196,107,602,814,444,743,67,777,144,893,698,130,327,801,51,57,766,597,953,737,750,541,345,331,636,711,484,120,559,636,685,448,981,632,537,694,581,696,664,789,239,417,447,671,74,711,747,452,70,192,852,167,65,896,334,745,64,492,339,873,838,414,149,145,784,554,625,473,671,894,975,374,241,634,770,975,256,462,268,5,304,51,57,404,849,717,977,654,643,921,809,33,314,280,431,809,230,253,537,466,344,277,966,191,279,771,893,21,19,461,344,575,499,626,63,951,806,74,331,775,854,373,356,896,828,914,749,466,78,47,793,125,112,75,574,811,974,221,920,255,587,238,47,247,123,225,917,302,386,743,87,252,595,509,649,966,388,837,722,903,896,514,999,559,219,365,328,366,443,592,429,372,96,847,605,319,254,29,187,762,716,603,585,514,446,616,295,630,573,884,626,379,859,584,691,497,883,45,654,582,618,20,66,616,718,647,931,558,882,594,276,216,765,63,887,135,405,154,113,30,834,404,708,2,736,222,65,772,881,961,316,929,889,727,905,934,13,278,183,172,249,481,900,534,959,498,540,435,723,229,612,169,303,250,985,328,183,82,57,892,769,133,326,600,205,229,569,58,811,676,467,67,716,560,204,788,632,169,639,842,935,233,579,48,728,624,630,809,299,489,209,365,433,226,16,617,640,549,359,918,556,75,699,997,807,837,873,703,393,512,279,501,726,688,490,664,556,127,599,244,958,647,828,726,785,457,637,606,80,808,832,645,614,988,188,129,389,832,102,652,408,158,802,780,254,753,652,115,595,809,11,955,229,983,115,688,830,53,406,876,402,690,329,648};     
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
