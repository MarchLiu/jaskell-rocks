package jaskell.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Just Pair
 * @param item0
 * @param item1
 * @param <T>
 * @param <U>
 */
public record Tuple7<T, U, V, W, X, Y, Z>(T item0, U item1, V item2, W item3, X item4, Y item5, Z item6)
        implements Tuple<T, Tuple6<U, V, W, X, Y, Z>, Z, Tuple6<T, U, V, W, X, Y>> {
    public <R> R uncurry(Function7<T, U, V, W, X, Y, Z, R> functor) throws Exception {
        return functor.apply(item0(), item1(), item2(), item3(), item4(), item5(), item6());
    }

    public <R> Try<R> tryIt(Function7<T, U, V, W, X, Y, Z, R> functor) throws Exception {
        return functor.tryIt(item0(), item1(), item2(), item3(), item4(), item5(), item6());
    }

    public <A> Tuple7<A, U, V, W, X, Y, Z> item0(A item) {
        return new Tuple7<>(item, item1(), item2(), item3(), item4(), item5(), item6());
    }

    public <A> Tuple7<T, A, V, W, X, Y, Z> item1(A item) {
        return new Tuple7<>(item0(), item, item2(), item3(), item4(), item5(), item6());
    }
    public <A> Tuple7<T, U, A, W, X, Y, Z> item2(A item) {
        return new Tuple7<>(item0(), item1(), item, item3(), item4(), item5(), item6());
    }
    public <A> Tuple7<T, U, V, A, X, Y, Z> item3(A item) {
        return new Tuple7<>(item0(), item1(), item2(), item, item4(), item5(), item6());
    }
    public <A> Tuple7<T, U, V, W, A, Y, Z> item4(A item) {
        return new Tuple7<>(item0(), item1(), item2(), item3(), item, item5(), item6());
    }
    public <A> Tuple7<T, U, V, W, X, A, Z> item5(A item) {
        return new Tuple7<>(item0(), item1(), item2(), item3(), item4(), item, item6());
    }
    public <A> Tuple7<T, U, V, W, X, Y, A> item6(A item) {
        return new Tuple7<>(item0(), item1(), item2(), item3(), item4(), item5(), item);
    }

    public <A> Tuple8<T, U, V, W, X, Y, Z, A> add(A item) {
        return new Tuple8<>(item0(), item1(), item2(), item3(), item4(), item5(), item6(), item);
    }

    public <A> Try<Tuple8<T, U, V, W, X, Y, Z, A>> tryAdd(Try<A> tryItem) {
        return tryItem.map(item -> new Tuple8<>(item0(), item1(), item2(), item3(), item4(), item5(), item6(), item));
    }

    public static <T, U, V, W, X, Y, Z> Try<Tuple7<T, U, V, W, X, Y, Z>> liftA(
            Try<T> t0, Try<U> t1, Try<V> t2, Try<W> t3, Try<X> t4, Try<Y> t5, Try<Z> t6) {
        return Try.joinMap7(t0, t1, t2, t3, t4, t5, t6, Tuple7::new);
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
            default -> throw new IndexOutOfBoundsException("tuple7 only accept pos in range [0, 6]");
        };
    }

    @Override
    public Map<String, Object> toMap(String... keys) throws IllegalArgumentException {
        if (keys.length != 6) {
            throw new IllegalArgumentException(
                    "tuple7 has 7 fields but %d keys given %s".formatted(
                            keys.length, Arrays.asList(keys)));
        }
        Map<String, Object> result = new HashMap<>();
        for(int pos = 0; pos < keys.length; pos ++) {
            result.put(keys[pos], get(pos));
        }
        return result;
    }

    @Override
    public int size() {
        return 7;
    }

    @Override
    public T head() {
        return item0();
    }

    @Override
    public Tuple6<U, V, W, X, Y, Z> tail() {
        return new Tuple6<>(item1(), item2(), item3(), item4(), item5(), item6());
    }

    @Override
    public Z last() {
        return item6();
    }

    @Override
    public Tuple6<T, U, V, W, X, Y> butLast() {
        return new Tuple6<>(item0(), item1(), item2(), item3(), item4(), item5());
    }
}
