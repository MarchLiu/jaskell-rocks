package jaskell.util;

import java.util.function.*;

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/12/06 13:56
 */
public sealed interface Try<T> permits Failure, Success {

    Try<T> or(Try<T> other);

    Try<T> recover(Function<Exception, T> func);

    Try<T> recoverToTry(java.util.function.Function<Exception, Try<T>> func);

    @SuppressWarnings("unchecked")
    T get() throws Exception;

    @SuppressWarnings("unchecked")
    T orElse(T other);

    @SuppressWarnings("unchecked")
    T orElseGet(Try<? extends T> other) throws Exception;

    @SuppressWarnings("unchecked")
    T getOr(Function<? super Exception, ? extends T> other) throws Exception;

    @SuppressWarnings("unchecked")
    T getRecovery(Function<? super Exception, Try<? extends T>> other) throws Exception;

    boolean isOk();

    boolean isErr();

    @SuppressWarnings("unchecked")
    <U> Try<U> map(Function<T, U> mapper);

    @SuppressWarnings("unchecked")
    <U> Try<U> flatMap(java.util.function.Function<? super T, Try<U>> mapper);


    default <U, R> Try<? extends R> map2(Try<U> other, BiFunction<? super T, ? super U, ? extends R> mapper) {
        if (this.isErr()) {
            return new Failure<>(this.error());
        }
        if (other.isErr()) {
            return new Failure<>(other.error());
        }
        try {
            return new Success<>(mapper.apply(this.get(), other.get()));
        } catch (Exception err) {
            return new Failure<>(err);
        }
    }

    default <U, R> Try<? extends R> flatMap2(Try<U> other, BiFunction<? super T, ? super U, Try<? extends R>> mapper) {
        if (this.isErr()) {
            return new Failure<>(this.error());
        }
        if (other.isErr()) {
            return new Failure<>(other.error());
        }
        try {
            return mapper.apply(this.get(), other.get());
        } catch (Exception err) {
            return new Failure<>(err);
        }
    }

    @SuppressWarnings("unchecked")
    void foreach(Consumer<T> consumer);

    @SuppressWarnings("unchecked")
    boolean anyMatch(Predicate<T> test);

    Exception error();

    static <T> Try<T> success(T value) {
        return new Success<>(value);
    }

    static <T> Try<T> failure(Exception err) {
        return new Failure<>(err);
    }

    static <T> Try<T> failure(String message) {
        return new Failure<>(new Exception(message));
    }

    static <T> Try<T> tryIt(Supplier<? extends T> supplier) {
        try {
            return Try.success(supplier.get());
        } catch (Exception err) {
            return Try.failure(err);
        }
    }

    static <T, U> Try<? extends U> call(Function<? super T, ? extends U> func, T arg) throws Exception{
        try {
            return Try.success(func.apply(arg));
        } catch (Exception err) {
            return Try.failure(err);
        }
    }

    /**
     * Collect two Try items and then map the BiFunction
     *
     * @param t1   try item 1
     * @param t2   try item 2
     * @param func the map function
     * @param <T>  type of item 1
     * @param <U>  type of item 2
     * @param <R>  type of result at last
     * @return return a Try include result of the biFunction
     */
    static <T, U, R> Try<? extends R> joinMap(Try<T> t1, Try<U> t2, BiFunction<? super T, ? super U, ? extends R> func) {
        try {
            var r1 = t1.get();
            var r2 = t2.get();
            return Try.success(func.apply(r1, r2));
        } catch (Exception e) {
            return Try.failure(e);
        }
    }

    /**
     * Collect two Try items and then map to a BiFunction return Try
     *
     * @param t1   try item 1
     * @param t2   try item 2
     * @param func the flat map function
     * @param <T>  type of item 1
     * @param <U>  type of item 2
     * @param <R>  type of result
     * @return return the flat map biFunction's result
     */
    static <T, U, R> Try<? extends R> joinFlatMap(Try<T> t1, Try<U> t2,
                                                  BiFunction<? super T, ? super U, Try<? extends R>> func) {
        try {
            var r1 = t1.get();
            var r2 = t2.get();
            return func.apply(r1, r2);
        } catch (Exception e) {
            return Try.failure(e);
        }
    }

