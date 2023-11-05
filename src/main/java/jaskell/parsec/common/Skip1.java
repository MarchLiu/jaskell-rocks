package jaskell.parsec.common;

/**
 * Created by Mars Liu on 2016-01-07.
 * 跳过指定算子 1 到多次.
 */
public class Skip1<E, T>
    implements Parsec<E, T> {
    private final Parsec<E, T> psc;
    private final Parsec<E, T> skip;

    @Override
    public T parse(State<E> s) throws Throwable {
        psc.parse(s);
        skip.parse(s);
        return null;
    }

    public Skip1(Parsec<E, T> psc) {
        this.psc = psc;
        this.skip = new Skip<>(psc);
    }
}