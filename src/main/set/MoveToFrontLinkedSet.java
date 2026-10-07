package set;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A node-backed implementation of the Set ADT that uses the move-to-front heuristic.
 *
 * @param <T> the type of elements in this set.
 */
public class MoveToFrontLinkedSet<T> implements Set<T> {

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

  public MoveToFrontLinkedSet() {
    head = null;  // the set starts empty
    size = 0;
    modCount = 0;
  }

  @Override
  public boolean contains(T item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }
    Node<T> prev = null;
    Node<T> curr = head;
    while (curr != null) {
      if (item.equals(curr.value)) {
        moveToFront(curr, prev);
        return true;
      }
      prev = curr;
      curr = curr.next;
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
    Node<T> prev = null;
    Node<T> curr = head;
    while (curr != null) {
      if (item.equals(curr.value)) {
        if (prev != null) {
          prev.next = curr.next;
        } else {
          head = head.next;
        }
        size--;
        modCount++;  // a real change; iterators in progress are now stale
        return true;
      }
      prev = curr;
      curr = curr.next;
    }
    return false;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public Iterator<T> iterator() {
    return new MoveToFrontLinkedSetIterator();
  }

  // Moves node to the head of the list. previous is the node before it,
  // or null if node is already the head.
  private void moveToFront(Node<T> node, Node<T> previous) {
    if (previous == null) {
      return;                   // already at the front; nothing to do
    }
    previous.next = node.next;  // unlink the node from its current spot
    node.next = head;           // splice it in at the head
    head = node;
    modCount++;                 // the order changed; iterators in progress are now stale
  }

  // Walks the node chain with a current-node field, starting at head.
  private class MoveToFrontLinkedSetIterator implements Iterator<T> {
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
