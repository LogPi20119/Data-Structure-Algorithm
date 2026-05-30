import java.util.Random;

public class SortComparisonApp {

    // BUBBLE SORT
    static long[] bubbleSort(int[] original) {
        int[] arr = original.clone();
        int n = arr.length;
        long comparisons = 0, swaps = 0, copies = 0;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                comparisons++;
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swaps++;
                    copies += 3;
                }
            }
        }
        return new long[]{comparisons, copies, swaps};
    }

    // SELECTION SORT 
    static long[] selectionSort(int[] original) {
        int[] arr = original.clone();
        int n = arr.length;
        long comparisons = 0, swaps = 0, copies = 0;

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                comparisons++;
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
                swaps++;
                copies += 3;
            }
        }
        return new long[]{comparisons, copies, swaps};
    }

    // INSERTION SORT 
    static long[] insertionSort(int[] original) {
        int[] arr = original.clone();
        int n = arr.length;
        long comparisons = 0, swaps = 0, copies = 0;

        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;
            copies++;

            while (j >= 0 && arr[j] > key) {
                comparisons++;
                arr[j + 1] = arr[j];
                j--;
                copies++; 
            }
            if (j >= 0) comparisons++; 
            arr[j + 1] = key;
            copies++; 
            if (j + 1 != i) swaps++; 
        }
        return new long[]{comparisons, copies, swaps};
    }

    //  MAIN 
        public static void main(String[] args) {
        int[] sizes = {10000, 15000, 20000, 25000, 30000, 35000, 40000, 45000, 50000};
        Random rand = new Random();

        System.out.println("=== COPIES / COMPARISONS / SWAPS ===");
        System.out.printf("%-10s | %-30s | %-30s | %-30s%n",
                "Size", "Bubble Sort", "Selection Sort", "Insertion Sort");
        System.out.println("-".repeat(107));
        System.out.printf("%-10s | %-10s %-10s %-10s | %-10s %-10s %-10s | %-10s %-10s %-10s%n",
                "", "Comps", "Copies", "Swaps", "Comps", "Copies", "Swaps", "Comps", "Copies", "Swaps");
        System.out.println("-".repeat(107));

        for (int size : sizes) {
            int[] arr = new int[size];
            for (int i = 0; i < size; i++) {
                arr[i] = rand.nextInt(100000);
            }

            long[] bubble    = bubbleSort(arr);
            long[] selection = selectionSort(arr);
            long[] insertion = insertionSort(arr);

            System.out.printf("%-10d | %-10d %-10d %-10d | %-10d %-10d %-10d | %-10d %-10d %-10d%n",
                    size,
                    bubble[0],    bubble[1],    bubble[2],
                    selection[0], selection[1], selection[2],
                    insertion[0], insertion[1], insertion[2]);
        }

        System.out.println("\n=== TREND ANALYSIS ===");
        System.out.println("Bubble Sort    : Comparisons & copies grow rapidly ~ O(n^2). Swaps can be very high.");
        System.out.println("Selection Sort : Comparisons ~ O(n^2), but swaps always <= n-1 (most efficient swaps).");
        System.out.println("Insertion Sort : Best case O(n), average O(n^2). Fewest comparisons on average.");
    }
}
