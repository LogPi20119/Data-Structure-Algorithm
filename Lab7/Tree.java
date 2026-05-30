// Tree.java - Binary Search Tree Implementation
class Node {
    int data;
    Node leftChild;
    Node rightChild;

    Node (int data) {
        this.data = data;
        this.leftChild = null;
        this.rightChild = null;
    }

    public void displayNode() {
        System.out.print("{" + data + "} ");
    }
}

public class Tree {
    private Node root;

    // Comparison counters for TreeApp analysis
    private int findComparisons;
    private int insertComparisons;
    private int deleteComparisons;

    // Constructor
    public Tree() {
        root = null;
        findComparisons = 0;
        insertComparisons = 0;
        deleteComparisons = 0;
    }

    // Counter getters & reset
    public int getFindComparisons() { return findComparisons; }
    public int getInsertComparisons() { return insertComparisons; }
    public int getDeleteComparisons() { return deleteComparisons; }

    public void resetCounters() {
        findComparisons = 0;
        insertComparisons = 0;
        deleteComparisons = 0;
    }

    // Find(): Search for a node by key
    public Node find(int key) {
        Node current = root;
        while (current != null) {
            if (key < current.data)
                current = current.leftChild;
            else if (key > current.data)
                current = current.rightChild;
            else
                return current;
        }
        return null;
    }

    // Insert(): Insert a new node
    public void insert(int data) {
        Node newNode = new Node(data);
        if (root == null) {
            root = newNode;
            return;
        }
        Node current = root;
        Node parent;
        while (true) {
            parent = current;
            if (data < current.data) {
                current = current.leftChild;
                if (current == null) {
                    parent.leftChild = newNode;
                    return;
                }
            } else {
                current = current.rightChild;
                if (current == null) {
                    parent.rightChild = newNode;
                    return;
                }
            }
        }
    }

    // delete(): Delete a node by key
    public boolean delete(int key) {
        Node current = root;
        Node parent = root;
        boolean isLeftChild = true;

        while (current.data != key) {
            parent = current;
            if (key < current.data) {
                isLeftChild = true;
                current = current.leftChild;
            } else {
                isLeftChild = false;
                current = current.rightChild;
            }
            if (current == null)
                return false;
        }

        // Case 1: No children (leaf)
        if (current.leftChild == null && current.rightChild == null) {
            if (current == root) {
                root = null;
            } else if (isLeftChild == true) {
                parent.leftChild = null;
            } else {
                parent.rightChild = null;
            }
        }

        // Case 2: No right children
        else if (current.rightChild == null) {
            if (current == root) {
                root = current.leftChild;
            } else if (isLeftChild) {
                parent.leftChild = current.leftChild;
            } else {
                parent.rightChild = current.leftChild;
            }
        }

        // Case 3: No left children
        else if (current.leftChild == null) {
            if (current == root) {
                root = current.rightChild;
            } else if (isLeftChild) {
                parent.leftChild = current.rightChild;
            } else {
                parent.rightChild = current.rightChild;
            }
        }

        // Case 4: Two children - replace with in-order successor
        else {
            Node successor = getSuccessor(current);
            if (current == root)
                root = successor;
            else if (isLeftChild) 
                parent.leftChild = successor;
            else
                parent.rightChild = successor;
            successor.leftChild = current.leftChild;
        }
        return true;
    }

    // Helper: find in-order successor
    private Node getSuccessor(Node delNode) {
        Node successorParent = delNode;
        Node successor = delNode;
        Node current = delNode.rightChild;

        while (current != null) {
            successorParent = successor;
            successor = current;
            current = current.leftChild;
        }

        if (successor != delNode.rightChild) {
            successorParent.leftChild = successor.rightChild;
            successor.rightChild = delNode.rightChild;
        }
        return successor;
    }

    // clear(): Remove all nodes - TreeApp requirement
    public void clear() {
        root = null;
        resetCounters();
    }

    // findMin(): Find the minimum value node
    public Node findMin() {
        if (root == null) return null;
        Node current = root;
        while (current.leftChild != null)
            current = current.leftChild;
        return current;
    }
    // findMax(): Find the maximum value node
    public Node findMax() {
        if (root == null) return null;
        Node current = root;
        while (current.rightChild != null)
            current = current.rightChild;
        return current;
    }

