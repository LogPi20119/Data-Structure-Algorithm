package Lab6;
import java.util.Random;

public class PartitionApp {

    static int comparisons = 0;
    static int swaps = 0;

    // PARTITION
    static int partition(int[] arr, int left, int right, int pivotIndex) {
        int pivot = arr[pivotIndex];

        // Swap pivot to end
        swap(arr, pivotIndex, right);

        int storeIndex = left;
        for (int i = left; i < right; i++) {
            comparisons++;
            if (arr[i] <= pivot) {
                swap(arr, i, storeIndex);
                storeIndex++;
            }
        }
        // Swap pivot back to correct position
        swap(arr, storeIndex, right);
        return storeIndex;
    }

    static void swap(int[] arr, int i, int j) {
        if (i != j) {
            swaps++;
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }

    // PIVOT STRATEGIES
    static int pivotBeginning(int left, int right) {
        return left;
    }

    static int pivotEnd(int left, int right) {
        return right;
    }

    static int pivotMiddle(int left, int right) {
        return (left + right) / 2;
    }

    static int pivotRandom(int left, int right) {
        return left + new Random().nextInt(right - left + 1);
    }

    // RESET COUNTERS
    static void resetCounters() {
        comparisons = 0;
        swaps = 0;
    }

    // RUN ONE PARTITION
    static int runPartition(int[] arr, String pivotStrategy) {
        resetCounters();
        int left = 0, right = arr.length - 1;
        int pivotIndex;

        switch (pivotStrategy) {
            case "beginning": pivotIndex = pivotBeginning(left, right); break;
            case "end":       pivotIndex = pivotEnd(left, right);       break;
            case "middle":    pivotIndex = pivotMiddle(left, right);    break;
            case "random":    pivotIndex = pivotRandom(left, right);    break;
            default:          pivotIndex = pivotEnd(left, right);
        }

        return partition(arr, left, right, pivotIndex);
    }

    // AVERAGE OVER 100 RUNS
    static void averageOver100Runs(int size, String pivotStrategy) {
        long totalComparisons = 0;
        long totalSwaps = 0;

        for (int run = 0; run < 100; run++) {
            // Generate random array each run
            int[] arr = new int[size];
            Random rand = new Random();
            for (int i = 0; i < size; i++)
                arr[i] = rand.nextInt(1000);

            runPartition(arr, pivotStrategy);
            totalComparisons += comparisons;
            totalSwaps += swaps;
        }

        System.out.printf("  Strategy=%-12s | Avg comparisons=%.2f | Avg swaps=%.2f%n",
                pivotStrategy,
                totalComparisons / 100.0,
                totalSwaps / 100.0);
    }

    // MAIN
    public static void main(String[] args) {
        // Part 1: Single run, show partition index + counters
        System.out.println("===== PART 1: Single partition run =====");
        int[] arr = {5, 3, 8, 1, 9, 2, 7, 4, 6};
        System.out.println("Original array: " + java.util.Arrays.toString(arr));

        String[] strategies = {"beginning", "middle", "end", "random"};
        for (String s : strategies) {
            int[] copy = arr.clone();
            int partIdx = runPartition(copy, s);
            System.out.printf("Pivot=%-10s | Partition index=%d | Comparisons=%d | Swaps=%d%n",
                    s, partIdx, comparisons, swaps);
            System.out.println("  Result: " + java.util.Arrays.toString(copy));
        }

        // Part 2: Investigate relationship
        System.out.println("\n===== PART 2: Relationship - partition index vs counters =====");
        System.out.println("(Fixed pivot = beginning, vary array size)");
        System.out.printf("%-10s | %-18s | %-10s | %-10s%n",
                "Size", "Partition index", "Comparisons", "Swaps");
        int[] sizes = {5, 10, 20, 50, 100};
        Random rand = new Random();
        for (int size : sizes) {
            int[] a = new int[size];
            for (int i = 0; i < size; i++) a[i] = rand.nextInt(1000);
            int idx = runPartition(a, "beginning");
            System.out.printf("%-10d | %-18d | %-10d | %-10d%n",
                    size, idx, comparisons, swaps);
        }

        // Part 3: Average over 100 runs
        System.out.println("\n===== PART 3: Average over 100 runs (array size = 50) =====");
        for (String s : strategies) {
            averageOver100Runs(50, s);
        }
    }
}
