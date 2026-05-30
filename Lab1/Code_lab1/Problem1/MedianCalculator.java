import java.util.Arrays;

public class MedianCalculator {
    public static double findMedian(int[] numbers) {
        //Sort the array in ascending order
        Arrays.sort(numbers);

        int n = numbers.length;

        if (n % 2 == 1) {
            // If the number of elements is odd, return the middle element
            return numbers[n / 2];
        } else {
            // If the number of elements is even, return the average of the two middle elements
            return (numbers[(n / 2) - 1] + numbers[n / 2]) / 2.0;
        }
    }

    public static void main(String[] args) {
        int[] list1 = {7, 1, 3, 5};
        int[] list2 = {2, 0, 1, 8, 9};

        System.out.println("Median list1 = " + findMedian(list1)); // Output: 4.0
        System.out.println("Median list2 = " + findMedian(list2)); // Output: 2.0
    }
}
