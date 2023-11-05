package jaskell.sql;

public class Or extends Binary {
    @Override
    protected String operator() {
        return " OR ";
    }
}
