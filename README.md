# Iterators

The `Set` ADT from the Set chapter, made iterable. `Set` now extends `Iterable`, and each of the five implementations supplies an `iterator()` written as an inner class. The iterators fail fast when the set changes during an iteration. A JUnit suite checks all five implementations against the same contract, iterator included.

## Prerequisites

- JDK 17+
- The JUnit jar is already vendored in `lib/`; there is nothing to download.

## Repository layout

```plaintext
code/
  README.md
  .gitignore
  lib/
    junit-platform-console-standalone-6.1.0.jar
  src/
    main/
      set/
        Set.java                        # the Set ADT contract, now extends Iterable<T>
        LinkedSet.java                  # node-backed Set
        ArraySet.java                   # array-backed Set
        MoveToFrontLinkedSet.java       # LinkedSet with the move-to-front heuristic
        TransposeArraySet.java          # ArraySet with the transpose heuristic
        SortedArraySet.java             # sorted-array Set
        Main.java                       # demo entry point (the username example)
    test/
      set/
        SetTest.java                    # abstract: the Set contract suite
        LinkedSetTest.java              # runs the suite against LinkedSet
        ArraySetTest.java               # runs the suite against ArraySet
        MoveToFrontLinkedSetTest.java   # runs the suite against MoveToFrontLinkedSet
        TransposeArraySetTest.java      # runs the suite against TransposeArraySet
        SortedArraySetTest.java         # runs the suite against SortedArraySet
  scripts/
    run.sh                              # compile and run the Set demo (set.Main)
    test.sh                             # compile and run every JUnit test
```

## How to compile and run

- `scripts/test.sh` — compiles everything and runs the full JUnit suite.
- `scripts/test.sh set.ArraySetTest` — compiles everything and runs only that test class. Use this while you are working on one class and the others are still empty.
- `scripts/run.sh` — compiles everything and runs the `Main` demo.

## What's here

- `set.Set<T>` — the Set contract: `add`, `remove`, `contains`, and `size`. It extends `Iterable<T>`, so every set also has `iterator()`.
- `set.LinkedSet<T>` — the node-backed implementation. Its iterator follows the nodes from `head`.
- `set.ArraySet<T>` — the array-backed implementation. Its iterator moves a cursor index over the filled part of the array.
- `set.MoveToFrontLinkedSet<T>` — a `LinkedSet` whose `contains` moves the item it finds to the head of the list.
- `set.TransposeArraySet<T>` — an `ArraySet` whose `contains` swaps the item it finds one slot toward the front.
- `set.SortedArraySet<T>` — the sorted-array implementation, whose `contains` uses binary search. Its iterator returns the elements in ascending order.
- `set.Main` — a runnable demo of the username example, run on all five implementations. It lists the names with an enhanced `for` loop.
- `set.SetTest` — the abstract contract suite. It checks `add`, `remove`, `contains`, `size`, and the iterator. The iterator tests check which elements come out, not their order, because a set has no order. They also check that an iteration fails fast after an `add` or a `remove`.
- `set.SortedArraySetTest` — runs the contract suite against `SortedArraySet`, and also checks that its iterator returns the elements in ascending order.
- `set.MoveToFrontLinkedSetTest`, `set.TransposeArraySetTest` — each runs the contract suite against one heuristic class, and also checks that an iteration fails fast after a `contains` that reorders the set.
- `set.LinkedSetTest`, `set.ArraySetTest` — each runs the contract suite against one implementation.

## Failing fast

Each set counts its changes in a `modCount` field. `add` and `remove` increase it when they change the set, and so does the `contains` of the two heuristic classes when it reorders the set. An iterator records `modCount` when it is created, and `next()` throws `ConcurrentModificationException` if the set has changed since then.
