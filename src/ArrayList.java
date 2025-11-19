/**
 * Array-based implementation of the custom List<T> interface
 * used in CS245 Project 2. This class stores elements inside
 * a dynamically resizing Object[] array and provides indexed
 * add, get, remove, and size operations.
 *
 * @param <T> the type of elements stored in this list
 */
public class ArrayList<T> implements List<T> {

    /** Internal array that stores list elements. */
    private Object[] data;

    /** Number of elements currently stored in the list. */
    private int n;

    /**
     * Constructs an ArrayList with a default initial capacity of 10.
     */
    public ArrayList() { 
        this(10); 
    }

    /**
     * Constructs an ArrayList with a specified initial capacity.
     * If the provided capacity is less than 1, it defaults to 10.
     *
     * @param cap initial capacity for the internal array
     */
    public ArrayList(int cap) {
        if (cap < 1) cap = 10;
        data = new Object[cap];
        n = 0;
    }

    /**
     * Doubles the internal array size when no more space is available.
     * This ensures amortized O(1) performance for append operations.
     */
    private void growIfNeeded() {
        if (n >= data.length) {
            Object[] nd = new Object[Math.max(1, data.length * 2)];
            for (int i = 0; i < n; i++) nd[i] = data[i];
            data = nd;
        }
    }

    /**
     * Checks that an index is within 0..n (inclusive)
     * which is used by add(index, element).
     *
     * @param idx index to validate
     * @throws IndexOutOfBoundsException if index < 0 or index > n
     */
    private void checkIndexInclusive(int idx) {
        if (idx < 0 || idx > n)
            throw new IndexOutOfBoundsException("index=" + idx);
    }

    /**
     * Checks that an index is within 0..n-1 (exclusive)
     * which is used by get() and remove().
     *
     * @param idx index to validate
     * @throws IndexOutOfBoundsException if index < 0 or index >= n
     */
    private void checkIndexExclusive(int idx) {
        if (idx < 0 || idx >= n)
            throw new IndexOutOfBoundsException("index=" + idx);
    }

    /**
     * Inserts an element at the given index, shifting existing elements
     * to the right. Valid indexes are 0..n (inclusive).
     *
     * @param index position where the element will be inserted
     * @param element the value to add
     */
    @Override
    public void add(int index, T element) {
        checkIndexInclusive(index);
        growIfNeeded();
        for (int i = n; i > index; i--) data[i] = data[i - 1];
        data[index] = element;
        n++;
    }

    /**
     * Appends an element at the end of the list.
     *
     * @param element the value to append
     * @return always returns true
     */
    @Override
    public boolean add(T element) {
        growIfNeeded();
        data[n++] = element;
        return true;
    }

    /**
     * Returns the element stored at the given index.
     *
     * @param index index of the element to access
     * @return element at the given index
     * @throws IndexOutOfBoundsException if index is invalid
     */
    @SuppressWarnings("unchecked")
    @Override
    public T get(int index) {
        checkIndexExclusive(index);
        return (T) data[index];
    }

    /**
     * Removes and returns the element at the given index.
     * Elements to the right of the index are shifted left.
     *
     * @param index index of the element to remove
     * @return the removed element
     * @throws IndexOutOfBoundsException if index is invalid
     */
    @SuppressWarnings("unchecked")
    @Override
    public T remove(int index) {
        checkIndexExclusive(index);
        T old = (T) data[index];
        for (int i = index; i < n - 1; i++) data[i] = data[i + 1];
        data[--n] = null;
        return old;
    }

    /**
     * Returns the number of elements currently stored.
     *
     * @return number of elements in this list
     */
    @Override
    public int size() { 
        return n; 
    }
}
