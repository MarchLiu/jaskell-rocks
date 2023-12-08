package jaskell.argsparser;

import java.util.List;
import java.util.stream.Collectors;

public class ErrorsStack extends Exception {
    List<Exception> errors;

    public ErrorsStack(List<Exception> errors) {
        super(errors.stream().map(Exception::getMessage)
                .collect(Collectors.joining("\n")));
        this.errors = errors;
    }
}
