package Lab6;

public class MergeSort {
    static long comparisons = 0, copies = 0;

    public static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        // Copy to temp arrays
        for (int i = 0; i < n1; i++) { L[i] = arr[left + i]; copies++; }
        for (int j = 0; j < n2; j++) { R[j] = arr[mid + 1 + j]; copies++; }

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            comparisons++;
            if (L[i] <= R[j]) { arr[k++] = L[i++]; }
            else               { arr[k++] = R[j++]; }
            copies++;
        }
        while (i < n1) { arr[k++] = L[i++]; copies++; }
        while (j < n2) { arr[k++] = R[j++]; copies++; }
    }

    public static void main(String[] args) {
        int[] sizes = {10000, 15000, 20000, 25000, 30000, 35000, 40000, 45000, 50000};

        System.out.printf("%-10s | %-15s | %-15s%n", "Size", "Comparisons", "Copies");
        System.out.println("-".repeat(45));

        for (int size : sizes) {
            comparisons = 0; copies = 0;
            int[] arr = generateRandom(size);
            mergeSort(arr, 0, arr.length - 1);
            System.out.printf("%-10d | %-15d | %-15d%n", size, comparisons, copies);
        }
    }

    private static int[] generateRandom(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++)
            arr[i] = (int)(Math.random() * 100000);
        return arr;
    }
}
