
public class DummyHeadLinkedList<T> implements List<T> {
    private static class Node<T> { T v; Node<T> next; Node(T v){this.v=v;} }
    private final Node<T> head = new Node<>(null); // dummy
    private int n;

    @Override
    public void add(int index, T element) {
        if (index < 0 || index > n) throw new IndexOutOfBoundsException();
        Node<T> prev = head;
        for (int i = 0; i < index; i++) prev = prev.next;
        Node<T> nd = new Node<>(element);
        nd.next = prev.next; prev.next = nd; n++;
    }

    @Override
    public boolean add(T element) { add(n, element); return true; }

    @Override
    public T get(int index) {
        if (index < 0 || index >= n) throw new IndexOutOfBoundsException();
        Node<T> cur = head.next;
        for (int i = 0; i < index; i++) cur = cur.next;
        return cur.v;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= n) throw new IndexOutOfBoundsException();
        Node<T> prev = head;
        for (int i = 0; i < index; i++) prev = prev.next;
        Node<T> del = prev.next; prev.next = del.next; n--; return del.v;
    }

    @Override
    public int size() { return n; }
}
