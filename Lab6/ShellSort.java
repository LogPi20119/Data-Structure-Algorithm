package Lab6;

public class ShellSort {
    static long comparisons = 0, swaps = 0, copies = 0;

    public static void shellSort(int[] arr) {
        int n = arr.length;
        int gap = n / 2;

        while (gap > 0) {
            for (int i = gap; i < n; i++) {
                int temp = arr[i];
                copies++;
                int j = i;

                while (j >= gap) {
                    comparisons++;
                    if (arr[j - gap] > temp) {
                        arr[j] = arr[j - gap];
                        copies++;
                        swaps++;
                        j -= gap;
                    } else
                        break;
                }
                arr[j] = temp;
                copies++;
            }
            gap /= 2;
        }
    }

    public static void main(String[] args) {
        int[] sizes = { 10000, 15000, 20000, 25000, 30000, 35000, 40000, 45000, 50000 };

        System.out.printf("%-10s | %-15s | %-15s | %-15s%n", "Size", "Comparisons", "Copies", "Swaps");
        System.out.println("-".repeat(55));

        for (int size : sizes) {
            comparisons = 0;
            swaps = 0;
            copies = 0;
            int[] arr = generateRandom(size);
            shellSort(arr);
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
