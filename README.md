# Iterators

The `Set` ADT from the Set chapter, made iterable. `Set` now extends `Iterable`, and each of the two implementations supplies an `iterator()` written as an inner class. The iterators fail fast when the set changes during an iteration. A JUnit suite checks both implementations against the same contract, iterator included.

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
        Main.java                       # demo entry point (the username example)
    test/
      set/
        SetTest.java                    # abstract: the Set contract suite
        LinkedSetTest.java              # runs the suite against LinkedSet
        ArraySetTest.java               # runs the suite against ArraySet
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
- `set.Main` — a runnable demo of the username example, run on both implementations. It lists the names with an enhanced `for` loop.
- `set.SetTest` — the abstract contract suite. It checks `add`, `remove`, `contains`, `size`, and the iterator. The iterator tests check which elements come out, not their order, because a set has no order. They also check that an iteration fails fast after an `add` or a `remove`.
- `set.LinkedSetTest`, `set.ArraySetTest` — each runs the contract suite against one implementation.

## Failing fast

Each set counts its changes in a `modCount` field. `add` and `remove` increase it when they change the set. An iterator records `modCount` when it is created, and `next()` throws `ConcurrentModificationException` if the set has changed since then.
