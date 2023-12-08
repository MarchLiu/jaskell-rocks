package jaskell.argsparser;

public class NoMoreSlotException extends Exception {
    private final String value;

    public NoMoreSlotException(String value) {
        super("no more slot for parameters, value ["
                + value
                + "] should be a varargs but program put it into parameters slots, maybe it is a bug");
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
