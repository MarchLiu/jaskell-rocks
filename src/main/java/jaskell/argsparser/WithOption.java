package jaskell.argsparser;

import java.util.Objects;

public class WithOption implements Arg {
    private String help;
    final private String name;
    private String argString;

    @Override
    public String argString() {
        return Objects.requireNonNullElseGet(argString, () -> "--with-" + name);
    }

    @Override
    public String getHelp() {
        return help;
    }


    public String getName() {
        return name;
    }

    public WithOption(String name, String help) {
        this.name = name;
        this.help = help;
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
