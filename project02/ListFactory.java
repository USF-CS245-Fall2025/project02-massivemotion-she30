
public class ListFactory {
    public static <T> List<T> make(String key) {
        if (key == null) key = "arraylist";
        key = key.trim().toLowerCase();
        switch (key) {
            case "arraylist": return new ArrayList<>();
            case "single": return new LinkedList<>();
            case "double": return new DoublyLinkedList<>();
            case "dummyhead": return new DummyHeadLinkedList<>();
            default:
                System.out.println("[WARN] Unknown list='" + key + "', fallback to arraylist");
                return new ArrayList<>();
        }
    }
}
