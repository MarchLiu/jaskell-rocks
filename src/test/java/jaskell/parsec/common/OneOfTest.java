package jaskell.parsec.common;

import jaskell.parsec.ParsecException;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class OneOfTest extends Base {
    private final String data = "It is a \"string\" for this unit test";


    /**
     * Method: script(State<T> s)
     */
    @Test
    public void simple() throws Exception {
        State<Character> state = newState("hello");

        Set<Character> buffer = Stream.of('b', 'e', 'h', 'f').collect(toSet()); //IntStream.range(0, 2).mapToObj(data::charAt).collect(toList());
        OneOf<Character> oneOf = new OneOf<>(buffer);

        Character c = oneOf.parse(state);


        assertEquals('h', c);
    }

    @Test
    public void fail() throws Exception {
        State<Character> state = newState("hello");
        OneOf<Character> oneOf = new OneOf<>(Stream.of('d', 'a', 't', 'e').collect(toSet()));
        assertThrowsExactly(ParsecException.class,
                () -> oneOf.parse(state),
                "Expect failed");
    }
}
