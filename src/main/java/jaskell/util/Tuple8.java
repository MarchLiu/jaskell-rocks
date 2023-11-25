package jaskell.util;

/**
 * Just Pair
 *
 * @param item0
 * @param item1
 * @param <T>
 * @param <U>
 */
public record Tuple8<S, T, U, V, W, X, Y, Z>(S item0, T item1, U item2, V item3, W item4, X item5, Y item6, Z item7) {
    public <R> R uncurry(Function8<S, T, U, V, W, X, Y, Z, R> functor) throws Exception {
        return functor.apply(item0(), item1(), item2(), item3(), item4(), item5(), item6(), item7());
    }

    public <R> Try<R> tryIt(Function8<S, T, U, V, W, X, Y, Z, R> functor) throws Exception {
        return functor.tryIt(item0(), item1(), item2(), item3(), item4(), item5(), item6(), item7());
    }

    public <A> Tuple8<A, T, U, V, W, X, Y, Z> item0(A item) {
        return new Tuple8<>(item, item1(), item2(), item3(), item4(), item5(), item6(), item7());
    }

    public <A> Tuple8<S, A, U, V, W, X, Y, Z> item1(A item) {
        return new Tuple8<>(item0(), item, item2(), item3(), item4(), item5(), item6(), item7());
    }

    public <A> Tuple8<S, T, A, V, W, X, Y, Z> item2(A item) {
        return new Tuple8<>(item0(), item1(), item, item3(), item4(), item5(), item6(), item7());
    }
    public <A> Tuple8<S, T, U, A, W, X, Y, Z> item3(A item) {
        return new Tuple8<>(item0(), item1(), item2(), item, item4(), item5(), item6(), item7());
    }
    public <A> Tuple8<S, T, U, V, A, X, Y, Z> item4(A item) {
        return new Tuple8<>(item0(), item1(), item2(), item3(), item, item5(), item6(), item7());
    }
    public <A> Tuple8<S, T, U, V, W, A, Y, Z> item5(A item) {
        return new Tuple8<>(item0(), item1(), item2(), item3(), item4(), item, item6(), item7());
    }
    public <A> Tuple8<S, T, U, V, W, X, A, Z> item6(A item) {
        return new Tuple8<>(item0(), item1(), item2(), item3(), item4(), item5(), item, item7());
    }
    public <A> Tuple8<A, T, U, V, W, X, Y, Z> item7(A item) {
        return new Tuple8<>(item, item1(), item2(), item3(), item4(), item5(), item6(), item7());
    }

    public static <S, T, U, V, W, X, Y, Z> Try<Tuple8<S, T, U, V, W, X, Y, Z>> liftA(
            Try<S> t0, Try<T> t1, Try<U> t2, Try<V> t3, Try<W> t4, Try<X> t5, Try<Y> t6, Try<Z> t7) {
        return Try.joinMap8(t0, t1, t2, t3, t4, t5, t6, t7, Tuple8::new);
    }
}