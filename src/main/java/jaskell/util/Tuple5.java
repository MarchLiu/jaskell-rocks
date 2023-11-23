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

    public static <T, U, V, W, X> Try<Tuple5<T, U, V, W, X>> liftA(Try<T> t0, Try<U> t1, Try<V> t2,
                                                                      Try<W> t3, Try<X> t4) {
        return Try.joinMap5(t0, t1, t2, t3, t4, Tuple5::new);
    }
}
