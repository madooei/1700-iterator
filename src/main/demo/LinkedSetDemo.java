package demo;

import set.LinkedSet;
import set.Set;

/** A demo of LinkedSet: a mailing list that keeps each subscriber's email address once. */
public final class LinkedSetDemo {

  private LinkedSetDemo() {
    // This class should not be instantiated!
  }

  public static void main(String[] args) {
    Set<String> subscribers = new LinkedSet<>();

    // Both addresses are new, so both adds return true.
    System.out.println("subscribe alice@jhu.edu: " + subscribers.add("alice@jhu.edu"));
    System.out.println("subscribe bob@gmail.com: " + subscribers.add("bob@gmail.com"));

    // alice@jhu.edu is already on the list, so this add returns false and the size stays 2.
    System.out.println("subscribe alice@jhu.edu again: " + subscribers.add("alice@jhu.edu"));
    System.out.println("subscribers: " + subscribers.size());

    // bob@gmail.com is on the list and carol@jhu.edu is not. Expect true, then false.
    System.out.println("is bob@gmail.com subscribed? " + subscribers.contains("bob@gmail.com"));
    System.out.println("is carol@jhu.edu subscribed? " + subscribers.contains("carol@jhu.edu"));

    // The enhanced for loop calls iterator(). New items go at the head, so expect bob@gmail.com, then alice@jhu.edu.
    for (String email : subscribers) {
      System.out.println("send newsletter to " + email);
    }

    // Once bob@gmail.com unsubscribes, the address can subscribe again. Expect true, false, true.
    System.out.println("bob@gmail.com unsubscribes: " + subscribers.remove("bob@gmail.com"));
    System.out.println("is bob@gmail.com subscribed? " + subscribers.contains("bob@gmail.com"));
    System.out.println("subscribe bob@gmail.com: " + subscribers.add("bob@gmail.com"));
  }
}
