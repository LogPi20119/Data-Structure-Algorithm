import java.util.Stack;

public class SimpleStackApp {

    // 1. Convert a decimal number to octal using a stack
    public static String decimalToOctal(int decimal) {
        if (decimal == 0) return "0";   // Special case for zero
        
        //Check negative
        boolean isNegative = decimal < 0;
        decimal = Math.abs(decimal);

        // Use a stack to store octal digits
        Stack<Integer> stack = new Stack<>();
        while (decimal > 0) {
            stack.push(decimal % 8);
            decimal /= 8;
        }

        // Pop from stack to build octal string
        StringBuilder octal = new StringBuilder(isNegative ? "-" : "");
        while (!stack.isEmpty()) {
            octal.append(stack.pop());
        }
        return octal.toString();
    }

    // 2. Concatenate two stacks (stack2 on top of stack1)
    public static <T> Stack<T> concatenate(Stack<T> stack1, Stack<T> stack2) {
        Stack<T> result = new Stack<>();
        result.addAll(stack1);   // bottom: all of stack1
        result.addAll(stack2);   // top:    all of stack2
        return result;
    }

    // 3. Check if two stacks are identical
    public static <T> boolean areIdentical(Stack<T> stack1, Stack<T> stack2) {
        if (stack1.size() != stack2.size()) return false;

        // Compare element-by-element from bottom to top
        for (int i = 0; i < stack1.size(); i++) {
            if (!stack1.get(i).equals(stack2.get(i))) return false;
        }
        return true;
    }

    // Helper: pretty-print a stack (bottom → top)
    public static <T> String stackToString(Stack<T> stack) {
        if (stack.isEmpty()) return "[]";
        StringBuilder sb = new StringBuilder("[bottom] ");
        for (T item : stack) {  // Iterates from bottom to top
            sb.append(item).append(" ");
        }
        sb.append("[top]");
        return sb.toString();
    }

    // Main
    public static void main(String[] args) {

        System.out.println("  1. DECIMAL to OCTAL CONVERSION");

        int[] testNumbers = {0, 8, 64, 255, 1024, -57};
        for (int n : testNumbers) {
            System.out.printf("  Decimal: %6d  to  Octal: %s%n", n, decimalToOctal(n));
        }

        System.out.println();
        System.out.println("  2. STACK CONCATENATION");

        Stack<Integer> stackA = new Stack<>();
        stackA.push(1);
        stackA.push(2);
        stackA.push(3);

        Stack<Integer> stackB = new Stack<>();
        stackB.push(4);
        stackB.push(5);
        stackB.push(6);

        System.out.println("  Stack A : " + stackToString(stackA));
        System.out.println("  Stack B : " + stackToString(stackB));

        Stack<Integer> concatenated = concatenate(stackA, stackB);
        System.out.println("  A + B   : " + stackToString(concatenated));

        System.out.println();
        System.out.println("  3. STACK IDENTITY CHECK");

        // Case 1: identical stacks
        Stack<Integer> s1 = new Stack<>();
        s1.push(10); s1.push(20); s1.push(30);

        Stack<Integer> s2 = new Stack<>();
        s2.push(10); s2.push(20); s2.push(30);

        System.out.println("  Stack 1      : " + stackToString(s1));
        System.out.println("  Stack 2      : " + stackToString(s2));
        System.out.println("  Identical?   : " + areIdentical(s1, s2));

        System.out.println();

        // Case 2: different stacks
        Stack<Integer> s3 = new Stack<>();
        s3.push(10); s3.push(20); s3.push(99);

        System.out.println("  Stack 1      : " + stackToString(s1));
        System.out.println("  Stack 3      : " + stackToString(s3));
        System.out.println("  Identical?   : " + areIdentical(s1, s3));

        System.out.println();

        // Case 3: different sizes
        Stack<Integer> s4 = new Stack<>();
        s4.push(10); s4.push(20);

        System.out.println("  Stack 1      : " + stackToString(s1));
        System.out.println("  Stack 4      : " + stackToString(s4));
        System.out.println("  Identical?   : " + areIdentical(s1, s4));
    }
}