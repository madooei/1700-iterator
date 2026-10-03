package set;

/** Runs the Set contract suite against ArraySet. */
public class ArraySetTest extends SetTest {

  @Override
  protected Set<Integer> createSet() {
    return new ArraySet<>();
  }
}
