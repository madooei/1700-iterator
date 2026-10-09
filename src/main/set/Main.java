package set;

/** A demo of the Set ADT: a website keeping track of the usernames that are taken. */
public final class Main {

  private Main() {
    // This class should not be instantiated!
  }

  public static void main(String[] args) {
    System.out.println("LinkedSet:");
    registerUsers(new LinkedSet<>());
    System.out.println("ArraySet:");
    registerUsers(new ArraySet<>());
  }

  // Registers a few usernames, including one that is already taken, then frees one.
  private static void registerUsers(Set<String> taken) {
    System.out.println("  register alice: " + taken.add("alice"));
    System.out.println("  register bob: " + taken.add("bob"));
    System.out.println("  register alice again: " + taken.add("alice"));
    System.out.println("  names taken: " + taken.size());
    System.out.println("  is bob taken? " + taken.contains("bob"));
    System.out.println("  is carol taken? " + taken.contains("carol"));
    System.out.print("  all names:");
    for (String name : taken) {
      System.out.print(" " + name);
    }
    System.out.println();
    System.out.println("  bob deletes the account: " + taken.remove("bob"));
    System.out.println("  is bob taken? " + taken.contains("bob"));
    System.out.println("  register bob: " + taken.add("bob"));
  }
}
