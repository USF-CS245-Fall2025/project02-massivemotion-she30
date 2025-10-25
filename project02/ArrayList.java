
public class ArrayList<T> implements List<T> {
    private Object[] data;
    private int n;

    public ArrayList() { this(10); }
    public ArrayList(int cap) {
        if (cap < 1) cap = 10;
        data = new Object[cap];
        n = 0;
    }

    private void growIfNeeded() {
        if (n >= data.length) {
            Object[] nd = new Object[Math.max(1, data.length * 2)];
            for (int i = 0; i < n; i++) nd[i] = data[i];
            data = nd;
        }
    }

    private void checkIndexInclusive(int idx) {
        if (idx < 0 || idx > n) throw new IndexOutOfBoundsException("index="+idx);
    }
    private void checkIndexExclusive(int idx) {
        if (idx < 0 || idx >= n) throw new IndexOutOfBoundsException("index="+idx);
    }

    @Override
    public void add(int index, T element) {
        checkIndexInclusive(index);
        growIfNeeded();
        for (int i = n; i > index; i--) data[i] = data[i-1];
        data[index] = element;
        n++;
    }

    @Override
    public boolean add(T element) {
        growIfNeeded();
        data[n++] = element;
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T get(int index) {
        checkIndexExclusive(index);
        return (T) data[index];
    }

    @SuppressWarnings("unchecked")
    @Override
    public T remove(int index) {
        checkIndexExclusive(index);
        T old = (T) data[index];
        for (int i = index; i < n - 1; i++) data[i] = data[i+1];
        data[--n] = null;
        return old;
    }

    @Override
    public int size() { return n; }
}
