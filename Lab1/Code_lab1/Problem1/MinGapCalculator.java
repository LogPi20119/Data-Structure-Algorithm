public class MinGapCalculator {
    public static int minGap(int[] array, int n) {
        if (n < 2) {
            return 0;
        }

        int minGap = Integer.MAX_VALUE; 

        for (int i = 0; i < n - 1; i++) {
            int gap = array[i + 1] - array[i]; 
            if (gap < minGap) {
                minGap = gap; 
            }
        }

        return minGap;
    }

    public static void main(String[] args) {
        int[] array = {1, 3, 6, 7, 12};
        System.out.println("Min gap = " + minGap(array, array.length)); 
        // Output: 1
    }
}

