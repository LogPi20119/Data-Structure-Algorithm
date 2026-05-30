import java.util.Scanner;

public class SelectSortApp {
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
            int minIndex = i;
            int comparisonsInner = 0;

            for (int j = i + 1; j < n; j++) {
                comparisonsInner++;
                totalComparisons++;

                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                System.out.println("\n--- Outer loop i = " + i + " ---");
                System.out.println("  Swap needed: arr[" + i + "] = " + arr[i] +
                                   " <-> arr[" + minIndex + "] = " + arr[minIndex]);
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
                totalSwaps++;
            } else {
                System.out.println("\n--- Outer loop i = " + i + " ---");
                System.out.println("  No swap needed: arr[" + i + "] = " + arr[i] +
                                   " is already the minimum.");
            }

            System.out.println("  >> Comparisons this inner loop: " + comparisonsInner);
            System.out.print("  >> Array after inner loop: ");
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





