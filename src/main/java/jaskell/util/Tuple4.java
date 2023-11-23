package jaskell.util;

/**
 * Just Pair
 * @param item0
 * @param item1
 * @param <T>
 * @param <U>
 */
public record Tuple4<T, U, V, W>(T item0, U item1, V item2, W item3) {
    public <R> R uncurry(Function4<T, U, V, W, R> functor) throws Exception {
        return functor.apply(item0(), item1(), item2(), item3());
    }

    public <R> Try<R> tryIt(Function4<T, U, V, W, R> functor) throws Exception {
        return functor.tryIt(item0(), item1(), item2(), item3());
    }

    public static <T, U, V, W> Try<Tuple4<T, U, V, W>> liftA(Try<T> t0, Try<U> t1, Try<V> t2, Try<W> t3) {
        return Try.joinMap4(t0, t1, t2, t3, Tuple4::new);
    }
}
