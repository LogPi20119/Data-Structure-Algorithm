package Lab6;

public class QuickSort3 {
    static long comparisons = 0, swaps = 0, copies = 0;

    public static void quickSort(int[] arr, int left, int right) {
        if (left < right) {
            int pivotIndex = partition(arr, left, right);
            quickSort(arr, left, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, right);
        }
    }

    private static int partition(int[] arr, int left, int right) {
        // Median-of-three pivot (quickSort3 variant)
        int mid = (left + right) / 2;
        if (arr[mid] < arr[left]) {
            swap(arr, left, mid);
        }
        if (arr[right] < arr[left]) {
            swap(arr, left, right);
        }
        if (arr[mid] < arr[right]) {
            swap(arr, mid, right);
        }
        // arr[right] is now the median → use as pivot
        int pivot = arr[right];

        int i = left - 1;
        for (int j = left; j < right; j++) {
            comparisons++;
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, right);
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        swaps++;
        copies += 3;
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] sizes = { 10000, 15000, 20000, 25000, 30000, 35000, 40000, 45000, 50000 };

        System.out.printf("%-10s | %-15s | %-15s | %-15s%n", "Size", "Comparisons", "Copies", "Swaps");
        System.out.println("-".repeat(55));

        for (int size : sizes) {
            comparisons = 0;
            swaps = 0;
            int[] arr = generateRandom(size);
            quickSort(arr, 0, arr.length - 1);
            System.out.printf("%-10d | %-15d | %-15d | %-15d%n", size, comparisons, copies, swaps);
        }
    }

    private static int[] generateRandom(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++)
            arr[i] = (int) (Math.random() * 100000);
        return arr;
    }
}
