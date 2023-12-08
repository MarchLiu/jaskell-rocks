package jaskell.argsparser;

public class ValidateFailedException extends Exception {
    private final String name;
    private final String value;
    private final String type;
    private final String help;


    public ValidateFailedException(String type, String name, String value, String help) {
        super("%s named %s has invalidate value %s: %s"
                .formatted(type, name, value, help));
        this.name = name;
        this.type = type;
        this.value = value;
        this.help = help;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public String getHelp() {
        return help;
    }
}
