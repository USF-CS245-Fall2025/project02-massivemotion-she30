/**
 * Dummy-head singly linked list implementation of the custom List
 * interface used in CS245 Project 2. This design uses a sentinel
 * (dummy) head node to simplify insertions and deletions at index 0.
 *
 * The list supports:
 * - indexed insertion
 * - append
 * - indexed removal
 * - indexed access
 * - size() in O(1)
 *
 * @param <T> the type of elements stored in this list
 */
public class DummyHeadLinkedList<T> implements List<T> {

    /**
     * Node class for singly-linked list.
     * Each node stores:
     *  - a value of type T
     *  - a pointer to the next node
     */
    private static class Node<T> {
        T v;
        Node<T> next;
        Node(T v) { this.v = v; }
    }

    /** Sentinel node that never stores a real value. */
    private final Node<T> head = new Node<>(null);

    /** Number of elements currently stored in the list. */
    private int n;

    /**
     * Inserts an element at a specific index.
     * With a dummy head node, we can always safely find (index-1)
     * without special-case handling for index 0.
     *
     * @param index index at which the element will be inserted
     * @param element element to insert
     * @throws IndexOutOfBoundsException if index is not in 0..n
     */
    @Override
    public void add(int index, T element) {
        if (index < 0 || index > n)
            throw new IndexOutOfBoundsException();

        Node<T> prev = head;
        for (int i = 0; i < index; i++) 
            prev = prev.next;

        Node<T> nd = new Node<>(element);
        nd.next = prev.next;
        prev.next = nd;
        n++;
    }

    /**
     * Appends an element to the end of the list.
     * This is implemented by calling add(n, element).
     *
     * @param element element to append
     * @return always returns true
     */
    @Override
    public boolean add(T element) {
        add(n, element);
        return true;
    }

    /**
     * Returns the element stored at a given index.
     *
     * @param index index of element to retrieve
     * @return the value stored at that index
     * @throws IndexOutOfBoundsException if index is not in 0..n-1
     */
    @Override
    public T get(int index) {
        if (index < 0 || index >= n)
            throw new IndexOutOfBoundsException();

        Node<T> cur = head.next;
        for (int i = 0; i < index; i++) 
            cur = cur.next;

        return cur.v;
    }

    /**
     * Removes and returns the element at a specific index.
     * Again, the dummy head simplifies removal at index 0.
     *
     * @param index index of element to remove
     * @return the removed element
     * @throws IndexOutOfBoundsException if index is not in 0..n-1
     */
    @Override
    public T remove(int index) {
        if (index < 0 || index >= n)
            throw new IndexOutOfBoundsException();

        Node<T> prev = head;
        for (int i = 0; i < index; i++) 
            prev = prev.next;

        Node<T> del = prev.next;
        prev.next = del.next;
        n--;
        return del.v;
    }

    /**
     * Returns the number of elements currently stored.
     *
     * @return current list size
     */
    @Override
    public int size() { 
        return n; 
    }
}
