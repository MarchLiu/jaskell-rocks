package jaskell.util;

/**
 * Just Pair
 *
 * @param item0
 * @param item1
 * @param <T>
 * @param <U>
 */
public record Tuple2<T, U>(T item0, U item1) {
    public <R> R uncurry(BiFunction<T, U, R> functor) throws Exception {
        return functor.apply(item0(), item1());
    }

    public <R> Try<R> tryIt(BiFunction<T, U, R> functor) throws Exception {
        return functor.tryIt(item0(), item1());
    }

    public static <T, U> Try<Tuple2<T, U>> liftA(Try<T> t0, Try<U> t1) {
        return Try.joinMap(t0, t1, Tuple2::new);
    }
}
