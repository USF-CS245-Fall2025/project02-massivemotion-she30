/**
 * Doubly-linked list implementation of the custom List<T> interface
 * used in CS245 Project 2. This structure stores elements in nodes
 * that contain both next and prev pointers, allowing efficient
 * insertions and deletions from both ends.
 *
 * @param <T> the type of elements stored in this list
 */
public class DoublyLinkedList<T> implements List<T> {

    /**
     * Node class used for storing values in this linked list.
     * Each node contains:
     * - a value of type T
     * - a reference to the next node
     * - a reference to the previous node
     */
    private static class Node<T> { 
        T v; 
        Node<T> next, prev; 
        Node(T v){ this.v = v; } 
    }

    /** Pointer to the first node in the list. */
    private Node<T> head;

    /** Pointer to the last node in the list. */
    private Node<T> tail;

    /** Number of elements currently stored in the list. */
    private int n;

    /**
     * Inserts an element at the specified index.
     * If index == n, the element is appended to the end.
     *
     * @param index index at which to insert
     * @param element element to insert
     * @throws IndexOutOfBoundsException if index is invalid
     */
    @Override
    public void add(int index, T element) {
        if (index < 0 || index > n) throw new IndexOutOfBoundsException();
        if (index == n) { add(element); return; }

        Node<T> nd = new Node<>(element);
        Node<T> cur = nodeAt(index);

        nd.prev = cur.prev;
        nd.next = cur;

        if (cur.prev != null) cur.prev.next = nd;
        else head = nd;

        cur.prev = nd;
        n++;
    }

    /**
     * Appends an element to the end of the list.
     *
     * @param element element to add
     * @return always returns true
     */
    @Override
    public boolean add(T element) {
        Node<T> nd = new Node<>(element);

        if (head == null) {
            head = tail = nd;
        } else {
            tail.next = nd;
            nd.prev = tail;
            tail = nd;
        }

        n++;
        return true;
    }

    /**
     * Returns the element at a given index.
     *
     * @param index index of element to retrieve
     * @return the element stored at that index
     * @throws IndexOutOfBoundsException if index is invalid
     */
    @Override
    public T get(int index) { 
        return nodeAt(index).v; 
    }

    /**
     * Removes and returns the element at the specified index.
     * The surrounding nodes are re-linked to preserve the list.
     *
     * @param index index of element to remove
     * @return the removed element
     * @throws IndexOutOfBoundsException if index is invalid
     */
    @Override
    public T remove(int index) {
        Node<T> cur = nodeAt(index);

        if (cur.prev != null) cur.prev.next = cur.next;
        else head = cur.next;

        if (cur.next != null) cur.next.prev = cur.prev;
        else tail = cur.prev;

        n--;
        return cur.v;
    }

    /**
     * Returns the number of elements in the list.
     *
     * @return the list size
     */
    @Override
    public int size() { 
        return n; 
    }

    /**
     * Returns the node at a given index.
     * This method chooses traversal direction based on whether
     * the index is in the first or second half of the list
     * for efficiency.
     *
     * @param index index of node to retrieve
     * @return the Node located at that index
     * @throws IndexOutOfBoundsException if index is invalid
     */
    private Node<T> nodeAt(int index) {
        if (index < 0 || index >= n) throw new IndexOutOfBoundsException();

        Node<T> cur;

        // Traverse from head or tail depending on index location
        if (index < n / 2) {
            cur = head;
            for (int i = 0; i < index; i++) cur = cur.next;
        } else {
            cur = tail;
            for (int i = n - 1; i > index; i--) cur = cur.prev;
        }

        return cur;
    }
}
