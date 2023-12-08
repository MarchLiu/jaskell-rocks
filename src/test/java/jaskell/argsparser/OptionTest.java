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
                .validator((value, values) -> true);
        assertEquals("name", option.getName());
    }

    @Test
    public void testArgString() {
        Option option = Option.create("name")
                .defaultValue("defaultValue")
                .validator((value, values) -> true);
        assertEquals("--name", option.argString());
    }

    @Test
    public void testGetDefaultValue() {
        Option option = Option.create("name")
                .defaultValue("defaultValue")
                .validator((value, values) -> true);
        assertEquals("defaultValue", option.getDefaultValue());
    }

    @Test
    public void testGetHelp() {
        Option option = Option.create("name")
                .defaultValue("defaultValue")
                .help("helpDocument")
                .validator((value, values) -> true);
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
        BiFunction<String, TreeSet<String>, Boolean> validator = (value, values) -> true;
        Option option = Option.create("name").defaultValue("defaultValue").validator(validator);
        assertEquals(validator, option.getValidator());
        assertTrue(option.validate("anything", new TreeSet<>()));
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
        BiFunction<String, TreeSet<String>, Boolean> validator = (x, xs) -> x.equals("newValidator");
        Option option = Option.create("name").defaultValue("defaultValue")
                        .validator(validator);
        assertEquals(validator, option.getValidator());
    }

    @Test
    public void testValidate() {
        BiFunction<String, TreeSet<String>, Boolean> validator = (x, xs) -> x.equals("newValidator");
        Option option = Option.create("name").defaultValue("defaultValue")
                .validator(validator);
        assertTrue(option.validate("newValidator", new TreeSet<>()));
    }

    @Test
    public void testValidateState() {
        BiFunction<String, TreeSet<String>, Boolean> validator = (x, xs) -> x.equals("newValidator") && !xs.contains(x);
        Option option = Option.create("name").defaultValue("defaultValue")
                .validator(validator);
        assertTrue(option.validate("newValidator", new TreeSet<>()));
        TreeSet<String> values = new TreeSet<>();
        values.add("newValidator");
        assertFalse(option.validate("newValidator", values));
    }

    @Test
    public void testOption() {
        Option option1 = Option.create("name").defaultValue("defaultValue");
        Option option2 = Option.create("name").defaultValue("defaultValue");
        assertEquals(option1.getName(), option2.getName());
    }
}