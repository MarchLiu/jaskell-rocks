package jaskell.parsec.common;

/**
 * Created by march on 16/9/12.
 * SkipSpaces is a parser skip all spaces.
 */
public class SkipSpaces
    implements Parsec<Character, Character> {
    private final Parsec<Character, Character> parser = new Skip<>(new Whitespace());
    @Override
    public Character parse(State<Character> s)
            throws Exception {
        return parser.parse(s);
    }
}
