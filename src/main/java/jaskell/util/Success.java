package jaskell.util;

import java.util.function.Consumer;
import java.util.function.Predicate;


/**
 * Success branch of Try
 * @param item result
 * @param <T> result's type
 */
public record Success<T>(T item) implements Try<T> {
    @Override
    public Try<T> or(Try<T> other) {
        return this;
    }

    @Override
    public <U> Try<U> map(Function<T, U> mapper) {
        try {
            return new Success<>(mapper.apply(item));
        } catch (Throwable err) {
            return new Failure<>(err);
        }
    }

    @Override
    public Try<T> recover(Function<Throwable, T> func) {
        return this;
    }

    @Override
    public Try<T> recoverToTry(java.util.function.Function<Throwable, Try<T>> func) {
        return this;
    }

    @Override
    public T get() throws Throwable {
        return item;
    }

    @Override
    public T orElse(T other) {
        return item;
    }

    @Override
    public T orElseGet(Try<? extends T> other) throws Throwable {
        return item;
    }

    @Override
    public T getOr(Function<? super Throwable, ? extends T> other) {
        return item;
    }

    @Override
    public T getRecovery(Function<? super Throwable, Try<? extends T>> other) throws Throwable {
        return item;
    }

    @Override
    public boolean isOk() {
        return true;
    }

    @Override
    public boolean isErr() {
        return false;
    }

    @Override
    public <U> Try<U> flatMap(java.util.function.Function<? super T, Try<U>> mapper) {
        return mapper.apply(item);
    }

    @Override
    public void foreach(Consumer<T> consumer) {
        consumer.accept(item);
    }

    @Override
    public boolean anyMatch(Predicate<T> test) {
        return test.test(item);
    }

    @Override
    public Throwable error() {
        return null;
    }
}
