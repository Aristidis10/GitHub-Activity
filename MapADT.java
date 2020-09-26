import java.util.NoSuchElementException;

public interface MapADT<KeyType,ValueType> {
    public boolean addRestaurant(KeyType key, ValueType value);
    public ValueType get(KeyType key) throws NoSuchElementException;
    public int size();
    public boolean containsKey(KeyType key);
    public ValueType removeRestaurant(KeyType key);
    public void clear();
}
