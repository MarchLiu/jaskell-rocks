package jaskell.argsparser;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class Parameter implements Arg {
    private String help;
    final private String name;
    private String defaultValue;
    private Predicate<String> validator;
    private boolean required;

    public String getDefaultValue() {
        return defaultValue;
    }

    public String getHelp() {
        return Objects.requireNonNullElse(help, "");
    }

    public Predicate<String> getValidator() {
        return validator;
    }

    public boolean isRequired() {
        return required;
    }

    public String getName() {
        return name;
    }

    public Parameter(String name, String help, String defaultValue, boolean required,  Predicate<String> validator) {
        this.name = name;
        this.help = help;
        this.validator = validator;
        this.required = required;
        this.defaultValue = defaultValue;
    }

    @Override
    public String argString() {
        if (required) {
            return String.format("<%s>", name);
        } else {
            return String.format("[%s]", name) ;
        }
    }

    public Parameter help(String value) {
        this.help = value;
        return this;
    }

    public Parameter required(boolean value) {
        this.required = value;
        return this;
    }

    public Parameter validator(Predicate<String> validator) {
        this.validator = validator;
        return this;
    }

    public Parameter defaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public Parameter validator(String regex) {
        Pattern compiled = Pattern.compile(regex);
        this.validator = compiled.asMatchPredicate();
        return this;
    }

    public boolean validate(String value) {
        return this.validator.test(value);
    }

    public static Parameter create(String name) {
        return new Parameter(name, null, null, false, x->true);
    }

}
