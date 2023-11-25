package jaskell.util;

/**
 * Just Pair
 * @param item0
 * @param item1
 * @param <T>
 * @param <U>
 */
public record Tuple5<T, U, V, W, X>(T item0, U item1, V item2, W item3, X item4) {
    public <R> R uncurry(Function5<T, U, V, W, X, R> functor) throws Exception {
        return functor.apply(item0(), item1(), item2(), item3(), item4());
    }

    public <R> Try<R> tryIt(Function5<T, U, V, W, X, R> functor) throws Exception {
        return functor.tryIt(item0(), item1(), item2(), item3(), item4());
    }

    public <Y> Tuple5<Y, U, V, W, X> item0(Y item) {
        return new Tuple5<>(item, item1(), item2(), item3(), item4());
    }

    public <Y> Tuple5<T, Y, V, W, X> item1(Y item) {
        return new Tuple5<>(item0(), item, item2(), item3(), item4());
    }

    public <Y> Tuple5<T, U, Y, W, X> item2(Y item) {
        return new Tuple5<>(item0(), item1(), item, item3(), item4());
    }

    public <Y> Tuple5<T, U, V, Y, X> item3(Y item) {
        return new Tuple5<>(item0(), item1(), item2(), item, item4());
    }

    public <Y> Tuple5<T, U, V, W, Y> item4(Y item) {
        return new Tuple5<>(item0(), item1(), item2(), item3(), item);
    }

    public <Y> Tuple6<T, U, V, W, X, Y> add(Y item) {
        return new Tuple6<>(item0(), item1(), item2(), item3(), item4(), item);
    }

    public <Y> Try<Tuple6<T, U, V, W, X, Y>> tryAdd(Try<Y> tryItem) {
        return tryItem.map(item -> new Tuple6<>(item0(), item1(), item2(), item3(), item4(), item));
    }

    public static <T, U, V, W, X> Try<Tuple5<T, U, V, W, X>> liftA(Try<T> t0, Try<U> t1, Try<V> t2,
                                                                      Try<W> t3, Try<X> t4) {
        return Try.joinMap5(t0, t1, t2, t3, t4, Tuple5::new);
    }
}
