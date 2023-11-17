package jaskell.parsec.common;

/**
 * Created by Mars Liu on 2016-01-11.
 * Crlf 即 haskell parsec 的 crlf 算子,匹配 \r\n .
 */
public class Crlf implements Parsec<Character, String> {
    @Override
    public String parse(State<Character> s)
            throws Exception {
        new Ch('\r').parse(s);
        new Newline().parse(s);
        return "\r\n";
    }
}
