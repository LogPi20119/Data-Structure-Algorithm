//  Person class
class Person {
    private String name;
    private int    age;

    public Person(String name, int age) {
        this.name = name;
        this.age  = age;
    }

    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return String.format("%-15s (age %2d)", name, age);
    }
}

//  Generic Stack backed by an array
class Stack<T> {
    private Object[] data;
    private int top;

    public Stack(int capacity) {
        data = new Object[capacity];
        top  = -1;
    }

    public void push(T item) {
        if (isFull()) throw new RuntimeException("Stack overflow");
        data[++top] = item;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) throw new RuntimeException("Stack underflow");
        return (T) data[top--];
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) throw new RuntimeException("Stack is empty");
        return (T) data[top];
    }

    public boolean isEmpty() { return top == -1; }
    public boolean isFull()  { return top == data.length - 1; }
    public int     size()    { return top + 1; }
}

//  Main application
public class ReverseApp {

    /** Print a Person array with a heading. */
    static void printList(String title, Person[] list) {
        System.out.println("\n" + title);
        System.out.println("-".repeat(35));
        for (int i = 0; i < list.length; i++) {
            System.out.printf("  [%d] %s%n", i + 1, list[i]);
        }
    }

    /** Reverse a Person array using a stack; return new reversed array. */
    static Person[] reverse(Person[] list) {
        Stack<Person> stack = new Stack<>(list.length);

        // Push every person onto the stack
        for (Person p : list) stack.push(p);

        // Pop them off in LIFO order → reversed
        Person[] reversed = new Person[list.length];
        for (int i = 0; i < reversed.length; i++) {
            reversed[i] = stack.pop();
        }
        return reversed;
    }

    public static void main(String[] args) {

        Person[] people = {
            new Person("Alice",   30),
            new Person("Bob",     25),
            new Person("Charlie", 40),
            new Person("Diana",   22),
            new Person("Edward",  35),
            new Person("Fiona",   28),
        };

        System.out.println("=".repeat(40));
        System.out.println("   REVERSE A LIST OF PERSONS VIA STACK");
        System.out.println("=".repeat(40));

        printList("Original list:", people);

        Person[] reversed = reverse(people);

        printList("Reversed list:", reversed);

        System.out.println("\n" + "=".repeat(40));
        System.out.println("  Stack trace (push → pop):");
        System.out.println("=".repeat(40));
        Stack<Person> demo = new Stack<>(people.length);
        System.out.println("  Pushing onto stack:");
        for (Person p : people) {
            demo.push(p);
            System.out.println("    PUSH → " + p);
        }
        System.out.println("\n  Popping from stack:");
        while (!demo.isEmpty()) {
            System.out.println("    POP  ← " + demo.pop());
        }
    }
}