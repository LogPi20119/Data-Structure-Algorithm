import java.util.Scanner;

public class BubbleSortApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of elements n: ");
        int n = sc.nextInt();

        int[] arr = new int[n];
        System.out.print("Enter " + n + " elements: ");
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        int totalComparisons = 0;
        int totalSwaps = 0;

        System.out.print("Initial array: ");
        printArray(arr);

        for (int i = 0; i < n - 1; i++) {
            System.out.println("\n--- Outer loop i = " + i + " ---");
            int swapsInner = 0;
            int comparisonsInner = 0;

            for (int j = 0; j < n - i - 1; j++) {
                comparisonsInner++;
                totalComparisons++;

                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapsInner++;
                    totalSwaps++;
                }

                System.out.print("  Compare [" + j + "] & [" + (j+1) + "]: ");
                printArray(arr);
            }

            System.out.println("  >> Swaps this inner loop: " + swapsInner);
            System.out.println("  >> Comparisons this inner loop: " + comparisonsInner);
            System.out.print("  >> Array after outer loop " + i + ": ");
            printArray(arr);
        }

        System.out.println("\n=== RESULT ===");
        System.out.print("Sorted array: ");
        printArray(arr);
        System.out.println("Total swaps: " + totalSwaps);
        System.out.println("Total comparisons: " + totalComparisons);
        System.out.println("Theoretical n*(n-1)/2 = " + (n * (n - 1) / 2));
        System.out.println("Algorithm complexity: O(n^2)");

        sc.close();
    }

    static void printArray(int[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) System.out.print(", ");
        }
        System.out.println("]");
    }
}