    /**
     * Collect three Try items and then map to a TriFunction
     *
     * @param t1   try item 1
     * @param t2   try item 2
     * @param t3   try item 3
     * @param func the map function
     * @param <T>  type of item 1
     * @param <U>  type of item 2
     * @param <V>  type of item 3
     * @param <R>  type of result
     * @return return a Try include result of the triFunction
     */
    static <T, U, V, R> Try<? extends R> joinMap3(Try<T> t1, Try<U> t2, Try<V> t3,
                                                  TriFunction<? super T, ? super U, ? super V, ? extends R> func) {
        try {
            var r1 = t1.get();
            var r2 = t2.get();
            var r3 = t3.get();
            return Try.success(func.apply(r1, r2, r3));
        } catch (Exception e) {
            return Try.failure(e);
        }
    }

    /**
     * Collect three Try items and then flat map to a TriFunction return Try item
     *
     * @param t1   try item 1
     * @param t2   try item 2
     * @param t3   try item 3
     * @param func the flat map function
     * @param <T>  type of item 1
     * @param <U>  type of item 2
     * @param <V>  type of item 3
     * @param <R>  type of item included in function's result
     * @return return Try result of the triFunction
     */
    static <T, U, V, R> Try<? extends R> joinFlatMap3(Try<T> t1, Try<U> t2, Try<V> t3,
                                                      TriFunction<? super T, ? super U, ? super V, Try<? extends R>> func) {
        try {
            var r1 = t1.get();
            var r2 = t2.get();
            var r3 = t3.get();
            return func.apply(r1, r2, r3);
        } catch (Exception e) {
            return Try.failure(e);
        }
    }

    /**
     * Collect four Try items and then map to a Function4
     *
     * @param t1   try item 1
     * @param t2   try item 2
     * @param t3   try item 3
     * @param t4   try item 4
     * @param func the map function
     * @param <T>  type of item 1
     * @param <U>  type of item 2
     * @param <V>  type of item 3
     * @param <W>  type of item 4
     * @param <R>  type of result
     * @return return a Try include result of the function
     */
    static <T, U, V, W, R> Try<? extends R> joinMap4(Try<T> t1, Try<U> t2, Try<V> t3, Try<W> t4,
                                                     Function4<? super T, ? super U, ? super V, ? super W, ? extends R> func) {
        try {
            var r1 = t1.get();
            var r2 = t2.get();
            var r3 = t3.get();
            var r4 = t4.get();
            return Try.success(func.apply(r1, r2, r3, r4));
        } catch (Exception e) {
            return Try.failure(e);
        }
    }

    /**
     * Collect four Try items and then flat map to a Function4
     *
     * @param t1   try item 1
     * @param t2   try item 2
     * @param t3   try item 3
     * @param t4   try item 4
     * @param func the flat map function
     * @param <T>  type of item 1
     * @param <U>  type of item 2
     * @param <V>  type of item 3
     * @param <W>  type of item 4
     * @param <R>  type of the function's result include
     * @return return result of the function
     */
    static <T, U, V, W, R> Try<? extends R> joinFlatMap4(Try<T> t1, Try<U> t2, Try<V> t3, Try<W> t4,
                                                         Function4<? super T, ? super U, ? super V, ? super W,
                                                                 Try<? extends R>> func) {
        try {
            var r1 = t1.get();
            var r2 = t2.get();
            var r3 = t3.get();
            var r4 = t4.get();
            return func.apply(r1, r2, r3, r4);
        } catch (Exception e) {
            return Try.failure(e);
        }
    }

    /**
     * Collect five Try items and then map to a Function5
     *
     * @param t1   try item 1
     * @param t2   try item 2
     * @param t3   try item 3
     * @param t4   try item 4
     * @param t5   try item 5
     * @param func the map function
     * @param <T>  type of item 1
     * @param <U>  type of item 2
     * @param <V>  type of item 3
     * @param <W>  type of item 4
     * @param <X>  type of item 5
     * @param <R>  type of result
     * @return return a Try include result of the function
     */
    static <T, U, V, W, X, R> Try<? extends R> joinMap5(Try<T> t1, Try<U> t2, Try<V> t3, Try<W> t4, Try<X> t5,
                                                        Function5<? super T, ? super U, ? super V,
                                                                ? super W, ? super X,
                                                                ? extends R> func) {
        try {
            var r1 = t1.get();
            var r2 = t2.get();
            var r3 = t3.get();
            var r4 = t4.get();
            var r5 = t5.get();
            return Try.success(func.apply(r1, r2, r3, r4, r5));
        } catch (Exception e) {
            return Try.failure(e);
        }
    }

