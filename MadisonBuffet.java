// --== CS400 File Header Information ==--
// Name: Aristidis Giannopoulos
// Email: agiannopoulo@wisc.edu
// Team: NC
// TA: Daniel Finer
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * 
 * @author Aristidis
 *
 * @param <String>
 * @param <Double>
 */
public class MadisonBuffet implements MapADT<String, Double> {
  final private double MAX_CAP = 0.8;
  private int capacity = 0;
  private int size = 0;
  public LinkedList<LinkedNode>[] hashTable;
  private int keyP; 

  /**
   * Constructor that initializes default values
   * 
   * @return
   */
  @SuppressWarnings("unchecked")
  public MadisonBuffet() {
    capacity = 10;
    size = 0;
    hashTable = new LinkedList[capacity];
    for (int i = 0; i < capacity; i++) {
      hashTable[i] = null;
    }
  }

  /**
   * Gets the index for the array at for a specific key.
   * 
   * @param key
   * @return the index for a specific key
   */
  private int getIndex(String key) {
    int hash = Math.abs(key.hashCode());
    return hash % capacity;
  }

  /**
   * Gets the index for an array with ranked indexes based on rating
   * 
   * @param key
   * @return the index for a specific rating
   */
  private int rankIndex(Double key) {
    double index = key * 10;
    return (int) index;
  }


  /**
   * Adds an element to the HashTable with a key and a value
   * 
   * @param key
   * @param value
   * @return true if element is added, false otherwise
   */
  @SuppressWarnings({"unchecked"})
  @Override
  public boolean put(String key, Double value) throws NoSuchElementException {
    int index = getIndex(key);
    LinkedList<LinkedNode> tempList = hashTable[index];
    LinkedList<LinkedNode>[] tempArray;

    if (tempList != null && containsKey(key)) {
      return false;
    }
    if (tempList != null && !containsKey(key)) {
      tempList.add(new LinkedNode(key, value));
      hashTable[index] = tempList;
      size++;
      if (size >= (MAX_CAP * capacity)) {
        tempArray = new LinkedList[capacity * 2];
        for (int i = 0; i < capacity; i++) {
          if (hashTable[i] != null) {
            for (LinkedNode newNode : hashTable[i]) {

              int tempIndex = Math.abs(newNode.name.hashCode()) % (capacity * 2);

              if (tempArray[tempIndex] == null) {
                tempArray[tempIndex] = new LinkedList<LinkedNode>();
              }
              tempArray[tempIndex].add(new LinkedNode(newNode.name, newNode.rating));
            }
          }
        }
        capacity = capacity * 2;
        hashTable = tempArray;
      }
      return true;
    } else {
      tempList = new LinkedList();
      tempList.add(new LinkedNode(key, value));
      hashTable[index] = tempList;
      size++;
      return true;
    }
  }



  /**
   * Gets the value at a specific key
   * 
   * @param key
   * @return value at a specific key
   */
  @SuppressWarnings("unchecked")
  @Override
  public Double get(String key) throws NoSuchElementException {
    int index = getIndex(key);
    LinkedList<LinkedNode> iterateList = hashTable[index];
    if (iterateList == null) {
      throw new NoSuchElementException("Null List");
    }
    for (int i = 0; i < iterateList.size(); i++) {
      if (containsKey(key)) {
        return (Double) iterateList.get(keyP).rating;
      }
    }
    throw new NoSuchElementException("No key");
  }


  /**
   * Gets the size of the HashTable
   * 
   * @return size
   */
  @Override
  public int size() {
    return size;
  }


  /**
   * Checks if the HashTable contains a specific key.
   * 
   * @param key
   * @return true if the key is in the HashMap
   */
  @Override
  public boolean containsKey(String key) {
    int index = getIndex(key);
    LinkedList<LinkedNode> iterateList = hashTable[index];
    if (iterateList == null) {
      return false;
    }
    for (int i = 0; i < iterateList.size(); i++) {
      if (iterateList.get(i).name.equals(key)) {
        keyP = i;
        return true;
      }
    }
    return false;
  }

  /**
   * Removes a keypair for the HashMap
   * 
   * @param key
   * @returns the ValueType for the removed key
   */
  @SuppressWarnings("unchecked")
  public Double remove(String key) {
    int index = getIndex(key);
    LinkedList<LinkedNode> iterateList = hashTable[index];
    Double remove = null;
    if (iterateList == null) {
      return remove;
    }
    for (int i = 0; i < iterateList.size(); i++) {
      if (iterateList.get(i).name.equals(key)) {
        remove = (Double) iterateList.get(i).rating;
        iterateList.remove(i);
        hashTable[index] = iterateList;
        --size;
        return remove;
      }
    }
    return remove;
  }

  /**
   * Clears the hashmap
   * 
   * @return
   */
  public void clear() {
    for (int i = 0; i < size; ++i) {
      hashTable[i] = null;
    }
  }

  /**
   * Creates a string with all the restaurants and their ratings
   * 
   * @return the string
   */
  public String showAll() {
    String output = "";
    for (int i = 0; i < capacity; ++i) {
      if (hashTable[i] == null) {
        continue;
      } else {
        for (int j = 0; j < hashTable[i].size(); ++j) {
          output = output + hashTable[i].get(j).name + ": " + hashTable[i].get(j).rating + "\n";
        }
      }
    }
    return output;
  }

  /**
   * Checks through the hashtable if a given string name matches a given string name
   * 
   * @param name
   * @return if found returns the restaurant and rating otherwise returns "No Such Restaurant Found"
   */
  public String showName(String name) {
    String output = "";
    for (int i = 0; i < capacity; ++i) {
      if (hashTable[i] == null) {
        continue;
      } else {
        for (int j = 0; j < hashTable[i].size(); ++j) {
          if (hashTable[i].get(j).name.equalsIgnoreCase(name)) {
            output = hashTable[i].get(j).name + ": " + hashTable[i].get(j).rating + "\n";
            return output;
          }
        }
      }
    }
    return "No Such Restaurant Found";
  }


  @SuppressWarnings("unchecked")
  public String rankRating() {
    String output = "";
    LinkedList<LinkedNode>[] rankHashTable = new LinkedList[51];
    for (int i = 0; i < capacity; ++i) {
      if (hashTable[i] == null) {
        continue;
      } else {
        for (int j = 0; j < hashTable[i].size(); ++j) {
          if (rankHashTable[rankIndex(hashTable[i].get(j).rating)] == null) {
            LinkedList<LinkedNode> tempList = new LinkedList<LinkedNode>();
            tempList.add(new LinkedNode(hashTable[i].get(j).name, hashTable[i].get(j).rating));
            rankHashTable[rankIndex(hashTable[i].get(j).rating)] = tempList;
          } else {
            rankHashTable[rankIndex(hashTable[i].get(j).rating)].add(new LinkedNode(hashTable[i].get(j).name, hashTable[i].get(j).rating));
          }
        }
      }
    }
    for (int i = 50; i >=0; --i) {
      if (rankHashTable[i] == null) {
        continue;
      } else {
        for (int j = 0; j < rankHashTable[i].size(); ++j) {
          output =
              output + rankHashTable[i].get(j).name + ": " + rankHashTable[i].get(j).rating + "\n";
        }
      }
    }
    return output;
  }
}
