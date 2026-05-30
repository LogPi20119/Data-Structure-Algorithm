public class SelectionSortMax {
    public static void selectionSortByMax(int[] arr) {
        int n = arr.length;
        
        for (int i = n - 1; i > 0; i--) {
            
            int maxIndex = 0;
            
            for (int j = 1; j <= i; j++) {
                if (arr[j] > arr[maxIndex]) {
                    maxIndex = j;
                }
            }
            
            int temp = arr[i];
            arr[i] = arr[maxIndex];
            arr[maxIndex] = temp;
        }
    }

    public static void main(String[] args) {
        int[] arr = {64, 25, 12, 22, 11};
        System.out.println("Original array:");
        for (int num : arr) System.out.print(num + " ");
        
        selectionSortByMax(arr);
        
        System.out.println("\nArray after sorting:");
        for (int num : arr) System.out.print(num + " ");
    }
}
