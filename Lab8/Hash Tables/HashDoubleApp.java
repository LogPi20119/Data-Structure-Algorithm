// hashDouble.java
// demonstrates hash table with double hashing
// to run this program: C:>java HashDoubleApp
import java.io.*;
////////////////////////////////////////////////////////////////
class HashDoubleApp
   {
   public static void main(String[] args) throws IOException
      {
      int aKey;
      DataItem aDataItem;
      int size, n;
                                  // get sizes
      System.out.print("Enter size of hash table: ");
      size = getInt();
      System.out.print("Enter initial number of items: ");
      n = getInt();
                                  // make table
      HashTable theHashTable = new HashTable(size);
      
      System.out.println("--- Initial fill: key sequence ---");   // initial fill
      int[] initialKeys = new int[n];
      for(int j=0; j<n; j++)      // insert data
         {
         aKey = (int)(java.lang.Math.random() * 2 * size);
         initialKeys[j] = aKey;
         System.out.println(aKey + " ");
         }
         System.out.println();

         for (int j = 0; j < n; j++)
         {
         aDataItem = new DataItem(initialKeys[j]);
         theHashTable.insert(initialKeys[j], aDataItem);
         }

         System.out.printf("%nAverage probe length (initial fill): %.2f%n",
                          theHashTable.getAverageProbeLength());
         theHashTable.resetStats(); // Reset so interactive operations are tracked separately

      while(true)                 // interact with user
         {
         System.out.print("Enter first letter of ");
         System.out.print("show, insert, delete, or find: ");
         char choice = getChar();
         switch(choice)
            {
            case 's':
               theHashTable.displayTable();
               break;
            case 'i':
               System.out.print("Enter key value to insert: ");
               aKey = getInt();
               aDataItem = new DataItem(aKey);
               theHashTable.insert(aKey, aDataItem);
               break;
            case 'd':
               System.out.print("Enter key value to delete: ");
               aKey = getInt();
               theHashTable.delete(aKey);
               break;
            case 'f':
               System.out.print("Enter key value to find: ");
               aKey = getInt();
               aDataItem = theHashTable.find(aKey);
               if(aDataItem != null)
                  System.out.println("Found " + aKey);
               else
                  System.out.println("Could not find " + aKey);
               break;
            default:
               System.out.print("Invalid entry\n");
            }  // end switch
         }  // end while
      }  // end main()
//--------------------------------------------------------------
   private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//--------------------------------------------------------------
   public static String getString() throws IOException
      {
      InputStreamReader isr = new InputStreamReader(System.in);
      BufferedReader br = new BufferedReader(isr);
      String s = br.readLine();
      return s;
      }
//--------------------------------------------------------------
   public static char getChar() throws IOException
      {
      String s = getString();
      return s.charAt(0);
      }
//-------------------------------------------------------------
   public static int getInt() throws IOException
      {
      String s = getString();
      return Integer.parseInt(s);
      }
//--------------------------------------------------------------
   }  // end class HashDoubleApp
////////////////////////////////////////////////////////////////

class DataItem
   {                                 // (could have more items)
   private int iData;                // data item (key)
//--------------------------------------------------------------
   public DataItem(int ii)           // constructor
      { iData = ii; }
//--------------------------------------------------------------
   public int getKey()
      { return iData; }
//--------------------------------------------------------------
   }  // end class DataItem
