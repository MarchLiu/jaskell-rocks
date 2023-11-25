package jaskell.util;

/**
 * Just Pair
 *
 * @param item0
 * @param item1
 * @param <T>
 * @param <U>
 */
public record Tuple6<T, U, V, W, X, Y>(T item0, U item1, V item2, W item3, X item4, Y item5) {
    public <R> R uncurry(Function6<T, U, V, W, X, Y, R> functor) throws Exception {
        return functor.apply(item0(), item1(), item2(), item3(), item4(), item5());
    }

    public <R> Try<R> tryIt(Function6<T, U, V, W, X, Y, R> functor) throws Exception {
        return functor.tryIt(item0(), item1(), item2(), item3(), item4(), item5());
    }

    public <Z> Tuple6<Z, U, V, W, X, Y> item0(Z item) {
        return new Tuple6<>(item, item1(), item2(), item3(), item4(), item5());
    }

    public <Z> Tuple6<T, Z, V, W, X, Y> item1(Z item) {
        return new Tuple6<>(item0(), item, item2(), item3(), item4(), item5());
    }
    public <Z> Tuple6<T, U, Z, W, X, Y> item2(Z item) {
        return new Tuple6<>(item0(), item1(), item, item3(), item4(), item5());
    }
    public <Z> Tuple6<T, U, V, Z, X, Y> item3(Z item) {
        return new Tuple6<>(item0(), item1(), item2(), item, item4(), item5());
    }
    public <Z> Tuple6<T, U, V, W, Z, Y> item4(Z item) {
        return new Tuple6<>(item0(), item1(), item2(), item3(), item, item5());
    }
    public <Z> Tuple6<T, U, V, W, X, Z> item5(Z item) {
        return new Tuple6<>(item0(), item1(), item2(), item3(), item4(), item);
    }
    public <Z> Tuple7<T, U, V, W, X, Y, Z> add(Z item) {
        return new Tuple7<>(item0(), item1(), item2(), item3(), item4(), item5(), item);
    }
    public <Z> Try<Tuple7<T, U, V, W, X, Y, Z>> tryAdd(Try<Z> tryItem) {
        return tryItem.map(item -> new Tuple7<>(item0(), item1(), item2(), item3(), item4(), item5(), item));
    }

    public static <T, U, V, W, X, Y> Try<Tuple6<T, U, V, W, X, Y>> liftA(Try<T> t0, Try<U> t1,
                                                                         Try<V> t2, Try<W> t3,
                                                                         Try<X> t4, Try<Y> t5) {
        return Try.joinMap6(t0, t1, t2, t3, t4, t5, Tuple6::new);
    }
}
