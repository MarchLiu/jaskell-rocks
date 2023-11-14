package jaskell.util;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;


/**
 * Failure branch of Try
 * @param err error error raised when try to get
 * @param <T> type of scuccess
 */
public record Failure<T>(Throwable err) implements Try<T> {
    @Override
    public Try<T> or(Try<T> other) {
        return other;
    }

    @Override
    public <U> Try<U> map(Function<T, U> mapper) {
        return new Failure<>(err);
    }

    @Override
    public Try<T> recover(Function<Throwable, T> func) {
        try {
            return new Success<>(func.apply(err));
        } catch (Throwable err){
            return new Failure<>(err);
        }
    }

    @Override
    public Try<T> recoverToTry(Function<Throwable, Try<T>> func) {
        return func.apply(err);
    }

    @Override
    public T get() throws Throwable {
        throw err;
    }

    @Override
    public T orElse(T other) {
        return other;
    }

    @Override
    public T orElseGet(Try<? extends T> other) throws Throwable {
        return other.get();
    }

    @Override
    public T getOr(Function<? super Throwable, ? extends T> other) {
        return other.apply(err);
    }

    @Override
    public T getRecovery(Function<? super Throwable, Try<? extends T>> other) throws Throwable {
        return other.apply(err).get();
    }

    @Override
    public boolean isOk() {
        return false;
    }

    @Override
    public boolean isErr() {
        return true;
    }

    @Override
    public <U> Try<U> flatMap(Function<? super T, Try<U>> mapper) {
        return new Failure<>(err);
    }

    @Override
    public void foreach(Consumer<T> consumer) {

    }

    @Override
    public boolean anyMatch(Predicate<T> test) {
        return false;
    }

    @Override
    public Throwable error() {
        return err;
    }
}
