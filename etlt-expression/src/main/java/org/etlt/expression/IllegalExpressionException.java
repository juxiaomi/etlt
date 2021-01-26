/**
 *
 */
package org.etlt.expression;

/**
 * Illegal expression exception
 * @version 2.0
 */
public class IllegalExpressionException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -382075370364295450L;

    /**
     * error token
     */
    private String errorTokenText;
    /**
     * error position
     */
    private int errorPosition = -1;

    public IllegalExpressionException() {
        super();
    }

    public IllegalExpressionException(String msg) {
        super(msg);
    }

    public IllegalExpressionException(String message, Throwable cause){
        super(message, cause);
    }

    public IllegalExpressionException(Throwable cause) {
        super(cause);
    }

    public IllegalExpressionException(String msg, String errorTokenText) {
        super(msg);
        this.errorTokenText = errorTokenText;
    }

    public IllegalExpressionException(String msg, String errorTokenText, int errorPosition) {
        super(msg);
        this.errorPosition = errorPosition;
        this.errorTokenText = errorTokenText;
    }

    public String getErrorTokenText() {
        return errorTokenText;
    }

    public void setErrorTokenText(String errorTokenText) {
        this.errorTokenText = errorTokenText;
    }

    public int getErrorPosition() {
        return errorPosition;
    }

    public void setErrorPosition(int errorPosition) {
        this.errorPosition = errorPosition;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(this.getMessage());
        if (this.errorTokenText != null)
            sb.append(" symbol:").append(errorTokenText);
        if (this.errorPosition != -1)
            sb.append(" position:").append(errorPosition == -1 ? " unknown " : errorPosition);
        return sb.toString();
    }
}
