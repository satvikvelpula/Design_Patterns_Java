package Strategy;

import java.util.Random;

public class Main {

    public static void main(String[] args) {

        // Dataset sizes
        int smallSize = 30;
        int largeSize = 100_000;

        // Generate the original datasets
        int[] smallData = generateRandomArray(smallSize);
        int[] largeData = generateRandomArray(largeSize);

        System.out.println("===== SMALL DATASET =====");
        testAlgorithms(smallData);

        System.out.println();
        System.out.println("===== LARGE DATASET =====");
        testAlgorithms(largeData);
    }

    private static int[] generateRandomArray(int size) {

        Random random = new Random();

        int[] arr = new int[size];

        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(1_000_000);
        }

        return arr;
    }

    private static void testAlgorithms(int[] originalData) {

        // -------------------------------------------------
        // Selection Sort
        // -------------------------------------------------

        int[] selectionData = originalData.clone();

        AlgorithmSetter selectionContext =
                new AlgorithmSetter(selectionData);

        SelectionSort selectionSort =
                new SelectionSort(selectionContext);

        selectionContext.setStrategy(selectionSort);

        long start = System.nanoTime();

        selectionContext.sort();

        long end = System.nanoTime();

        System.out.println(
                "Selection Sort: " +
                (end - start) +
                " ns"
        );


        // -------------------------------------------------
        // QuickSort
        // -------------------------------------------------

        int[] quickData = originalData.clone();

        AlgorithmSetter quickContext =
                new AlgorithmSetter(quickData);

        Quicksort quickSort =
                new Quicksort(
                        quickContext,
                        0,
                        quickData.length - 1
                );

        quickContext.setStrategy(quickSort);

        start = System.nanoTime();

        quickContext.sort();

        end = System.nanoTime();

        System.out.println(
                "QuickSort: " +
                (end - start) +
                " ns"
        );


        // -------------------------------------------------
        // Third Strategy
        // -------------------------------------------------

        int[] insertionData = originalData.clone(); 
        AlgorithmSetter insertionContext = new AlgorithmSetter(insertionData); 
        InsertionSort insertionSort = new InsertionSort(insertionContext); 
        insertionContext.setStrategy(insertionSort); 
        start = System.nanoTime(); 
        insertionContext.sort(); 
        end = System.nanoTime(); 
        System.out.println( "Insertion Sort: " + (end - start) + " ns" );
    }
}