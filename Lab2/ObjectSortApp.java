public class ObjectSortApp {

    // Bubble Sort by First Name
    static void sortByFirstName(Student[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j].getFirstName().compareTo(arr[j + 1].getFirstName()) > 0) {
                    Student temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    // Bubble Sort by Last Name 
    static void sortByLastName(Student[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j].getLastName().compareTo(arr[j + 1].getLastName()) > 0) {
                    Student temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    // Bubble Sort by Grade (ascending) 
    static void sortByGrade(Student[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j].getGrade() > arr[j + 1].getGrade()) {
                    Student temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    // Print Array 
    static void printArray(Student[] arr) {
        for (Student s : arr) {
            System.out.println("  " + s);
        }
    }

    // ---------- Clone Array ----------
    static Student[] cloneArray(Student[] arr) {
        Student[] copy = new Student[arr.length];
        for (int i = 0; i < arr.length; i++) copy[i] = arr[i];
        return copy;
    }

    // MAIN 
    public static void main(String[] args) {
        Student[] students = {
            new Student("A",   "Nguyen",  8.5),
            new Student("C", "Tran",    6.0),
            new Student("B",     "Le",      9.0),
            new Student("D",   "Pham",    7.5),
            new Student("E",  "Hoang",   5.5),
            new Student("F",   "Vo",      8.0),
            new Student("G",  "Dang",    6.5),
            new Student("H",  "Bui",     9.5),
            new Student("I",    "Do",      7.0),
            new Student("J",   "Ngo",     4.5)
        };

        System.out.println("=== ORIGINAL ARRAY ===");
        printArray(students);

        // Sort by First Name
        Student[] byFirstName = cloneArray(students);
        sortByFirstName(byFirstName);
        System.out.println("\n=== SORTED BY FIRST NAME ===");
        printArray(byFirstName);

        // Sort by Last Name
        Student[] byLastName = cloneArray(students);
        sortByLastName(byLastName);
        System.out.println("\n=== SORTED BY LAST NAME ===");
        printArray(byLastName);

        // Sort by Grade
        Student[] byGrade = cloneArray(students);
        sortByGrade(byGrade);
        System.out.println("\n=== SORTED BY GRADE (ascending) ===");
        printArray(byGrade);
    }
}