    // saveToArray(): Save all elements via chosen traversal
    // traversalType: "inorder" | "preorder" | "postorder"
    // Returns an int[] of all node values in traversal order:
    public int[] saveToArray(String traversalType) {
        int size = countElements();
        int[] arr = new int[size];
        int[] index = {0}; // use array to allow mutation inside lambda/inner class
 
        switch (traversalType.toLowerCase()) {
            case "preorder":
                savePreOrder(root, arr, index);
                break;
            case "postorder":
                savePostOrder(root, arr, index);
                break;
            default: // inorder
                saveInOrder(root, arr, index);
                break;
        }
        return arr;
    }
 
    private void saveInOrder(Node node, int[] arr, int[] index) {
        if (node == null) return;
        saveInOrder(node.leftChild, arr, index);
        arr[index[0]++] = node.data;
        saveInOrder(node.rightChild, arr, index);
    }
 
    private void savePreOrder(Node node, int[] arr, int[] index) {
        if (node == null) return;
        arr[index[0]++] = node.data;
        savePreOrder(node.leftChild, arr, index);
        savePreOrder(node.rightChild, arr, index);
    }
 
    private void savePostOrder(Node node, int[] arr, int[] index) {
        if (node == null) return;
        savePostOrder(node.leftChild, arr, index);
        savePostOrder(node.rightChild, arr, index);
        arr[index[0]++] = node.data;
    }

    // Traversals
    public void inOrder(Node localRoot) {
        if (localRoot != null) {
            inOrder(localRoot.leftChild);
            localRoot.displayNode();
            inOrder(localRoot.rightChild);
        }
    }

    public void preOrder(Node localRoot) {
        if (localRoot != null) {
            localRoot.displayNode();
            preOrder(localRoot.leftChild);
            preOrder(localRoot.rightChild);
        }
    }

    public void postOrder(Node localRoot) {
        if (localRoot != null) {
            postOrder(localRoot.leftChild);
            postOrder(localRoot.rightChild);
            localRoot.displayNode();
        }
    }

    public Node getRoot() {
        return root;
    }

    // PROBLEM 1: COUNT THE NUMBER OF ELEMENTS IN THE TREE
    // Returns: total number of nodes
    public int countElements() {
        return countElementsHelper(root);
    }

    private int countElementsHelper(Node node) {
        if (node == null)
            return 0;
        return 1 + countElementsHelper(node.leftChild) + countElementsHelper(node.rightChild);
    }

    // PROBLEM 2: COMPUTE THE HEIGHT OF THE TREE
    // Returns: height (empty = 0, single node = 1)
    public int height() {
        return heightHelper(root);
    }

    private int heightHelper(Node node) {
        if (node == null)
            return 0;
        int leftHeight = heightHelper(node.leftChild);
        int rightHeight = heightHelper(node.rightChild);
        return 1 + Math.max(leftHeight, rightHeight);
    }

    // PROBLEM 3: COUNT THE LEAVES OF THE TREE
    // Returns: number of leaf nodes (node with no children)
    public int countLeaves() {
        return countLeavesHelper(root);
    }

    private int countLeavesHelper(Node node) {
        if (node == null) 
            return 0;
        if (node.leftChild == null && node.rightChild == null)
            return 1;
        return countLeavesHelper(node.leftChild) + countLeavesHelper(node.rightChild);
    }

    // PROBLEM 4: CHECK IF THE TREE IS FULLY BALANCED
    // A tree is fully balanced if for every node, the heights of left and right subtrees differ by at most 1
    // Returns: true if fully balanced, false otherwise
    public boolean isFullyBalanced() {
        return isBalancedHelper(root) != -1;
    }

    private int isBalancedHelper(Node node) {
        if (node == null)
            return 0;
        int leftHeight = isBalancedHelper(node.leftChild);
        if (leftHeight == -1) return -1;
        int rightHeight = isBalancedHelper(node.rightChild);
        if (rightHeight == -1) return -1;
        int diff = Math.abs(leftHeight - rightHeight);
        if (diff > 1)
            return -1;
        return 1 + Math.max(leftHeight, rightHeight);
    }

    // PROBLEM 5: CHECK IF TWO TREES ARE IDENTICAL
    // Returns: true if identical, false otherwise
    public boolean isIdentical(Tree otherTree) {
        return isIdenticalHelper(this.root, otherTree.root);
    }

    private boolean isIdenticalHelper(Node nodeA, Node nodeB) {
        if (nodeA == null && nodeB == null)
            return true;
        if (nodeA == null || nodeB == null)
            return false;
        return (nodeA.data == nodeB.data)
            && isIdenticalHelper(nodeA.leftChild, nodeB.leftChild)
            && isIdenticalHelper(nodeA.rightChild, nodeB.rightChild);
    }
}