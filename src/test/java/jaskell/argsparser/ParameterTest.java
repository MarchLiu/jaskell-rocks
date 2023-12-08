package jaskell.argsparser;


import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

public class ParameterTest {
    @Test
    public void testGetName() {
        String name = "name";
        Parameter parameter = new Parameter(name, null, null, false, x -> true);
        assertEquals(name, parameter.getName());
    }

    @Test
    public void testArgString() {
        String name = "name";
        Predicate<String> validator = x -> true;
        Parameter parameter = Parameter.create(name).validator(validator);
        assertEquals("[" + name + "]", parameter.argString());
        parameter.required(true);
        assertEquals("<" + name + ">", parameter.argString());
    }

    @Test
    public void testGetDefaultValue() {
        String name = "name";
        String defaultValue = "defaultValue";
        Parameter parameter = new Parameter(name, null, defaultValue, false, x -> true);
        assertEquals(defaultValue, parameter.getDefaultValue());
    }

    @Test
    public void testGetHelp() {
        String name = "name";
        String help = "help";
        Parameter parameter = new Parameter(name, help, null, false, x -> true);
        assertEquals(help, parameter.getHelp());
    }

    @Test
    public void testIsRequired() {
        String name = "name";
        Parameter parameter = new Parameter(name, null, null, false, x -> true);
        assertFalse(parameter.isRequired());
    }

    @Test
    public void testGetValidator() {
        String name = "name";
        Predicate<String> validator = x -> true;
        Parameter parameter = new Parameter(name, null, null, false, validator);
        assertEquals(validator, parameter.getValidator());
    }
}