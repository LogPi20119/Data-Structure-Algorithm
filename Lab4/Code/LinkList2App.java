public class LinkList2App {

    // Node class for singly linked list
    private static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    // Singly linked list implementation
    private static class LinkList {
        private Node head;

        // Insert at the front
        public void insertFirst(int data) {
            Node newNode = new Node(data);
            newNode.next = head;
            head = newNode;
        }

        public boolean insertAfter(int key, int data) {
            Node current = head;
            while (current != null && current.data != key) {
                current = current.next;
            }
            if (current == null) {
                return false; // key not found
            }
            Node newNode = new Node(data);
            newNode.next = current.next;
            current.next = newNode;
            return true;
        }

        // Utility: display list contents
        public void displayList() {
            Node current = head;
            System.out.print("List (head -> tail): ");
            while (current != null) {
                System.out.print(current.data);
                if (current.next != null) System.out.print(" -> ");
                current = current.next;
            }
            System.out.println();
        }

        // Optional: find method (returns true if found)
        public boolean contains(int key) {
            Node current = head;
            while (current != null) {
                if (current.data == key) return true;
                current = current.next;
            }
            return false;
        }
    }

    // Demo main
    public static void main(String[] args) {
        LinkList list = new LinkList();

        // Build list: 30 -> 20 -> 10
        list.insertFirst(10);
        list.insertFirst(20);
        list.insertFirst(30);

        System.out.println("Before insertAfter:");
        list.displayList();

        // Insert 25 after 20
        boolean ok = list.insertAfter(20, 25);
        System.out.println("insertAfter(20, 25) returned: " + ok);
        list.displayList();

        // Try inserting after a non-existent key
        boolean ok2 = list.insertAfter(99, 77);
        System.out.println("insertAfter(99, 77) returned: " + ok2);
        list.displayList();
    }
}
