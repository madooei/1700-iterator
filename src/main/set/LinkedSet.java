package set;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A node-backed implementation of the Set ADT.
 *
 * @param <T> the type of elements in this set.
 */
public class LinkedSet<T> implements Set<T> {

  private Node<T> head;  // the first node, or null when empty
  private int size;      // how many items the set holds
  private int modCount;  // counts structural changes, so iterators can fail fast

  private static class Node<T> {
    T value;
    Node<T> next;

    Node(T value) {
      this.value = value;
    }
  }

  public LinkedSet() {
    head = null;  // the set starts empty
    size = 0;
    modCount = 0;
  }

  @Override
  public boolean contains(T item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }
    for (Node<T> node = head; node != null; node = node.next) {
      if (node.value.equals(item)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean add(T item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }
    if (contains(item)) {
      return false;  // already present; a set holds no duplicates
    }
    Node<T> newNode = new Node<>(item);
    newNode.next = head;  // insert at the head
    head = newNode;
    size++;
    modCount++;           // a real change; iterators in progress are now stale
    return true;
  }

  @Override
  public boolean remove(T item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }
    Node<T> previous = null;
    for (Node<T> node = head; node != null; node = node.next) {
      if (node.value.equals(item)) {
        if (previous == null) {
          head = node.next;  // the match was the first node
        } else {
          previous.next = node.next;  // unlink the match
        }
        size--;
        modCount++;  // a real change; iterators in progress are now stale
        return true;
      }
      previous = node;
    }
    return false;  // not found; nothing changed
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public Iterator<T> iterator() {
    return new LinkedSetIterator();
  }

  // Walks the node chain with a current-node field, starting at head.
  private class LinkedSetIterator implements Iterator<T> {
    private Node<T> current = head;            // node holding the next element to return
    private int expectedModCount = modCount;   // the set's count when this walk began

    @Override
    public boolean hasNext() {
      return current != null;
    }

    @Override
    public T next() {
      if (modCount != expectedModCount) {
        throw new ConcurrentModificationException();
      }
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      T element = current.value;   // save before advancing: once current moves on,
      current = current.next;      // the node we were on is out of reach
      return element;
    }
  }
}
