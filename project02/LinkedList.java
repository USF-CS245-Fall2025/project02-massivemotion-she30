
public class LinkedList<T> implements List<T> {
    private static class Node<T> { T v; Node<T> next; Node(T v){this.v=v;} }
    private Node<T> head; 
    private int n;

    @Override
    public void add(int index, T element) {
        if (index < 0 || index > n) throw new IndexOutOfBoundsException();
        Node<T> nd = new Node<>(element);
        if (index == 0) { nd.next = head; head = nd; }
        else {
            Node<T> cur = head;
            for (int i = 0; i < index - 1; i++) cur = cur.next;
            nd.next = cur.next; cur.next = nd;
        }
        n++;
    }

    @Override
    public boolean add(T element) {
        Node<T> nd = new Node<>(element);
        if (head == null) head = nd;
        else {
            Node<T> cur = head;
            while (cur.next != null) cur = cur.next;
            cur.next = nd;
        }
        n++;
        return true;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= n) throw new IndexOutOfBoundsException();
        Node<T> cur = head;
        for (int i = 0; i < index; i++) cur = cur.next;
        return cur.v;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= n) throw new IndexOutOfBoundsException();
        T out;
        if (index == 0) { out = head.v; head = head.next; }
        else {
            Node<T> cur = head;
            for (int i = 0; i < index - 1; i++) cur = cur.next;
            out = cur.next.v;
            cur.next = cur.next.next;
        }
        n--;
        return out;
    }

    @Override
    public int size() { return n; }
}
