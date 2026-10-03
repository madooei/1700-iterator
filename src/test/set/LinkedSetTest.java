package set;

/** Runs the Set contract suite against LinkedSet. */
public class LinkedSetTest extends SetTest {

  @Override
  protected Set<Integer> createSet() {
    return new LinkedSet<>();
  }
}
