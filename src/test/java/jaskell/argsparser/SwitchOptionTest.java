package jaskell.argsparser;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SwitchOptionTest {

    @Test
    public void testName() {
        SwitchOption option = SwitchOption.create("name").help("help message");
        assertEquals("name", option.getName());
    }

    @Test
    public void testHelp() {
        SwitchOption option = SwitchOption.create("name").help("help message");
        assertEquals("help message", option.getHelp());
    }

    @Test
    public void testDefaultValue() {
        SwitchOption option = SwitchOption.create("name").help("help message").defaultValue(false);
        assertFalse(option.isDefaultValue());
    }

    @Test
    public void testRequired() {
        SwitchOption option = SwitchOption.create("name").help("help message").required(true);
        assertTrue(option.isRequired());
    }

    @Test
    public void testArgString() {
        SwitchOption option = SwitchOption.create("name")
                .help("help message")
                .defaultValue(false)
                .required(false);
        assertEquals("--<enable|disable>-name", option.argString());
    }

    @Test
    public void testHelpMethod() {
        SwitchOption option = SwitchOption.create("name")
                .help("help message");
        option.help("new help message");
        assertEquals("new help message", option.getHelp());
    }

    @Test
    public void testEnableMethod() {
        SwitchOption option = new SwitchOption("name", "help message", false, false);
        option.enable();
        assertTrue(option.isDefaultValue());
    }

    @Test
    public void testDisableMethod() {
        SwitchOption option = new SwitchOption("name", "help message", true, false);
        option.disable();

        assertFalse(option.isDefaultValue());
    }

    @Test
    public void testRequiredMethod() {
        SwitchOption option = new SwitchOption("name", "help message", false, false);
        option.required(true);

        assertTrue(option.isRequired());
    }

    @Test
    public void testDefaultValueMethod() {
        SwitchOption option = new SwitchOption("name", "help message", true, false);
        option.defaultValue(false);

        assertFalse(option.isDefaultValue());
    }
}