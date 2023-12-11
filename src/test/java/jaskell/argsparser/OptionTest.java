package jaskell.argsparser;


import org.junit.jupiter.api.Test;

import java.util.TreeSet;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;


public class OptionTest {
    @Test
    public void testGetName() {
        Option option = Option.create("name")
                .defaultValue("defaultValue")
                .validator(value -> true);
        assertEquals("name", option.getName());
    }

    @Test
    public void testArgString() {
        Option option = Option.create("name")
                .defaultValue("defaultValue")
                .validator(value -> true);
        assertEquals("--name", option.argString());
    }

    @Test
    public void testGetDefaultValue() {
        Option option = Option.create("name")
                .defaultValue("defaultValue")
                .validator(value -> true);
        assertEquals("defaultValue", option.getDefaultValue());
    }

    @Test
    public void testGetHelp() {
        Option option = Option.create("name")
                .defaultValue("defaultValue")
                .help("helpDocument")
                .validator(value -> true);
        assertEquals("helpDocument", option.getHelp());
    }

    @Test
    public void testIsRequired() {
        Option option = Option.create("name")
                .defaultValue("defaultValue");
        assertFalse(option.isRequired());
    }

    @Test
    public void testGetValidator() {
        Predicate<String> validator = value -> true;
        Option option = Option.create("name").defaultValue("defaultValue").validator(validator);
        assertEquals(validator, option.getValidator());
        assertTrue(option.validate("anything"));
    }

    @Test
    public void testDefaultValue() {
        Option option = Option.create("name").defaultValue("defaultValue")
                .defaultValue("newValue");
        assertEquals("newValue", option.getDefaultValue());
    }

    @Test
    public void testHelp() {
        Option option = Option.create("name").defaultValue("defaultValue")
                .help("newHelp");
        assertEquals("newHelp", option.getHelp());
    }

    @Test
    public void testValidator() {
        Predicate<String> validator = x -> x.equals("newValidator");
        Option option = Option.create("name").defaultValue("defaultValue")
                        .validator(validator);
        assertEquals(validator, option.getValidator());
    }

    @Test
    public void testValidate() {
        Predicate<String> validator = x -> x.equals("newValidator");
        Option option = Option.create("name").defaultValue("defaultValue")
                .validator(validator);
        assertTrue(option.validate("newValidator"));
    }

    @Test
    public void testOption() {
        Option option1 = Option.create("name").defaultValue("defaultValue");
        Option option2 = Option.create("name").defaultValue("defaultValue");
        assertEquals(option1.getName(), option2.getName());
    }
}