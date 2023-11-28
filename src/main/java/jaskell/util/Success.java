package jaskell.util;

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
        } catch (Exception err) {
            return new Failure<>(err);
        }
    }

    @Override
    public Try<T> recover(Function<Exception, T> func) {
        return this;
    }

    @Override
    public Try<T> recoverToTry(java.util.function.Function<Exception, Try<T>> func) {
        return this;
    }

    @Override
    public T get() throws Exception {
        return item;
    }

    @Override
    public T orElse(T other) {
        return item;
    }

    @Override
    public T orElseGet(Try<? extends T> other) throws Exception {
        return item;
    }

    @Override
    public T getOr(Function<? super Exception, ? extends T> other) {
        return item;
    }

    @Override
    public T getRecovery(Function<? super Exception, Try<? extends T>> other) throws Exception {
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
    public <U> Try<U> flatMap(Function<? super T, Try<U>> mapper) {
        try {
            return mapper.apply(item);
        } catch (Exception e) {
            return Try.failure(e);
        }
    }

    @Override
    public Try<Void> foreach(Consumer<T> consumer) {
        try {
            consumer.accept(item);
            return Try.success(null);
        } catch (Exception err) {
            return Try.failure(err);
        }
    }

    @Override
    public boolean anyMatch(Predicate<T> test) {
        return test.test(item);
    }

    @Override
    public Exception error() {
        return null;
    }
}
