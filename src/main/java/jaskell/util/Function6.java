package jaskell.util;

import java.util.Objects;


/**
 * @param <T> type of arg1
 * @param <U> type of arg2
 * @param <V> type of arg3
 * @param <W> type of arg4
 * @param <X> type of arg5
 * @param <Y> type of arg6
 * @param <R> type of result
 */
public interface Function6<T, U, V, W, X, Y, R> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @return the function result
     */
    R apply(T t, U u, V v, W w, X x, Y y) throws Exception;

    /**
     * Returns a composed function that first applies this function to
     * its input, and then applies the {@code after} function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <O>   the type of output of the {@code after} function, and of the
     *              composed function
     * @param after the function to apply after this function is applied
     * @return a composed function that first applies this function and then
     * applies the {@code after} function
     * @throws NullPointerException if after is null
     */
    default <O> Function6<T, U, V, W, X, Y, O> andThen(Function<? super R, ? extends O> after) {
        Objects.requireNonNull(after);
        return (T t, U u, V v, W w, X x, Y y) -> after.apply(apply(t, u, v, w, x, y));
    }

    default Try<R> collect(T t, U u, V v, W w, X x, Y y) {
        try {
            return Try.success(apply(t, u, v, w, x, y));
        } catch (Exception e) {
            return Try.failure(e);
        }
    }

    default R apply(Tuple6<T, U, V, W, X, Y> tuple) throws Exception {
        return apply(tuple.item0(), tuple.item1(), tuple.item2(), tuple.item3(), tuple.item4(), tuple.item5());
    }

    default Try<R> collect(Tuple6<T, U, V, W, X, Y> tuple) {
        return collect(tuple.item0(), tuple.item1(), tuple.item2(), tuple.item3(), tuple.item4(), tuple.item5());
    }

    default Function5<U, V, W, X, Y, R> curry(T t) {
        return (u, v, w, x, y) -> apply(t, u, v, w, x, y);
    }

    default Function4<V, W, X, Y, R> curry(T t, U u) {
        return (v, w, x, y) -> apply(t, u, v, w, x, y);
    }

    default TriFunction<W, X, Y, R> curry(T t, U u, V v) {
        return (w, x, y) -> apply(t, u, v, w, x, y);
    }

    default BiFunction<X, Y, R> curry(T t, U u, V v, W w) {
        return (x, y) -> apply(t, u, v, w, x, y);
    }

    default Function<Y, R> curry(T t, U u, V v, W w, X x) {
        return y -> apply(t, u, v, w, x, y);
    }
}
