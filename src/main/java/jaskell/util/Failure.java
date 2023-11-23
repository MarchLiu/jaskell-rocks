package jaskell.util;

import java.util.function.Predicate;


/**
 * Failure branch of Try
 * @param err error error raised when try to get
 * @param <T> type of scuccess
 */
public record Failure<T>(Exception err) implements Try<T> {
    @Override
    public Try<T> or(Try<T> other) {
        return other;
    }

    @Override
    public <U> Try<U> map(Function<T, U> mapper) {
        return new Failure<>(err);
    }

    @Override
    public Try<T> recover(Function<Exception, T> func) {
        try {
            return new Success<>(func.apply(err));
        } catch (Exception err){
            return new Failure<>(err);
        }
    }

    @Override
    public Try<T> recoverToTry(java.util.function.Function<Exception, Try<T>> func){
        return func.apply(err);
    }

    @Override
    public T get() throws Exception {
        throw err;
    }

    @Override
    public T orElse(T other) {
        return other;
    }

    @Override
    public T orElseGet(Try<? extends T> other) throws Exception {
        return other.get();
    }

    @Override
    public T getOr(Function<? super Exception, ? extends T> other) throws Exception {
        return other.apply(err);
    }

    @Override
    public T getRecovery(Function<? super Exception, Try<? extends T>> other) throws Exception {
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
    public <U> Try<U> flatMap(java.util.function.Function<? super T, Try<U>> mapper) {
        return new Failure<>(err);
    }

    @Override
    public Try<Void> foreach(Consumer<T> consumer) {
        return Try.success(null);
    }

    @Override
    public boolean anyMatch(Predicate<T> test) {
        return false;
    }

    @Override
    public Exception error() {
        return err;
    }
}
