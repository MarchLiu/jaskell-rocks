package jaskell.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Just Pair
 *
 * @param item0
 * @param item1
 * @param <T>
 * @param <U>
 */
public record Tuple8<S, T, U, V, W, X, Y, Z>(S item0, T item1, U item2, V item3, W item4, X item5, Y item6, Z item7)
        implements Tuple<S, Tuple7<T, U, V, W, X, Y, Z>, Z, Tuple7<S, T, U, V, W, X, Y>> {
    public <R> R uncurry(Function8<S, T, U, V, W, X, Y, Z, R> functor) throws Exception {
        return functor.apply(item0(), item1(), item2(), item3(), item4(), item5(), item6(), item7());
    }

    public <R> Try<R> tryIt(Function8<S, T, U, V, W, X, Y, Z, R> functor) throws Exception {
        return functor.collect(item0(), item1(), item2(), item3(), item4(), item5(), item6(), item7());
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

    public Object get(int pos) throws IndexOutOfBoundsException {
        return switch (pos) {
            case 0 -> item0();
            case 1 -> item1();
            case 2 -> item2();
            case 3 -> item3();
            case 4 -> item4();
            case 5 -> item5();
            case 6 -> item6();
            case 7 -> item7();
            default -> throw new IndexOutOfBoundsException("tuple8 only accept pos in range [0, 7]");
        };
    }

    @Override
    public Map<String, Object> toMap(String... keys) throws IllegalArgumentException {
        if (keys.length != 6) {
            throw new IllegalArgumentException(
                    "tuple8 has 8 fields but %d keys given %s".formatted(
                            keys.length, Arrays.asList(keys)));
        }
        Map<String, Object> result = new HashMap<>();
        for (int pos = 0; pos < keys.length; pos++) {
            result.put(keys[pos], get(pos));
        }
        return result;
    }

    @Override
    public int size() {
        return 8;
    }

    @Override
    public S head() {
        return item0();
    }

    @Override
    public Tuple7<T, U, V, W, X, Y, Z> tail() {
        return new Tuple7<>(item1(), item2(), item3(), item4(), item5(), item6(), item7());
    }

    @Override
    public Z last() {
        return item7();
    }

    @Override
    public Tuple7<S, T, U, V, W, X, Y> butLast() {
        return new Tuple7<>(item0(), item1(), item2(), item3(), item4(), item5(), item6());
    }
}
