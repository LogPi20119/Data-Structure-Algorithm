// hashChain.java
// demonstrates hash table with separate chaining
// to run this program: C:>java HashChainApp
import java.io.*;
////////////////////////////////////////////////////////////////
class Link
   {                                   // (could be other items)
   private int iData;                  // data item
   public Link next;                   // next link in list
// -------------------------------------------------------------
   public Link(int it)                 // constructor
      { iData= it; }
// -------------------------------------------------------------
   public int getKey()
      { return iData; }
// -------------------------------------------------------------
   public void displayLink()           // display this link
      { System.out.print(iData + " "); }
   }  // end class Link
////////////////////////////////////////////////////////////////
class SortedList
   {
   private Link first;               // ref to first list item
// -------------------------------------------------------------
   public SortedList()          // constructor
      { first = null; }
// -------------------------------------------------------------
   public int insert(Link theLink)  // insert link, in order
      {
      int key = theLink.getKey();
      Link previous = null;          // start at first
      Link current = first;
      int probes = 1;                // count the initial position check
                                     // until end of list,
      while( current != null && key > current.getKey() )
         {                           // or current > key,
         previous = current;
         current = current.next;
         probes++;     // go to next item
         }
      if(previous==null)             // if beginning of list,
         first = theLink;            //    first --> new link
      else                           // not at beginning,
         previous.next = theLink;    //    prev --> new link
      theLink.next = current;
      
      return probes;
      }  // end insert()
// -------------------------------------------------------------
   public void delete(int key)       // delete link
      {                              // (assumes non-empty list)
      Link previous = null;          // start at first
      Link current = first;
                                     // until end of list,
      while( current != null && key != current.getKey() )
         {                           // or key == current,
         previous = current;
         current = current.next;     // go to next link
         }

         if (current == null) return; // not found
                                     // disconnect link
         if(previous == null)             //   if beginning of list
            first = first.next;         //      delete first link
         else                           //   not at beginning
            previous.next = current.next; //    delete current link
      }  // end delete()
// -------------------------------------------------------------
   public Link find(int key, int[] probeCount)         // find link
      {
      Link current = first;          // start at first
      probeCount[0] = 1;             
                                     // until end of list,
      while(current != null &&  current.getKey() <= key)
         {                           // or key too small,
         if(current.getKey() == key)    // is this the link?
            return current;          // found it, return link
         current = current.next;
         probeCount[0]++;     
         }
      return null;                   // didn't find it
      }  // end find()
// -------------------------------------------------------------
   public void displayList()
      {
      System.out.print("List (first-->last): ");
      Link current = first;       // start at beginning of list
      while(current != null)      // until end of list,
         {
         current.displayLink();   // print data
         current = current.next;  // move to next link
         }
      System.out.println("");
      }
   }  // end class SortedList
////////////////////////////////////////////////////////////////
class HashTable
   {
   private SortedList[] hashArray;   // array of lists
   private int arraySize;
   private int totalProbes;
   private int operationCount;
// -------------------------------------------------------------
   public HashTable(int size)        // constructor
      {
      arraySize = size;
      hashArray = new SortedList[arraySize];  // create array
      for(int j=0; j<arraySize; j++)          // fill array
         hashArray[j] = new SortedList();
      totalProbes = 0;
      operationCount = 0;
      }
// -------------------------------------------------------------
   public void displayTable()
      {
      for(int j=0; j<arraySize; j++) // for each cell,
         {
         System.out.print(j + ". "); // display cell number
         hashArray[j].displayList(); // display list
         }
      }
// -------------------------------------------------------------
   public int hashFunc(int key)      // hash function
      {
      return key % arraySize;
      }
// -------------------------------------------------------------
   public void insert(Link theLink)  // insert a link
      {
      int key = theLink.getKey();
      int hashVal = hashFunc(key);   // hash the key
      int probes = hashArray[hashVal].insert(theLink); // insert at hashVal

      System.out.printf("Insert key = %d bucket = %d probe length = %d%n", key, hashVal, probes);
      totalProbes += probes;
      operationCount++;
      }  // end insert()
// -------------------------------------------------------------
   public void insertQuiet(Link theLink) {   // Insert silently (initial fill)
      int key = theLink.getKey();
      int hashVal = hashFunc(key);
      int probes = hashArray[hashVal].insert(theLink);

      totalProbes += probes;
      operationCount++;
   }      
// -------------------------------------------------------------
   public void delete(int key)       // delete a link
      {
      int hashVal = hashFunc(key);   // hash the key
      hashArray[hashVal].delete(key); // delete link
      }  // end delete()
// -------------------------------------------------------------
   public Link find(int key)         // find link
      {
      int hashVal = hashFunc(key);   // hash the key
      int[] probeCount = {0};
      Link result = hashArray[hashVal].find(key, probeCount);
      
      System.out.printf("Find key = %d bucket = %d probe length = %d%n", key, hashVal, probeCount[0]);
      totalProbes += probeCount[0];
      operationCount++;
      return result;                // return link
      }
// -------------------------------------------------------------
   public void resetStats() {
      totalProbes = 0;
      operationCount = 0;
   }
   public double getAverageProbeLength() {
      if (operationCount == 0) return 0.0;
      return (double) totalProbes / operationCount;
   } 
// -------------------------------------------------------------
}  // end class HashTable
////////////////////////////////////////////////////////////////
class HashChainApp
   {
   public static void main(String[] args) throws IOException
      {
      int aKey;
      Link aDataItem;
      int size, n, keysPerCell = 100;
                                     // get sizes
      System.out.print("Enter size of hash table: ");
      size = getInt();
      System.out.print("Enter initial number of items: ");
      n = getInt();
                                     // make table
      HashTable theHashTable = new HashTable(size);
      
      System.out.println("--- Initial fill: key sequence ---");
      int[] initialKeys = new int[n];
      for(int j=0; j<n; j++)         // insert data
         {
         aKey = (int)(java.lang.Math.random() * keysPerCell * size);
         initialKeys[j] = aKey;
         System.out.print(aKey + " ");
         }
         System.out.println();
         
         for (int j = 0; j < n; j++) {
            aDataItem = new Link(initialKeys[j]);
            theHashTable.insertQuiet(aDataItem);
         }

      System.out.printf("%nAverage probe length (initial fill): %.2f%n", theHashTable.getAverageProbeLength());
      theHashTable.resetStats();

      while(true)                    // interact with user
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
               aDataItem = new Link(aKey);
               theHashTable.insert(aDataItem);
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
   private static final BufferedReader br =
      new BufferedReader(new InputStreamReader(System.in));
      
   public static String getString() throws IOException
      {
      InputStreamReader isr = new InputStreamReader(System.in);
      BufferedReader br = new BufferedReader(isr);
      String s = br.readLine();
      return s;
      }
//-------------------------------------------------------------
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
   }  // end class HashChainApp
////////////////////////////////////////////////////////////////