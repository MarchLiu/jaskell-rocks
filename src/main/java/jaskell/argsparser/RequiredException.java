package jaskell.argsparser;

public class RequiredException extends Exception {
    private final String name;
    private final String type;

    public RequiredException(String type, String name) {
        super("%s named [%s] is required".formatted(type, name));
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
