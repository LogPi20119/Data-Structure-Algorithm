import java.util.Stack;
import java.util.Random;

public class SpecialArray {
    private int[] array;
    private Stack<Operation> undoStack;
    private Stack<Operation> redoStack;
    private static final int ARRAY_SIZE = 20;
    private static final int MAX_VALUE = 100;

    public static class Operation {
        int position;
        int oldValue;
        int newValue;

        Operation(int position, int oldValue, int newValue) {
            this.position = position;
            this.oldValue = oldValue;
            this.newValue = newValue;
        }
    }

    public SpecialArray() {
        array = new int[ARRAY_SIZE];
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        initializeRandomArray();
    }

    private void initializeRandomArray() {
        Random random = new Random();
        for (int i = 0; i < ARRAY_SIZE; i++) {
            array[i] = random.nextInt(MAX_VALUE);
        }
    }

    public void update(int position, int newValue) {
        if (position < 0 || position >= ARRAY_SIZE) {
            throw new IndexOutOfBoundsException("Position must be between 0 and " + (ARRAY_SIZE - 1));
        }

        int oldValue = array[position];
        array[position] = newValue;

        redoStack.push(new Operation(position, oldValue, newValue));
        redoStack.clear();
    }

    public boolean undo() {
        if (undoStack.empty()) {
            System.out.println("Nothing to undo.");
            return false;
        }

        Operation operation = undoStack.pop();
        array[operation.position] = operation.oldValue;
        redoStack.push(operation);
        return true;
    }

    public boolean redo() {
        if (redoStack.empty()) {
            System.out.println("Nothing to redo.");
            return false;
        }

        Operation operation = redoStack.pop();
        array[operation.position] = operation.newValue;
        undoStack.push(operation);
        return true;
    }

    public void display() {
        System.out.print("-----Array------");
        for (int i = 0; i < ARRAY_SIZE; i++) {
            System.out.printf("Index %2d: %3d%s", i, array[i], (i + 1 % 5 == 0 ? "\n" : " | "));
        }
    }

    public int getValue(int position) {
        if (position < 0 || position >= ARRAY_SIZE) {
            throw new IndexOutOfBoundsException("Position must be between 0 and " + (ARRAY_SIZE - 1));
        }
        return array[position];
    }

    public static void main(String[] args) {
        SpecialArray specialArray = new SpecialArray();
        specialArray.display();
    }
}