    /**
     * Collect five Try items and then flat map to a Function5
     *
     * @param t1   try item 1
     * @param t2   try item 2
     * @param t3   try item 3
     * @param t4   try item 4
     * @param t5   try item 5
     * @param func the flat map function
     * @param <T>  type of item 1
     * @param <U>  type of item 2
     * @param <V>  type of item 3
     * @param <W>  type of item 4
     * @param <X>  type of item 5
     * @param <R>  type of the function's result include
     * @return return result of the function
     */
    static <T, U, V, W, X, R> Try<? extends R> joinFlatMap5(Try<T> t1, Try<U> t2, Try<V> t3, Try<W> t4, Try<X> t5,
                                                            Function5<? super T, ? super U, ? super V, ? super W, ? super X,
                                                                    Try<? extends R>> func) {
        try {
            var r1 = t1.get();
            var r2 = t2.get();
            var r3 = t3.get();
            var r4 = t4.get();
            var r5 = t5.get();
            return func.apply(r1, r2, r3, r4, r5);
        } catch (Exception e) {
            return Try.failure(e);
        }
    }

    /**
     * Collect six Try items and then map to a Function6
     *
     * @param t1   try item 1
     * @param t2   try item 2
     * @param t3   try item 3
     * @param t4   try item 4
     * @param t5   try item 5
     * @param t6   try item 6
     * @param func the map function
     * @param <T>  type of item 1
     * @param <U>  type of item 2
     * @param <V>  type of item 3
     * @param <W>  type of item 4
     * @param <X>  type of item 5
     * @param <Y>  type of item 6
     * @param <R>  type of result
     * @return return a Try include result of the function
     */
    static <T, U, V, W, X, Y, R> Try<? extends R> joinMap6(Try<T> t1, Try<U> t2, Try<V> t3, Try<W> t4, Try<X> t5, Try<Y> t6,
                                                           Function6<? super T, ? super U, ? super V, ? super W,
                                                                   ? super X, ? super Y, ? extends R> func) {
        try {
            var r1 = t1.get();
            var r2 = t2.get();
            var r3 = t3.get();
            var r4 = t4.get();
            var r5 = t5.get();
            var r6 = t6.get();
            return Try.success(func.apply(r1, r2, r3, r4, r5, r6));
        } catch (Exception e) {
            return Try.failure(e);
        }
    }

    /**
     * Collect six Try items and then flat map to a Function6
     *
     * @param t1   try item 1
     * @param t2   try item 2
     * @param t3   try item 3
     * @param t4   try item 4
     * @param t5   try item 5
     * @param t6   try item 6
     * @param func the flat map function
     * @param <T>  type of item 1
     * @param <U>  type of item 2
     * @param <V>  type of item 3
     * @param <W>  type of item 4
     * @param <X>  type of item 5
     * @param <Y>  type of item 6
     * @param <R>  type of the function's result include
     * @return return result of the function
     */
    static <T, U, V, W, X, Y, R> Try<? extends R> joinFlatMap6(Try<T> t1, Try<U> t2, Try<V> t3, Try<W> t4, Try<X> t5, Try<Y> t6,
                                                               Function6<? super T, ? super U, ? super V,
                                                                       ? super W, ? super X, ? super Y,
                                                                       Try<? extends R>> func) {
        try {
            var r1 = t1.get();
            var r2 = t2.get();
            var r3 = t3.get();
            var r4 = t4.get();
            var r5 = t5.get();
            var r6 = t6.get();
            return func.apply(r1, r2, r3, r4, r5, r6);
        } catch (Exception e) {
            return Try.failure(e);
        }
    }

    /**
     * Collect seven Try items and then map to a Function7
     *
     * @param t1   try item 1
     * @param t2   try item 2
     * @param t3   try item 3
     * @param t4   try item 4
     * @param t5   try item 5
     * @param t6   try item 6
     * @param t7   try item 6
     * @param func the map function
     * @param <T>  type of item 1
     * @param <U>  type of item 2
     * @param <V>  type of item 3
     * @param <W>  type of item 4
     * @param <X>  type of item 5
     * @param <Y>  type of item 6
     * @param <Z>  type of item 7
     * @param <R>  type of result
     * @return return a Try include result of the function
     */
    static <T, U, V, W, X, Y, Z, R> Try<? extends R> joinMap7(Try<T> t1, Try<U> t2, Try<V> t3, Try<W> t4,
                                                              Try<X> t5, Try<Y> t6, Try<Z> t7,
                                                              Function7<? super T, ? super U, ? super V, ? super W,
                                                                      ? super X, ? super Y, ? super Z, ? extends R> func) {
        try {
            var r1 = t1.get();
            var r2 = t2.get();
            var r3 = t3.get();
            var r4 = t4.get();
            var r5 = t5.get();
            var r6 = t6.get();
            var r7 = t7.get();
            return Try.success(func.apply(r1, r2, r3, r4, r5, r6, r7));
        } catch (Exception e) {
            return Try.failure(e);
        }
    }

