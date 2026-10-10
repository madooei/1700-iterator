package set;

/** A demo of the Set ADT: a mailing list that keeps each subscriber's email address once. */
public final class Main {

  private Main() {
    // This class should not be instantiated!
  }

  public static void main(String[] args) {
    System.out.println("LinkedSet:");
    runMailingList(new LinkedSet<>());
    System.out.println("ArraySet:");
    runMailingList(new ArraySet<>());
  }

  // Subscribes a few addresses, including one twice, sends a newsletter, then unsubscribes one.
  private static void runMailingList(Set<String> subscribers) {
    System.out.println("  subscribe alice@jhu.edu: " + subscribers.add("alice@jhu.edu"));
    System.out.println("  subscribe bob@gmail.com: " + subscribers.add("bob@gmail.com"));
    System.out.println("  subscribe alice@jhu.edu again: " + subscribers.add("alice@jhu.edu"));
    System.out.println("  subscribers: " + subscribers.size());
    System.out.println("  is bob@gmail.com subscribed? " + subscribers.contains("bob@gmail.com"));
    System.out.println("  is carol@jhu.edu subscribed? " + subscribers.contains("carol@jhu.edu"));
    for (String email : subscribers) {
      sendNewsletter(email);
    }
    System.out.println("  bob@gmail.com unsubscribes: " + subscribers.remove("bob@gmail.com"));
    System.out.println("  is bob@gmail.com subscribed? " + subscribers.contains("bob@gmail.com"));
    System.out.println("  subscribe bob@gmail.com: " + subscribers.add("bob@gmail.com"));
  }

  // Stands in for sending an email: prints the address the newsletter goes to.
  private static void sendNewsletter(String email) {
    System.out.println("  send newsletter to " + email);
  }
}
