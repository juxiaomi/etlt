package org.etlt.load;

public class ColumnSetting {

    public static final char EQUAL = '=';

    public ColumnSetting(String expression){
        int index = expression.indexOf(EQUAL);
        setName(expression.substring(0, index).trim());
        setExpression(expression.substring(index + 1).trim());
    }

    private String name;

    private String expression;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}
