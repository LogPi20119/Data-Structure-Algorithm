// LinkStackApp.java
import java.util.Stack;

public class LinkStackApp {

    // Node class for singly linked list
    private static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    // Singly linked list with reverseUsingStack method
    private static class LinkList {
        private Node head;

        // Insert at the front
        public void insertFirst(int data) {
            Node newNode = new Node(data);
            newNode.next = head;
            head = newNode;
        }

        // Insert at the end (helper for building demo lists)
        public void insertLast(int data) {
            Node newNode = new Node(data);
            if (head == null) {
                head = newNode;
                return;
            }
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }

        public void reverseUsingStack() {
            if (head == null || head.next == null) return; // empty or single node

            Stack<Node> stack = new Stack<>();
            Node current = head;
            while (current != null) {
                stack.push(current);
                current = current.next;
            }

            // Pop the first node to become new head
            head = stack.pop();
            Node newCurrent = head;

            // Pop remaining nodes and relink
            while (!stack.isEmpty()) {
                Node node = stack.pop();
                newCurrent.next = node;
                newCurrent = node;
            }

            // Terminate the list
            newCurrent.next = null;
        }

        // Utility: display list contents
        public void displayList() {
            Node current = head;
            System.out.print("List (head -> tail): ");
            if (current == null) {
                System.out.println("empty");
                return;
            }
            while (current != null) {
                System.out.print(current.data);
                if (current.next != null) System.out.print(" -> ");
                current = current.next;
            }
            System.out.println();
        }
    }

    // Demo main
    public static void main(String[] args) {
        LinkList list = new LinkList();

        // Build list: 10 -> 20 -> 30 -> 40
        list.insertLast(10);
        list.insertLast(20);
        list.insertLast(30);
        list.insertLast(40);

        System.out.println("Before reverseUsingStack:");
        list.displayList();

        // Reverse using stack
        list.reverseUsingStack();

        System.out.println("After reverseUsingStack:");
        list.displayList();

        // Reverse again to show idempotence
        list.reverseUsingStack();
        System.out.println("After reversing again:");
        list.displayList();
    }
}
