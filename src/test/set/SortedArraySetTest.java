package set;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/** Runs the Set contract suite against SortedArraySet. */
public class SortedArraySetTest extends SetTest {

  @Override
  protected Set<Integer> createSet() {
    return new SortedArraySet<>();
  }

  @Test
  public void iteratorYieldsAscendingOrder() {
    set.add(3);
    set.add(1);
    set.add(2);
    set.add(5);
    set.add(4);
    List<Integer> seen = new ArrayList<>();
    for (Integer x : set) {
      seen.add(x);
    }
    List<Integer> expected = new ArrayList<>();
    expected.add(1);
    expected.add(2);
    expected.add(3);
    expected.add(4);
    expected.add(5);
    assertEquals(expected, seen);
  }
}
