package set;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A sorted-array implementation of the Set ADT.
 *
 * @param <T> the type of elements in this set.
 */
public class SortedArraySet<T extends Comparable<T>> implements Set<T> {

  private T[] arr;       // the backing array, kept in sorted order
  private int size;      // how many items the set holds
  private int modCount;  // counts structural changes, so iterators can fail fast

  // Comparable[] (not Object[]) because T is bounded by Comparable.
  // arr only ever holds T, so the cast is safe.
  @SuppressWarnings("unchecked")
  public SortedArraySet() {
    arr = (T[]) new Comparable[10];  // start with room for 10 items
    size = 0;                        // the set starts empty
    modCount = 0;
  }

  @Override
  public boolean contains(T item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }
    return indexOf(item) != -1;
  }

  @Override
  public boolean add(T item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }
    int i = insertionPoint(item);
    if (i < size && arr[i].equals(item)) {
      return false;  // already present; a set holds no duplicates
    }
    if (size == arr.length) {
      grow();        // grow when the array is full
    }
    for (int j = size; j > i; j--) {
      arr[j] = arr[j - 1];  // shift larger items right to open a gap
    }
    arr[i] = item;
    size++;
    modCount++;  // a real change; iterators in progress are now stale
    return true;
  }

  @Override
  public boolean remove(T item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }
    int i = indexOf(item);
    if (i == -1) {
      return false;  // not present; nothing changed
    }
    for (int j = i; j < size - 1; j++) {
      arr[j] = arr[j + 1];  // shift left to close the gap
    }
    arr[size - 1] = null;   // clear the vacated slot
    size--;
    modCount++;             // a real change; iterators in progress are now stale
    return true;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public Iterator<T> iterator() {
    return new SortedArraySetIterator();
  }

  // Returns the index of item, or -1 if item is not found.
  private int indexOf(T item) {
    int i = insertionPoint(item);
    if (i < size && arr[i].equals(item)) {
      return i;
    }
    return -1;
  }

  // Index where item sits, or where it would belong if absent (binary search).
  private int insertionPoint(T item) {
    int low = 0;
    int high = size - 1;
    while (low <= high) {
      int mid = low + (high - low) / 2;
      int cmp = arr[mid].compareTo(item);
      if (cmp == 0) {
        return mid;
      } else if (cmp < 0) {
        low = mid + 1;
      } else {
        high = mid - 1;
      }
    }
    return low;
  }

  // Doubles the capacity. Same cast rationale as the constructor.
  @SuppressWarnings("unchecked")
  private void grow() {
    T[] bigger = (T[]) new Comparable[arr.length * 2];
    for (int i = 0; i < size; i++) {
      bigger[i] = arr[i];
    }
    arr = bigger;
  }

  // Walks the sorted backing array with a cursor index, so elements come out in
  // ascending order.
  private class SortedArraySetIterator implements Iterator<T> {
    private int cursor = 0;                    // index of the next element to return
    private int expectedModCount = modCount;   // the set's count when this walk began

    @Override
    public boolean hasNext() {
      return cursor < size;
    }

    @Override
    public T next() {
      if (modCount != expectedModCount) {
        throw new ConcurrentModificationException();
      }
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      return arr[cursor++];
    }
  }
}
