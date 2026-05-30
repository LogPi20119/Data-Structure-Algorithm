import java.util.Random;
import java.util.Scanner;

public class TreeApp {

    // Main entry point
    public static void main(String[] args) throws Exception {
        Tree theTree = new Tree();
        Scanner scanner = new Scanner(System.in);

        // Seed some initial values for demonstration
        theTree.insert(50);
        theTree.insert(25);
        theTree.insert(75);
        theTree.insert(12);
        theTree.insert(37);
        theTree.insert(43);
        theTree.insert(87);

        System.out.println("=== BST Application - ITITIU21238 ===");
        System.out.println("Initial tree (in-order): ");
        theTree.inOrder(theTree.getRoot());
        System.out.println();

        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Enter choice: ");
            int choice = getInt(scanner);

            switch (choice) {
                // 1. Find a node
                case 1: {
                    System.out.print("Enter value to find: ");
                    int val = getInt(scanner);
                    theTree.resetCounters();
                    Node found = theTree.find(val);
                    if (found != null)
                        System.out.println("Found: " + found.data);
                    else
                        System.out.println("Value " + val + " not found.");
                    System.out.println("[find() comparisons: " + theTree.getFindComparisons() + "]");
                    analyzeEfficiency("find", theTree.getFindComparisons(), theTree.countElements());
                    break;
                }

                // 2. Insert a node
                case 2: {
                    System.out.print("Enter value to insert: ");
                    int val = getInt(scanner);
                    theTree.resetCounters();
                    theTree.insert(val);
                    System.out.println("Inserted: " + val);
                    System.out.println("[insert() comparisons: " + theTree.getInsertComparisons() + "]");
                    analyzeEfficiency("insert", theTree.getInsertComparisons(), theTree.countElements());
                    break;
                }

                // 3. Delete a node
                case 3: {
                    System.out.print("Enter value to delete: ");
                    int val = getInt(scanner);
                    theTree.resetCounters();
                    boolean deleted = theTree.delete(val);
                    if (deleted)
                        System.out.println("Deleted: " + val);
                    else
                        System.out.println("Value " + val + " not found.");
                    System.out.println("[delete() comparisons: " + theTree.getDeleteComparisons() + "]");
                    analyzeEfficiency("delete", theTree.getDeleteComparisons(), theTree.countElements());
                    break;
                }

                // 4. Display tree (traversals)
                case 4: {
                    System.out.print("In-order    : ");
                    theTree.inOrder(theTree.getRoot());
                    System.out.println();
                    System.out.print("Pre-order   : ");
                    theTree.preOrder(theTree.getRoot());
                    System.out.println();
                    System.out.print("Post-order  : ");
                    theTree.postOrder(theTree.getRoot());
                    System.out.println();
                    break;
                }

                // 5. Tree statistics (Lab 07 Problems 1–4)
                case 5: {
                    System.out.println("--- Tree Statistics ---");
                    System.out.println("  Total elements : " + theTree.countElements());
                    System.out.println("  Height         : " + theTree.height());
                    System.out.println("  Leaf count     : " + theTree.countLeaves());
                    System.out.println("  Fully balanced : " + theTree.isFullyBalanced());
                    break;
                }

                // 6. Check if identical to a second tree
                case 6: {
                    System.out.println("Building second tree — enter values one by one.");
                    System.out.println("Enter -1 to finish.");
                    Tree secondTree = new Tree();
                    while (true) {
                        System.out.print("  Value: ");
                        int v = getInt(scanner);
                        if (v == -1) break;
                        secondTree.insert(v);
                    }
                    System.out.println("Second tree (in-order): ");
                    secondTree.inOrder(secondTree.getRoot());
                    System.out.println();
                    boolean same = theTree.isIdentical(secondTree);
                    System.out.println("Trees are identical: " + same);
                    break;
                }

                // 7. Clear the tree
                case 7: {
                    theTree.clear();
                    System.out.println("Tree cleared. All nodes removed.");
                    break;
                }

                // 8. Insert random items
                case 8: {
                    System.out.print("How many random items to insert? ");
                    int count = getInt(scanner);
                    System.out.print("Value range max (e.g. 100): ");
                    int range = getInt(scanner);

                    Random rand = new Random();
                    System.out.print("Inserted: ");
                    for (int i = 0; i < count; i++) {
                        int val = rand.nextInt(range) + 1;
                        theTree.insert(val);
                        System.out.print(val + " ");
                    }
                    System.out.println();
                    System.out.println("--- Tree after random inserts ---");
                    System.out.println("  Elements : " + theTree.countElements());
                    System.out.println("  Height   : " + theTree.height());
                    System.out.println("  Balanced : " + theTree.isFullyBalanced());
                    System.out.print("  In-order : ");
                    theTree.inOrder(theTree.getRoot());
                    System.out.println();
                    break;
                }

                // 9. Find min and max
                case 9: {
                    Node minNode = theTree.findMin();
                    Node maxNode = theTree.findMax();
                    if (minNode != null)
                        System.out.println("Minimum value: " + minNode.data);
                    else
                        System.out.println("Tree is empty.");
                    if (maxNode != null)
                        System.out.println("Maximum value: " + maxNode.data);
                    break;
                }

                // 10. Save via traversal and reinsert
                case 10: {
                    traversalReinsertDemo(theTree, scanner);
                    break;
                }

                // 0. Exit
                case 0:
                    running = false;
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            if (choice != 4 && choice != 0) {
                // Show quick state summary after most operations
                if (theTree.countElements() > 0) {
                    System.out.print("[Current tree in-order]: ");
                    theTree.inOrder(theTree.getRoot());
                    System.out.println();
                } else {
                    System.out.println("[Tree is currently empty]");
                }
            }
            System.out.println();
        }

        scanner.close();
    }

