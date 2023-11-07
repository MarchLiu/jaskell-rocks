package jaskell.parsec.common;

import jaskell.parsec.ParsecException;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class NoneOfTest extends Base {


    /**
     * Method: script(State<T> s)
     */
    @Test
    public void simpleOK() throws Exception {
        State<Character> state = newState("hello");

        NoneOf<Character> noneOf = new NoneOf<>(Stream.of('k', 'o', 'f').collect(toSet()));
        Character c = noneOf.parse(state);

        assertEquals(c, 'h');
    }

    @Test
    public void simpleFail() throws Exception {
        NoneOf<Character> noneOf = new NoneOf<>(Stream.of('k', 'f', 's').collect(toSet()));
        String content = "sound";
        State<Character> state2 = newState(content);
        assertThrowsExactly(ParsecException.class,
                () -> noneOf.parse(state2),
                "Expect none of \"%s\" failed");
    }


} 
