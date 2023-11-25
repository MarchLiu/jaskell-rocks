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

    public <X> Tuple4<X, U, V, W> item0(X item) {
        return new Tuple4<>(item, item1(), item2(), item3());
    }

    public <X> Tuple4<T, X, V, W> item1(X item) {
        return new Tuple4<>(item0(), item, item2(), item3());
    }

    public <X> Tuple4<T, U, X, W> item2(X item) {
        return new Tuple4<>(item0(), item1(), item, item3());
    }

    public <X> Tuple4<T, U, V, X> item3(X item) {
        return new Tuple4<>(item0(), item1(), item2(), item);
    }

    public <X> Tuple5<T, U, V, W, X> add(X item) {
        return new Tuple5<>(item0(), item1(), item2(), item3(), item);
    }

    public <X> Try<Tuple5<T, U, V, W, X>> tryAdd(Try<X> tryItem) {
        return tryItem.map(item -> new Tuple5<>(item0(), item1(), item2(), item3(), item));
    }

    public static <T, U, V, W> Try<Tuple4<T, U, V, W>> liftA(Try<T> t0, Try<U> t1, Try<V> t2, Try<W> t3) {
        return Try.joinMap4(t0, t1, t2, t3, Tuple4::new);
    }
}
