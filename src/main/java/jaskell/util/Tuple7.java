package jaskell.util;

/**
 * Just Pair
 * @param item0
 * @param item1
 * @param <T>
 * @param <U>
 */
public record Tuple7<T, U, V, W, X, Y, Z>(T item0, U item1, V item2, W item3, X item4, Y item5, Z item6) {
    public <R> R uncurry(Function7<T, U, V, W, X, Y, Z, R> functor) throws Exception {
        return functor.apply(item0(), item1(), item2(), item3(), item4(), item5(), item6());
    }

    public <R> Try<R> tryIt(Function7<T, U, V, W, X, Y, Z, R> functor) throws Exception {
        return functor.tryIt(item0(), item1(), item2(), item3(), item4(), item5(), item6());
    }

    public static <T, U, V, W, X, Y, Z> Try<Tuple7<T, U, V, W, X, Y, Z>> liftA(
            Try<T> t0, Try<U> t1, Try<V> t2, Try<W> t3, Try<X> t4, Try<Y> t5, Try<Z> t6) {
        return Try.joinMap7(t0, t1, t2, t3, t4, t5, t6, Tuple7::new);
    }
}
