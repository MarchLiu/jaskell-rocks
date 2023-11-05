package jaskell.sql;

public class And extends Binary {
    @Override
    protected String operator() {
        return " AND ";
    }
}
