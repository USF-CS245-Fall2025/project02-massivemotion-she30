
public class DoublyLinkedList<T> implements List<T> {
    private static class Node<T> { T v; Node<T> next, prev; Node(T v){this.v=v;} }
    private Node<T> head, tail;
    private int n;

    @Override
    public void add(int index, T element) {
        if (index < 0 || index > n) throw new IndexOutOfBoundsException();
        if (index == n) { add(element); return; }
        Node<T> nd = new Node<>(element);
        Node<T> cur = nodeAt(index);
        nd.prev = cur.prev; nd.next = cur;
        if (cur.prev != null) cur.prev.next = nd; else head = nd;
        cur.prev = nd;
        n++;
    }

    @Override
    public boolean add(T element) {
        Node<T> nd = new Node<>(element);
        if (head == null) head = tail = nd;
        else { tail.next = nd; nd.prev = tail; tail = nd; }
        n++; return true;
    }

    @Override
    public T get(int index) { return nodeAt(index).v; }

    @Override
    public T remove(int index) {
        Node<T> cur = nodeAt(index);
        if (cur.prev != null) cur.prev.next = cur.next; else head = cur.next;
        if (cur.next != null) cur.next.prev = cur.prev; else tail = cur.prev;
        n--; return cur.v;
    }

    @Override
    public int size() { return n; }

    private Node<T> nodeAt(int index) {
        if (index < 0 || index >= n) throw new IndexOutOfBoundsException();
        Node<T> cur;
        if (index < n/2) { cur = head; for (int i=0;i<index;i++) cur = cur.next; }
        else { cur = tail; for (int i=n-1;i>index;i--) cur = cur.prev; }
        return cur;
    }
}
