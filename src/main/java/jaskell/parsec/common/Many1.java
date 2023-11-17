package jaskell.parsec.common;

import jaskell.util.Success;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mars Liu on 2016-01-03.
 * Many1 匹配给定算子 1 到多次.
 */
public class Many1<E, T>
        implements Parsec<E, List<T>> {
    private final Parsec<E, T> parser;

    @Override
    public List<T> parse(State<E> s) throws Exception {
        List<T> re = new ArrayList<>();
        re.add(this.parser.parse(s));
        Parsec<E, T> p = new Attempt<>(parser);
        while (p.exec(s) instanceof Success<T> item) {
            re.add(item.get());
        }
        return re;
    }

    public Many1(Parsec<E, T> parsec) {
        this.parser = parsec;
    }
}
