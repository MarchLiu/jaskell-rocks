package jaskell.util;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/12/06 13:56
 */
public interface Try<T>{

    public Try<T> or(Try<T> other);

    @SuppressWarnings("unchecked")
    public <U> Try<U> map(Function<T, U> mapper) ;

    public Try<T> recover(Function<Throwable, T> func) ;

    public Try<T> recoverToTry(Function<Throwable, Try<T>> func);

    @SuppressWarnings("unchecked")
    public T get() throws Throwable;

    @SuppressWarnings("unchecked")
    public T orElse(T other) ;

    @SuppressWarnings("unchecked")
    public T orElseGet(Try<? extends T> other) throws Throwable;

    @SuppressWarnings("unchecked")
    public T getOr(Function<? super Throwable, ? extends T> other) ;

    @SuppressWarnings("unchecked")
    public T getRecovery(Function<? super Throwable, Try<? extends T>> other) throws Throwable;

    public boolean isOk();

    public boolean isErr() ;

    @SuppressWarnings("unchecked")
    public <U> Try<U> flatMap(Function<? super T, Try<U>> mapper) ;

    @SuppressWarnings("unchecked")
    public void foreach(Consumer<T> consumer) ;

    @SuppressWarnings("unchecked")
    public boolean anyMatch(Predicate<T> test) ;

    public Throwable error();

    public static <T> Try<T> success(T value) {
        return new Success<>(value);
    }

    public static <T> Try<T> failure(Throwable err) {
        return new Failure<>(err);
    }

    public static <T> Try<T> failure(String message) {
        return new Failure<>(new Exception(message));
    }

    public static <T> Try<T> tryIt(Supplier<T> supplier) {
        try {
            return Try.success(supplier.get());
        } catch (Exception err){
            return Try.failure(err);
        }
    }

    public static <T, U> Try<U> call(Function<T, U> func, T arg) {
        try {
            return Try.success(func.apply(arg));
        } catch (Exception err){
            return Try.failure(err);
        }
    }
}
