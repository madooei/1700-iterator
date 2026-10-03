package set;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.fail;

/** Runs the Set contract suite against MoveToFrontLinkedSet. */
public class MoveToFrontLinkedSetTest extends SetTest {

  @Override
  protected Set<Integer> createSet() {
    return new MoveToFrontLinkedSet<>();
  }

  @Test
  public void nextAfterReorderingContainsThrows() {
    set.add(1);
    set.add(2);
    Iterator<Integer> it = set.iterator();
    it.next();
    set.contains(1);
    try {
      it.next();
      fail("expected ConcurrentModificationException after contains reordered the set");
    } catch (ConcurrentModificationException e) {
      return;
    }
  }
}
