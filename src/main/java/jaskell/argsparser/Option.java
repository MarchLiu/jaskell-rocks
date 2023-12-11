package jaskell.argsparser;

import java.util.Objects;
import java.util.TreeSet;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class Option implements Arg {
    private final String name;
    private String defaultValue;
    private String help;
    private Predicate<String> validator;
    private boolean required;

    public Option(String name, String defaultValue, String help, boolean required,
                  Predicate<String> validator) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.help = help;
        this.required = required;
        this.validator = validator;
    }

    public String getName() {
        return name;
    }

    @Override
    public String argString() {
        return "--" + name;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public String getHelp() {
        return Objects.requireNonNullElse(help, "");
    }

    public boolean isRequired() {
        return required;
    }

    public Predicate<String> getValidator() {
        return validator;
    }

    public Option defaultValue(String value) {
        this.defaultValue = value;
        return this;
    }

    public Option help(String value) {
        this.help = value;
        return this;
    }

    public Option validator(Predicate<String> validator) {
        this.validator = validator;
        return this;
    }

    public Option validator(String regex) {
        Pattern compiled = Pattern.compile(regex);
        this.validator = compiled.asMatchPredicate();
        return this;
    }

    public Option required(boolean value) {
        this.required = value;
        return this;
    }

    public boolean validate(String value) {
        return this.validator.test(value);
    }

    public static Option create(String name) {
        return new Option(name, null, null, false, s -> true);
    }

}
