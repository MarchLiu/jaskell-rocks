package jaskell.util;

/**
 * Just Pair
 * @param item0
 * @param item1
 * @param <T>
 * @param <U>
 */
public record Tuple3<T, U, V>(T item0, U item1, V item2) {
    public <R> R uncurry(TriFunction<T, U, V, R> functor) throws Exception {
        return functor.apply(item0(), item1(), item2());
    }

    public <R> Try<R> tryIt(TriFunction<T, U, V, R> functor) throws Exception {
        return functor.tryIt(item0(), item1(), item2());
    }

    public <W> Tuple3<W, U, V> item0(W item) {
        return new Tuple3<>(item, item1(), item2());
    }

    public <W> Tuple3<T, W, V> item1(W item) {
        return new Tuple3<>(item0(), item, item2());
    }

    public <W> Tuple3<T, U, W> item2(W item) {
        return new Tuple3<>(item0(), item1(), item);
    }

    public static <T, U, V> Try<Tuple3<T, U, V>> liftA(Try<T> t0, Try<U> t1, Try<V> t2) {
        return Try.joinMap3(t0, t1, t2, Tuple3::new);
    }

    public <W> Tuple4<T, U, V, W> add(W item) {
        return new Tuple4<>(item0(), item1(), item2(), item);
    }

    public <W> Try<Tuple4<T, U, V, W>> tryAdd(Try<W> tryItem) {
        return tryItem.map(item -> new Tuple4<>(item0(), item1(), item2(), item));
    }
}
