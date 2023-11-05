package jaskell.parsec.common;

import jaskell.parsec.State;
import org.junit.jupiter.api.Test;

import java.io.EOFException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

/**
 * BasicState Tester.
 *
 * @author Mars Liu
 * @version 1.0
 * @since 2016-09-11
 */
public class SimpleStateTest extends Base {


    /**
     * Method: Index()
     */
    @Test
    public void testIndex() throws Exception {
        String data = "It is a \"string\" for this unit test";
        State<Character, Integer, Integer> state = newState(data);
        while (state.status() < data.length()) {
            int index = state.status();
            Character c = state.next();
            Character chr = data.charAt(index);
            assertEquals(chr, c);
        }
        assertThrowsExactly(
                EOFException.class,
                state::next,
                "The state next at preview line should failed");

    }

    /**
     * Method: Begin()
     */
    @Test
    public void testBegin() throws Exception {
        State<Character, Integer, Integer> state = newState("hello");

        Character c = state.next();

        assertEquals('h', c);

        Integer a = state.begin();

        state.next();
        state.next();
        state.next();

        state.rollback(a);

        Character d = state.next();

        assertEquals('e', d);
    }

    /**
     * Method: Commit(int tran)
     */
    @Test
    public void testCommit() throws Exception {
        State<Character, Integer, Integer> state = newState("hello");
        int a = state.begin();
        Character c = state.next();


        assertEquals('h', c);
        state.next();

        state.commit(a);

        Character d = state.next();

        assertEquals('l', d);
    }

    /**
     * Method: Rollback(int tran)
     */
    @Test
    public void testRollback() throws Exception {
        State<Character, Integer, Integer> state = newState("hello");

        int a = state.begin();
        Character c = state.next();


        assertEquals('h', c);

        state.rollback(a);

        Character d = state.next();

        assertEquals('h', d);
    }

    /**
     * Method: Next()
     */
    @Test
    public void testNext() throws Exception {
        State<Character, Integer, Integer> state = newState("hello");


        Character c = state.next();
        assertEquals('h', c);

        Character d = state.next();
        assertEquals('e', d);

        Character e = state.next();
        assertEquals('l',e);

        Character f = state.next();
        assertEquals('l', f);

        Character g = state.next();
        assertEquals('o', g);

    }

} 
