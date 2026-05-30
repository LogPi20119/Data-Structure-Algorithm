public class ArrayToNumber {
    public static int convertArrayToNumber(int[] digits) {
        int number = 0;
        for (int digit : digits) {
            number = number * 10 + digit;
        }
        return number;
    }

    public static void main(String[] args) {
        int[] digits = {2, 0, 1, 8};
        System.out.println(convertArrayToNumber(digits));
    }
}