    /**
     * Collect seven Try items and then flat map to a Function7
     *
     * @param t1   try item 1
     * @param t2   try item 2
     * @param t3   try item 3
     * @param t4   try item 4
     * @param t5   try item 5
     * @param t6   try item 6
     * @param t7   try item 7
     * @param func the flat map function
     * @param <T>  type of item 1
     * @param <U>  type of item 2
     * @param <V>  type of item 3
     * @param <W>  type of item 4
     * @param <X>  type of item 5
     * @param <Y>  type of item 6
     * @param <Z>  type of item 7
     * @param <R>  type of the function's result include
     * @return return result of the function
     */
    static <T, U, V, W, X, Y, Z, R> Try<? extends R> joinFlatMap7(Try<T> t1, Try<U> t2, Try<V> t3, Try<W> t4,
                                                                  Try<X> t5, Try<Y> t6, Try<Z> t7,
                                                                  Function7<? super T, ? super U, ? super V,
                                                                          ? super W, ? super X, ? super Y,
                                                                          ? super Z, Try<? extends R>> func) {
        try {
            var r1 = t1.get();
            var r2 = t2.get();
            var r3 = t3.get();
            var r4 = t4.get();
            var r5 = t5.get();
            var r6 = t6.get();
            var r7 = t7.get();
            return func.apply(r1, r2, r3, r4, r5, r6, r7);
        } catch (Exception e) {
            return Try.failure(e);
        }
    }

    /**
     * Collect eight Try items and then map to a Function8
     *
     * @param t1   try item 1
     * @param t2   try item 2
     * @param t3   try item 3
     * @param t4   try item 4
     * @param t5   try item 5
     * @param t6   try item 6
     * @param t7   try item 7
     * @param t8   try item 8
     * @param func the map function
     * @param <S>  type of item 1
     * @param <T>  type of item 2
     * @param <U>  type of item 3
     * @param <V>  type of item 4
     * @param <W>  type of item 5
     * @param <X>  type of item 6
     * @param <Y>  type of item 7
     * @param <Z>  type of item 8
     * @param <R>  type of result
     * @return return a Try include result of the function
     */
    static <S, T, U, V, W, X, Y, Z, R> Try<? extends R> joinMap8(Try<S> t1, Try<T> t2, Try<U> t3, Try<V> t4,
                                                                 Try<W> t5, Try<X> t6, Try<Y> t7, Try<Z> t8,
                                                                 Function8<? super S, ? super T, ? super U,
                                                                         ? super V, ? super W, ? super X,
                                                                         ? super Y, ? super Z, ? extends R> func) {
        try {
            var r1 = t1.get();
            var r2 = t2.get();
            var r3 = t3.get();
            var r4 = t4.get();
            var r5 = t5.get();
            var r6 = t6.get();
            var r7 = t7.get();
            var r8 = t8.get();
            return Try.success(func.apply(r1, r2, r3, r4, r5, r6, r7, r8));
        } catch (Exception e) {
            return Try.failure(e);
        }
    }

    /**
     * Collect eight Try items and then flat map to a Function8
     *
     * @param t1   try item 1
     * @param t2   try item 2
     * @param t3   try item 3
     * @param t4   try item 4
     * @param t5   try item 5
     * @param t6   try item 6
     * @param t7   try item 7
     * @param t8   try item 7
     * @param func the flat map function
     * @param <S>  type of item 1
     * @param <T>  type of item 2
     * @param <U>  type of item 3
     * @param <V>  type of item 4
     * @param <W>  type of item 5
     * @param <X>  type of item 6
     * @param <Y>  type of item 7
     * @param <Z>  type of item 8
     * @param <R>  type of the function's result include
     * @return return result of the function
     */
    static <S, T, U, V, W, X, Y, Z, R> Try<? extends R> joinFlatMap8(Try<S> t1, Try<T> t2, Try<U> t3, Try<V> t4,
                                                                     Try<W> t5, Try<X> t6, Try<Y> t7, Try<Z> t8,
                                                                     Function8<? super S, ? super T, ? super U,
                                                                             ? super V, ? super W, ? super X,
                                                                             ? super Y, ? super Z,
                                                                             Try<? extends R>> func) {
        try {
            var r1 = t1.get();
            var r2 = t2.get();
            var r3 = t3.get();
            var r4 = t4.get();
            var r5 = t5.get();
            var r6 = t6.get();
            var r7 = t7.get();
            var r8 = t8.get();
            return func.apply(r1, r2, r3, r4, r5, r6, r7, r8);
        } catch (Exception e) {
            return Try.failure(e);
        }
    }
}
