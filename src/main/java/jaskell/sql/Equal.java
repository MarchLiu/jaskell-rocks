package jaskell.sql;

public class Equal extends Binary {
    @Override
    protected String operator() {
        return " = ";
    }
}
