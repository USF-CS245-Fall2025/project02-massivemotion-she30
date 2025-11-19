/**
 * Singly linked list implementation of the custom List<T> interface
 * used in CS245 Project 2. This version does not use a dummy head,
 * so operations at index 0 require special handling.
 *
 * Supported operations include:
 * - insertion at any index
 * - append to the end
 * - indexed access
 * - indexed removal
 * - reporting list size
 *
 * @param <T> the type of elements stored in this list
 */
public class LinkedList<T> implements List<T> {

    /**
     * Node class for singly linked list.  
     * Each node stores:
     *  - a value of type T  
     *  - a pointer to the next node  
     */
    private static class Node<T> {
        T v;
        Node<T> next;
        Node(T v) { this.v = v; }
    }

    /** Pointer to the first node in the list (null if empty). */
    private Node<T> head;

    /** Number of elements stored in this list. */
    private int n;

    /**
     * Inserts an element at the given index.  
     * If index == 0, the new node becomes the head.
     *
     * @param index index at which to insert the element
     * @param element element to insert
     * @throws IndexOutOfBoundsException if index is not in 0..n
     */
    @Override
    public void add(int index, T element) {
        if (index < 0 || index > n)
            throw new IndexOutOfBoundsException();

        Node<T> nd = new Node<>(element);

        if (index == 0) {
            nd.next = head;
            head = nd;
        } else {
            Node<T> cur = head;
            for (int i = 0; i < index - 1; i++)
                cur = cur.next;

            nd.next = cur.next;
            cur.next = nd;
        }

        n++;
    }

    /**
     * Appends an element to the end of the list.
     *
     * @param element element to append
     * @return always returns true
     */
    @Override
    public boolean add(T element) {
        Node<T> nd = new Node<>(element);

        if (head == null) {
            head = nd;
        } else {
            Node<T> cur = head;
            while (cur.next != null)
                cur = cur.next;
            cur.next = nd;
        }

        n++;
        return true;
    }

    /**
     * Returns the element stored at the given index.
     *
     * @param index index of the desired element
     * @return element at the given index
     * @throws IndexOutOfBoundsException if index is not in 0..n-1
     */
    @Override
    public T get(int index) {
        if (index < 0 || index >= n)
            throw new IndexOutOfBoundsException();

        Node<T> cur = head;
        for (int i = 0; i < index; i++)
            cur = cur.next;

        return cur.v;
    }

    /**
     * Removes and returns the element at the given index.  
     * If index == 0, removes the head node.
     *
     * @param index index of element to remove
     * @return the removed element
     * @throws IndexOutOfBoundsException if index is not in 0..n-1
     */
    @Override
    public T remove(int index) {
        if (index < 0 || index >= n)
            throw new IndexOutOfBoundsException();

        T out;

        if (index == 0) {
            out = head.v;
            head = head.next;
        } else {
            Node<T> cur = head;
            for (int i = 0; i < index - 1; i++)
                cur = cur.next;

            out = cur.next.v;
            cur.next = cur.next.next;
        }

        n--;
        return out;
    }

    /**
     * Returns the number of elements stored in the list.
     *
     * @return list size
     */
    @Override
    public int size() {
        return n;
    }
}
