package jaskell.argsparser;

public class EmptyOptionException extends Exception {
    private final String name;
    public EmptyOptionException(String name) {
        super("value of option %s not found".formatted(name));
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
