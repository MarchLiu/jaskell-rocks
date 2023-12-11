package jaskell.argsparser;

import jaskell.util.TriFunction;

import java.util.function.BiFunction;

import java.util.Objects;
import java.util.Set;

public class WithOption implements Arg {
    private String help;
    final private String name;
    private String argString;
    private boolean preset = false;

    @Override
    public String argString() {
        return Objects.requireNonNullElseGet(argString, () -> "--<with|without>-" + name);
    }

    @Override
    public String getHelp() {
        return help;
    }


    public String getName() {
        return name;
    }

    public boolean getPreset() {
        return preset;
    }

    public WithOption(String name, String help) {
        this.name = name;
        this.help = help;
    }

    public WithOption preset() {
        this.preset = true;
        return this;
    }

    public WithOption unset() {
        this.preset = false;
        return this;
    }

    public WithOption preset(boolean value) {
        this.preset = value;
        return this;
    }

    public WithOption help(String value) {
        this.help = value;
        return this;
    }

    public WithOption argString(String value) {
        this.argString = value;
        return this;
    }

    public WithOption required(boolean value) {
        return this;
    }

    public static WithOption create(String name) {
        return new WithOption(name, null);
    }
}
