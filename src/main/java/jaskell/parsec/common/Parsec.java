package jaskell.parsec.common;

import jaskell.util.Try;

import java.util.List;

/**
 * Common Parsec 是简化的组合子接口，支持整数索引和事务标示
 * @param <T> 结果类型
 * @param <E> 输入的元素类型
 */
@FunctionalInterface
public interface Parsec<E, T> {
  T parse(State<E> s)
          throws Throwable;

  default <C extends List<E>> T parse(C collection) throws Throwable {
    State<E> s = new SimpleState<>(collection);
    return this.parse(s);
  }

  @SuppressWarnings("unchecked")
  default T parse(String content) throws Throwable {
      State<Character> s = new TxtState(content);
      return ((Parsec<Character, T>)this).parse(s);
  }

  default Try<T> exec(State<E> s) {
    try {
      return Try.success(Parsec.this.parse(s));
    } catch (Throwable e) {
      return Try.failure(e);
    }
  }

  default <C extends List<E>> Try<T> exec(C collection) {
    State<E> s = new SimpleState<>(collection);
    return this.exec(s);
  }

  default Try<T> exec(String content) throws Throwable {
    State<Character> s = new TxtState(content);
    return ((Parsec<Character, T>)this).exec(s);
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

}