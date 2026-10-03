package set;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An array-backed implementation of the Set ADT.
 *
 * @param <T> the type of elements in this set.
 */
public class ArraySet<T> implements Set<T> {

  private T[] arr;       // the backing array
  private int size;      // how many items the set holds
  private int modCount;  // counts structural changes, so iterators can fail fast

  // arr only ever holds T, so the cast is safe.
  @SuppressWarnings("unchecked")
  public ArraySet() {
    arr = (T[]) new Object[10];  // start with room for 10 items
    size = 0;                    // the set starts empty
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
    if (indexOf(item) != -1) {
      return false;    // already present; a set holds no duplicates
    }
    if (size == arr.length) {
      grow();          // grow when the array is full
    }
    arr[size] = item;  // append at the end
    size++;
    modCount++;        // a real change; iterators in progress are now stale
    return true;
  }

  @Override
  public boolean remove(T item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }
    int i = indexOf(item);
    if (i == -1) {
      return false;          // not present; nothing changed
    }
    arr[i] = arr[size - 1];  // move the last item into the gap
    arr[size - 1] = null;    // clear the vacated slot
    size--;
    modCount++;              // a real change; iterators in progress are now stale
    return true;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public Iterator<T> iterator() {
    // TODO: Implement me
    throw new UnsupportedOperationException("TODO: Implement me");
  }

  // Returns the index of item, or -1 if item is not found.
  private int indexOf(T item) {
    for (int i = 0; i < size; i++) {
      if (arr[i].equals(item)) {
        return i;
      }
    }
    return -1;
  }

  // Doubles the capacity. Same cast rationale as the constructor.
  @SuppressWarnings("unchecked")
  private void grow() {
    T[] bigger = (T[]) new Object[arr.length * 2];
    for (int i = 0; i < size; i++) {
      bigger[i] = arr[i];
    }
    arr = bigger;
  }

  // Walks the filled prefix of the backing array with a cursor index.
  private class ArraySetIterator implements Iterator<T> {
    private int cursor = 0;                    // index of the next element to return
    private int expectedModCount = modCount;   // the set's count when this walk began

    @Override
    public boolean hasNext() {
      // TODO: Implement me
      throw new UnsupportedOperationException("TODO: Implement me");
    }

    @Override
    public T next() {
      // TODO: Implement me
      throw new UnsupportedOperationException("TODO: Implement me");
    }
  }
}
