package jaskell.util;

import java.util.Objects;


/**
 * @param <T> type of arg1
 * @param <U> type of arg2
 * @param <V> type of arg3
 * @param <W> type of arg4
 * @param <R> type of result
 */
public interface Function4<T, U, V, W, R> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @return the function result
     */
    R apply(T t, U u, V v, W w) throws Exception;

    /**
     * Returns a composed function that first applies this function to
     * its input, and then applies the {@code after} function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <O> the type of output of the {@code after} function, and of the
     *           composed function
     * @param after the function to apply after this function is applied
     * @return a composed function that first applies this function and then
     * applies the {@code after} function
     * @throws NullPointerException if after is null
     */
    default <O> Function4<T, U, V, W, O> andThen(Function<? super R, ? extends O> after) {
        Objects.requireNonNull(after);
        return (T t, U u, V v, W w) -> after.apply(apply(t, u, v, w));
    }

    default Try<R> collect(T t, U u, V v, W w) {
        try {
            return Try.success(apply(t, u, v, w));
        } catch (Exception e) {
            return Try.failure(e);
        }
    }

    default R apply(Tuple4<T, U, V, W> tuple) throws Exception {
        return apply(tuple.item0(), tuple.item1(), tuple.item2(), tuple.item3());
    }

    default Try<R> collect(Tuple4<T, U, V, W> tuple) {
        return collect(tuple.item0(), tuple.item1(), tuple.item2(), tuple.item3());
    }

    default TriFunction<U, V, W, R> curry(T t) {
        return (u, v, w) -> apply(t, u, v, w);
    }

    default BiFunction<V, W, R> curry(T t, U u) {
        return (v, w) -> apply(t, u, v, w);
    }

    default Function<W, R> curry(T t, U u, V v) {
        return w -> apply(t, u, v, w);
    }

}
