package jaskell.parsec.common;

/**
 * Created by zhaonf on 16/1/10.
 * EndOfLine 尝试匹配 \r\n 或 \n
 */
public class EndOfLine
    implements Parsec<Character, String> {
    private final Parsec<Character, String> parsec =
        new Choice<>(new Text("\n"), new Text("\r\n"));
    @Override
    public String parse(State<Character> s)
            throws Throwable {
        return parsec.parse(s);
    }
}
