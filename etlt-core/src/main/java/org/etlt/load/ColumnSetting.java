package org.etlt.load;

public class ColumnSetting {

    public static final char EQUAL = '=';

    public ColumnSetting(String expression) {
        int index = expression.indexOf(EQUAL);
        if (index != -1) {
            setName(expression.substring(0, index).trim());
            setExpression(expression.substring(index + 1).trim());
        } else
            throw new IllegalArgumentException("expression for column setting: " + expression);
    }

    public ColumnSetting(String name, String extractorName) {
        setName(name.trim());
        setExpression(extractorName.trim() + "." + name.trim());
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
