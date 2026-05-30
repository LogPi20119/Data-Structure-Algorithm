import java.util.Scanner;

public class InsertSortApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of elements n: ");
        int n = sc.nextInt();

        int[] arr = new int[n];
        System.out.print("Enter " + n + " elements: ");
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        int totalPasses = 0;

        System.out.print("Initial array: ");
        printArray(arr);

        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;
            int passesInner = 0;

            System.out.println("\n--- Outer loop i = " + i + " | key = " + key + " ---");

            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
                passesInner++;
                totalPasses++;
            }

            arr[j + 1] = key;

            if (passesInner == 0) {
                System.out.println("  key = " + key + " is already in correct position, no shift needed.");
            } else {
                System.out.println("  Shifted " + passesInner + " element(s) to the right, inserted key = " 
                                   + key + " at index " + (j + 1));
            }

            System.out.println("  >> Passes of inner loop: " + passesInner);
            System.out.print("  >> Array after outer loop " + i + ": ");
            printArray(arr);
        }

        System.out.println("\n=== RESULT ===");
        System.out.print("Sorted array: ");
        printArray(arr);
        System.out.println("Total passes of inner loop: " + totalPasses);
        System.out.println("Theoretical n*(n-1)/4 = " + (n * (n - 1) / 4));
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
