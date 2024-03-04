package tp2;

import javax.swing.text.AsyncBoxView;
import javax.xml.crypto.Data;

public class HashMap<KeyType, DataType> {

    private static final int DEFAULT_CAPACITY = 20;
    private static final float DEFAULT_LOAD_FACTOR = 0.5f;
    private static final int CAPACITY_INCREASE_FACTOR = 2;

    private Node<KeyType, DataType>[] map;
    private int size = 0;
    private int capacity;
    private final float loadFactor; // Compression factor

    public HashMap() { this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR); }

    public HashMap(int initialCapacity) {
        this(initialCapacity > 0 ? initialCapacity : DEFAULT_CAPACITY,
                DEFAULT_LOAD_FACTOR);
    }

    public HashMap(int initialCapacity, float loadFactor) {
        capacity = initialCapacity;
        this.loadFactor = 1 / loadFactor;
        map = new Node[capacity];
    }

    /**
     * Finds the index attached to a particular key
     * This is the hashing function ("Fonction de dispersement")
     * @param key Value used to access to a particular instance of a DataType within map
     * @return Index value where this key should be placed in attribute map
     */
    private int hash(KeyType key){
        int keyHash = key.hashCode() % capacity;
        return Math.abs(keyHash);
    }

    /**
     * @return if map should be rehashed
     */
    private boolean needRehash() {
        //return size / capacity >= loadFactor;
        return size * loadFactor > capacity;
    }

    /**
     * @return Number of elements currently in the map
     */
    public int size() {
        return size;
    }

    /**
     * @return Current reserved space for the map
     */
    public int capacity(){
        return capacity;
    }

    /**
     * @return if map does not contain any element
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**Average Case : O(n)
     * Increases capacity by CAPACITY_INCREASE_FACTOR (multiplication) and
     * reassigns all contained values within the new map
     */
    private void rehash() {
        if(needRehash()){
            Node<KeyType, DataType>[] oldMap = map;
            capacity *= CAPACITY_INCREASE_FACTOR;
            clear();

            for(Node<KeyType,DataType> node : oldMap){
                Node<KeyType,DataType> presentSurOld = node;
                while (presentSurOld != null){
                    if(map[hash(presentSurOld.key)] == null){
                        map[hash(presentSurOld.key)] = new Node<KeyType,DataType>(presentSurOld.key,presentSurOld.data);
                        size++;
                    }
                    else{
                        Node presentSurNew = map[hash(presentSurOld.key)];
                        while(presentSurNew.next != null){
                            presentSurNew = presentSurNew.next;
                        }
                        presentSurNew.next = new Node<KeyType,DataType>(presentSurOld.key,presentSurOld.data);
                        size++;
                    }
                    presentSurOld = presentSurOld.next;
                }
            }
        }
    }

    /** Average Case : O(1)
     * Finds if map contains a key
     * @param key Key which we want to know if exists within map
     * @return if key is already used in map
     */
    public boolean containsKey(KeyType key) {
        Node<KeyType,DataType> present = map[hash(key)];
        while(present!=null && present.next!=null){
            if(present.key.equals(key)){
                return true;
            }
            present = present.next;
        }
        if(present!=null && present.key.equals(key)){
            return true;
        }
        return false;
    }

    /** Average Case : O(1)
     * Finds the value attached to a key
     * @param key Key which we want to have its value
     * @return DataType instance attached to key (null if not found)
     */
    public DataType get(KeyType key) {
        Node<KeyType,DataType> present = map[hash(key)];
        if(present!=null){
            while(present.next!=null){
                if(present.key.equals(key)){
                    return present.data;
                }
                present = present.next;
            }
            if(present.key.equals(key)){
                return present.data;
            }
        }
        return null;
    }

    /** Average Case : O(1) , Worst Case : O(n)
     * Assigns a value to a key
     * @param key Key which will have its value assigned or reassigned
     * @return Old DataType instance at key (null if none existed)
     */
    public DataType put(KeyType key, DataType value) {

        if(map[hash(key)] == null) {
            map[hash(key)] = new Node<KeyType, DataType>(key,value);
            size++;
            rehash();
            return null;
        }
        Node present = map[hash(key)];
        while(present.next != null){
            if(present.key.equals(key)){
                DataType oldValue = map[hash(key)].data;
                map[hash(key)].data = value;
                return oldValue;
            }
            present = present.next;
        }
        if(present.key.equals(key)){
            DataType oldValue = map[hash(key)].data;
            map[hash(key)].data = value;
            return oldValue;
        }
        else{
            present.next = new Node<KeyType,DataType>(key,value);
            size++;
            rehash();
            return null;
        }
    }

    /** Average Case : O(1)
     * Removes the node attached to a key
     * @param key Key which is contained in the node to remove
     * @return Old DataType instance at key (null if none existed)
     */
    public DataType remove(KeyType key) {
        if(containsKey(key)){
            if(map[hash(key)].key.equals(key)){
                DataType oldData = map[hash(key)].data;
                map[hash(key)] = map[hash(key)].next;
                size--;
                return oldData;
            }
            else{
                Node<KeyType,DataType> present = map[hash(key)];
                while(!present.next.key.equals(key)){
                    present = present.next;
                }
                DataType oldData = present.next.data;
                present.next = present.next.next;
                size--;
                return oldData;
            }

        }
        return null;
    }

    /** Worst Case : O(1)
     * Removes all nodes contained within the map
     */
    public void clear() {
        map = new Node[capacity];
        size = 0;
    }

    static class Node<KeyType, DataType> {
        final KeyType key;
        DataType data;
        Node<KeyType, DataType> next; // Pointer to the next node within a Linked List

        Node(KeyType key, DataType data)
        {
            this.key = key;
            this.data = data;
            next = null;
        }
    }
}