package set;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.fail;

/** Runs the Set contract suite against TransposeArraySet. */
public class TransposeArraySetTest extends SetTest {

  @Override
  protected Set<Integer> createSet() {
    return new TransposeArraySet<>();
  }

  @Test
  public void nextAfterReorderingContainsThrows() {
    set.add(1);
    set.add(2);
    Iterator<Integer> it = set.iterator();
    it.next();
    set.contains(2);
    try {
      it.next();
      fail("expected ConcurrentModificationException after contains reordered the set");
    } catch (ConcurrentModificationException e) {
      return;
    }
  }
}
