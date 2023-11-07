package jaskell.parsec.common;

import jaskell.util.Try;

import java.io.EOFException;
import java.util.function.Function;

/**
 * Created by Mars Liu on 2016-01-07.
 * Ch 即 char, 是为 Character 特化的 Eq.
 */
public class Ch implements Parsec<Character, Character> {
    private final Function<State<Character>, Try<Character>> parser;

    public Ch(Character chr) {
        this(chr, true);
    }

    public Ch(Character chr, Boolean caseSensitive) {
        Character chr1;
        if (caseSensitive) {
            chr1 = chr;
            this.parser = s -> {
                try {
                    Character c = s.next();
                    if (c.equals(chr1)) {
                        return Try.success(c);
                    } else {
                        return Try.failure(s.trap(String.format("expect char %c (case sensitive %b) at %s but %c",
                                chr, caseSensitive, s.status().toString(), c)));
                    }
                } catch (EOFException e) {
                    return Try.failure(e);
                }
            };
        } else {
            chr1 = Character.toLowerCase(chr);
            this.parser = s -> {
                try {
                    Character c = s.next();
                    if (chr1.equals(c) ){
                        return Try.success(c);
                    } else {
                        return Try.failure(s.trap(String.format("expect char %c (case sensitive %b) at %s but %c",
                                chr, caseSensitive, s.status().toString(), c)));
                    }
                } catch (EOFException e) {
                    return Try.failure(e);
                }
            };
        }
    }

    @Override
    public Character parse(State<Character> s) throws Throwable {
        return parser.apply(s).get();
    }
}
