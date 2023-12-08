package jaskell.parsec.common;

import jaskell.util.Try;

import java.util.List;

/**
 * Common Parsec 是简化的组合子接口，支持整数索引和事务标示
 *
 * @param <T> 结果类型
 * @param <E> 输入的元素类型
 */
@FunctionalInterface
public interface Parsec<E, T> {
    T parse(State<E> s)
            throws Exception;

    default <C extends List<E>> T parse(C collection) throws Exception {
        State<E> s = new SimpleState<>(collection);
        return this.parse(s);
    }

    @SuppressWarnings("unchecked")
    default T parse(String content) throws Exception {
        State<Character> s = new TxtState(content);
        return ((Parsec<Character, T>) this).parse(s);
    }

    default Try<T> exec(State<E> s) {
        try {
            return Try.success(Parsec.this.parse(s));
        } catch (Exception e) {
            return Try.failure(e);
        }
    }

    default <C extends List<E>> Try<T> exec(C collection) {
        State<E> s = new SimpleState<>(collection);
        return this.exec(s);
    }

    default Try<T> exec(String content) throws Exception {
        State<Character> s = new TxtState(content);
        return ((Parsec<Character, T>) this).exec(s);
    }

    default <C> Parsec<E, C> bind(Binder<E, T, C> binder) {
        return s -> {
            T value = Parsec.this.parse(s);
            return binder.bind(value).parse(s);
        };
    }

    default <C> Parsec<E, C> then(Parsec<E, C> parsec) {
        return s -> {
            Parsec.this.parse(s);
            return parsec.parse(s);
        };
    }

    default <C> Parsec<E, T> over(Parsec<E, C> parsec) {
        return s -> {
            T value = Parsec.this.parse(s);
            parsec.parse(s);
            return value;
        };
    }

    default Parsec<E, T> attempt() {
        return new Attempt<>(this);
    }

    default Parsec<E, T> ahead() {
        return new Ahead<>(this);
    }

    default Parsec<E, List<T>> many() {
        return new Many<>(this);
    }

    default Parsec<E, List<T>> many1() {
        return new Many1<>(this);
    }

    default Parsec<E, T> skip() {
        return new Skip<>(this);
    }

    default Parsec<E, T> skip1() {
        return new Skip1<>(this);
    }

    default <Sep> Parsec<E, List<T>> sepBy(Parsec<E, Sep> by) {
        return new SepBy<>(this, by);
    }

    default <Sep> Parsec<E, List<T>> sepBy1(Parsec<E, Sep> by) {
        return new SepBy1<>(this, by);
    }

    default <Till> Parsec<E, List<T>> manyTill(Parsec<E, Till> till){
        return new ManyTill<>(this, till);
    }

}