package jaskell.parsec.common;

/**
 * Created by Mars Liu .
 * Newline 尝试匹配换\n
 * -----------------
 */
public class Newline
    implements Parsec<Character, Character> {
    private final Parsec<Character, Character> parser = new Ch('\n');
    @Override
    public Character parse(State<Character> s)
            throws Throwable {
        return parser.parse(s);
    }
}