////////////////////////////////////////////////////////////////
class HashTable
   {
   private DataItem[] hashArray;     // array is the hash table
   private int arraySize;
   private DataItem nonItem;         // for deleted items
   private int totalProbes;         // accumulated probe count
   private int operationCount;       // number of operations tracked
// -------------------------------------------------------------
   public HashTable(int size)               // constructor
      {
      arraySize = size;
      hashArray = new DataItem[arraySize];
      nonItem = new DataItem(-1);
      totalProbes = 0;
      operationCount = 0;
      }
// -------------------------------------------------------------
   public void displayTable()
      {
      System.out.print("Table: ");
      for(int j=0; j<arraySize; j++)
         {
         if(hashArray[j] != null)
            System.out.print(hashArray[j].getKey()+ " ");
         else
            System.out.print("** ");
         }
      System.out.println("");
      }
// -------------------------------------------------------------
   public int hashFunc1(int key)
      {
      return key % arraySize;
      }
// -------------------------------------------------------------
   public int hashFunc2(int key)
      {
      // non-zero, less than array size, different from hF1
      // array size must be relatively prime to 5, 4, 3, and 2
      return 5 - key % 5;
      }
// -------------------------------------------------------------
                                     // insert a DataItem
   public void insert(int key, DataItem item)
   // (assumes table not full)
      {
      int hashVal = hashFunc1(key);  // hash the key
      int stepSize = hashFunc2(key); // get step size
      int probes = 1;

      System.out.printf("Insert key = %d hashVal = %d step = %d%n", key, hashVal, stepSize);
      System.out.print("Probe sequence: " + hashVal);
                                     // until empty cell or -1
      while(hashArray[hashVal] != null &&
                      hashArray[hashVal].getKey() != -1)
         {
         hashVal += stepSize;        // add the step
         hashVal %= arraySize;
         probes++;
         System.out.print(" -> " + hashVal);       // for wraparound
         }
      hashArray[hashVal] = item;     // insert item
      System.out.printf("Probe length: %d%n", probes);
      totalProbes += probes;
      operationCount++;
      }  // end insert()

      // Insert silently (used during initial load to collect stats only)
      public void insertQuiet(int key, DataItem item) {
         int hashVal  = hashFunc1(key);
         int stepSize = hashFunc2(key);
         int probes   = 1;
 
         while (hashArray[hashVal] != null &&
               hashArray[hashVal].getKey() != -1) {
            hashVal = (hashVal + stepSize) % arraySize;
            probes++;
         }
 
         hashArray[hashVal] = item;
         totalProbes += probes;
         operationCount++;
      }
// -------------------------------------------------------------
   public DataItem delete(int key)   // delete a DataItem
      {
      int hashVal = hashFunc1(key);      // hash the key
      int stepSize = hashFunc2(key);     // get step size

      while(hashArray[hashVal] != null)  // until empty cell,
         {                               // is correct hashVal?
         if(hashArray[hashVal].getKey() == key)
            {
            DataItem temp = hashArray[hashVal]; // save item
            hashArray[hashVal] = nonItem;       // delete item
            return temp;                        // return item
            }
         hashVal += stepSize;            // add the step
         hashVal %= arraySize;           // for wraparound
         }
      return null;                   // can't find item
      }  // end delete()
// -------------------------------------------------------------
   public DataItem find(int key)     // find item with key
   // (assumes table not full)
      {
      int hashVal = hashFunc1(key);      // hash the key
      int stepSize = hashFunc2(key);     // get step size
      int probes = 1;

      System.out.printf("  Find  key=%d  hashVal=%d  step=%d%n",
                          key, hashVal, stepSize);
      System.out.print ("  Probe sequence: " + hashVal);

      while(hashArray[hashVal] != null)  // until empty cell,
         {                               // is correct hashVal?
         if (hashArray[hashVal].getKey() == key) {
               System.out.printf("%n  Probe length: %d%n", probes);
               totalProbes += probes;
               operationCount++;
               return hashArray[hashVal];
            }
            hashVal = (hashVal + stepSize) % arraySize;
            probes++;
            System.out.print(" -> " + hashVal);
         }
      System.out.printf("%n  Probe length: %d (not found)%n", probes);
      totalProbes += probes;
      operationCount++;
      return null;                   // can't find item
      }

   // ------------------------------------------------------------
   // Reset statistics (called after initial fill if we want separate tracking)
   public void resetStats() {
      totalProbes    = 0;
      operationCount = 0;
   }
 
   public double getAverageProbeLength() {
      if (operationCount == 0) return 0.0;
      return (double) totalProbes / operationCount;
   }
// -------------------------------------------------------------
}  // end class HashTable
////////////////////////////////////////////////////////////////