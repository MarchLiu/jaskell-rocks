package jaskell.util;

import com.sun.source.tree.BreakTree;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Just Pair
 *
 * @param item0
 * @param item1
 * @param <T>
 * @param <U>
 */
public record Tuple2<T, U>(T item0, U item1) implements Tuple<T, U, U, T> {
    public <R> R uncurry(BiFunction<T, U, R> functor) throws Exception {
        return functor.apply(item0(), item1());
    }

    public <R> Try<R> tryIt(BiFunction<T, U, R> functor) throws Exception {
        return functor.tryIt(item0(), item1());
    }

    public <V> Tuple2<V, U> item0(V item) {
        return new Tuple2<>(item, item1());
    }

    public <V> Tuple2<T, V> item1(V item) {
        return new Tuple2<>(item0(), item);
    }

    public <V> Tuple3<T, U, V> add(V item) {
        return new Tuple3<>(item0(), item1(), item);
    }

    public <V> Try<Tuple3<T, U, V>> tryAdd(Try<V> tryItem) {
        return tryItem.map(item -> new Tuple3<>(item0(), item1(), item));
    }

    public static <T, U> Try<Tuple2<T, U>> liftA(Try<T> t0, Try<U> t1) {
        return Try.joinMap(t0, t1, Tuple2::new);
    }

    @Override
    public Object get(int pos) throws IndexOutOfBoundsException {
        return switch (pos) {
            case 0 -> item0();
            case 1 -> item1();
            default -> throw new IndexOutOfBoundsException("tuple2 only accept 0 or 1 pos");
        };
    }

    @Override
    public int size() {
        return 2;
    }

    @Override
    public T head() {
        return item0();
    }

    @Override
    public U tail() {
        return item1();
    }

    @Override
    public U last() {
        return item1();
    }

    @Override
    public T butLast() {
        return item0();
    }
}
