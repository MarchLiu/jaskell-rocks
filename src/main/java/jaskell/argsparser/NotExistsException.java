package jaskell.argsparser;

public class NotExistsException extends Exception {
    private final String name;
    private final String parameter;
    private final String type;
    public NotExistsException(String type, String name, String parameter) {
        super("%s named %s not exists but received %s".formatted(type, name, parameter));
        this.name = name;
        this.type = type;
        this.parameter = parameter;
    }

    public String getName() {
        return name;
    }

    public String getParameter() {
        return parameter;
    }

    public String getType() {
        return type;
    }
}
