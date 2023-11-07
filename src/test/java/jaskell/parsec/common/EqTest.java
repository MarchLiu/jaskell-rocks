package jaskell.parsec.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Eq Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>一月 9, 2016</pre>
 */
public class EqTest extends Base {

    /**
     * Method: script(State<T> s)
     */
    @Test
    public void testEq() throws Exception {
        State<Character> state = newState("hello");

        Eq<Character> eq = new Eq<>('h');
        Character c = eq.parse(state);
        assertEquals('h', c);
    }


} 