    // printMenu(): Display the main menu
    private static void printMenu() {
        System.out.println("------ MENU ------");
        System.out.println(" 1. Find a node");
        System.out.println(" 2. Insert a node");
        System.out.println(" 3. Delete a node");
        System.out.println(" 4. Display tree (all traversals)");
        System.out.println(" 5. Tree statistics (count / height / leaves / balanced)");
        System.out.println(" 6. Check if identical to another tree");
        System.out.println(" 7. Clear the tree");
        System.out.println(" 8. Insert random items");
        System.out.println(" 9. Find min / max");
        System.out.println("10. Save via traversal and reinsert");
        System.out.println(" 0. Exit");
        System.out.println("------------------");
    }

    // analyzeEfficiency(): Print complexity analysis for an operation
    private static void analyzeEfficiency(String op, int comparisons, int n) {
        System.out.println("  Efficiency analysis for " + op + "():");
        System.out.println("    n (nodes)    = " + n);
        System.out.println("    comparisons  = " + comparisons);
        double logN = (n > 0) ? Math.log(n) / Math.log(2) : 0;
        System.out.printf("    log2(n)      = %.2f%n", logN);
        System.out.println("    Expected     : O(log n) for balanced BST, O(n) worst case (skewed)");
        if (comparisons <= (int)(logN + 1))
            System.out.println("    Result       : EFFICIENT (close to balanced)");
        else
            System.out.println("    Result       : May indicate unbalanced tree");
    }

    // traversalReinsertDemo(): Save by traversal → reinsert → compare
    private static void traversalReinsertDemo(Tree originalTree, Scanner scanner) {
        if (originalTree.countElements() == 0) {
            System.out.println("Tree is empty. Please insert some nodes first.");
            return;
        }

        System.out.println("Choose traversal to save elements:");
        System.out.println("  1. In-order");
        System.out.println("  2. Pre-order");
        System.out.println("  3. Post-order");
        System.out.print("Choice: ");
        int tChoice = getInt(scanner);

        String traversalType;
        switch (tChoice) {
            case 2:  traversalType = "preorder";  break;
            case 3:  traversalType = "postorder"; break;
            default: traversalType = "inorder";   break;
        }

        // Save elements to array
        int[] saved = originalTree.saveToArray(traversalType);

        System.out.print("Saved array (" + traversalType + "): ");
        for (int v : saved) System.out.print(v + " ");
        System.out.println();

        // Reinsert into a new tree
        Tree newTree = new Tree();
        for (int v : saved) newTree.insert(v);

        System.out.println("--- New tree (reinserted from " + traversalType + ") ---");
        System.out.println("  Elements : " + newTree.countElements());
        System.out.println("  Height   : " + newTree.height());
        System.out.println("  Balanced : " + newTree.isFullyBalanced());
        System.out.print("  In-order : ");
        newTree.inOrder(newTree.getRoot());
        System.out.println();

        // Explanation
        System.out.println();
        System.out.println("  --- Observation ---");
        switch (traversalType) {
            case "inorder":
                System.out.println("  In-order produces a SORTED array.");
                System.out.println("  Re-inserting sorted data into a plain BST creates");
                System.out.println("  a SKEWED (degenerate) tree with height = n → O(n) operations.");
                break;
            case "preorder":
                System.out.println("  Pre-order saves root first → reconstructs the SAME tree structure.");
                System.out.println("  Height and shape will be identical to the original tree.");
                break;
            case "postorder":
                System.out.println("  Post-order saves leaves first → different insertion order.");
                System.out.println("  The resulting tree will likely have a different shape and height.");
                break;
        }
        System.out.println("  Original height : " + originalTree.height());
        System.out.println("  New tree height : " + newTree.height());
    }

    // getInt(): Safe integer input from scanner
    private static int getInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Enter a number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

} // end class TreeApp