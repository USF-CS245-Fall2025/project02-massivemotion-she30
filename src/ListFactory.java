/**
 * Factory class used in CS245 Project 2 for creating different
 * implementations of the custom List<T> interface.
 * <p>
 * The type of list created is determined by a string key from
 * the configuration file (e.g., "arraylist", "single", "double", "dummyhead").
 * If an unknown key is provided, the method falls back to ArrayList.
 */
public class ListFactory {

    /**
     * Creates and returns a specific List<T> implementation based on the key.
     *
     * Valid keys:
     * <ul>
     *   <li>"arraylist"  → ArrayList</li>
     *   <li>"single"     → LinkedList</li>
     *   <li>"double"     → DoublyLinkedList</li>
     *   <li>"dummyhead"  → DummyHeadLinkedList</li>
     * </ul>
     *
     * If the key is null or unknown, the factory defaults to ArrayList
     * and prints a warning message.
     *
     * @param key the name of the list implementation to create
     * @param <T> the element type of the list
     * @return a new List<T> instance based on the provided key
     */
    public static <T> List<T> make(String key) {
        if (key == null) key = "arraylist";
        key = key.trim().toLowerCase();

        switch (key) {
            case "arraylist": 
                return new ArrayList<>();

            case "single": 
                return new LinkedList<>();

            case "double": 
                return new DoublyLinkedList<>();

            case "dummyhead": 
                return new DummyHeadLinkedList<>();

            default:
                System.out.println("[WARN] Unknown list='" + key + "', fallback to arraylist");
                return new ArrayList<>();
        }
    }
}
