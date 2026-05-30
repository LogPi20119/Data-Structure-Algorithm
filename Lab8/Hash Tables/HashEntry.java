public class HashEntry {
    public int key;
    public boolean isDeleted;

    public HashEntry(int key) {
        this.key = key;
        this.isDeleted = false;
    }
}
