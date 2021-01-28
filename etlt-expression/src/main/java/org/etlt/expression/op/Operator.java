/**
 *
 */
package org.etlt.expression.op;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.etlt.expression.IllegalExpressionException;
import org.etlt.expression.datameta.BaseDataMeta;
import org.etlt.expression.datameta.Literal;
import org.etlt.expression.op.define.Op_AND;
import org.etlt.expression.op.define.Op_APPEND;
import org.etlt.expression.op.define.Op_COLON;
import org.etlt.expression.op.define.Op_DIV;
import org.etlt.expression.op.define.Op_EQ;
import org.etlt.expression.op.define.Op_GE;
import org.etlt.expression.op.define.Op_GT;
import org.etlt.expression.op.define.Op_LE;
import org.etlt.expression.op.define.Op_LT;
import org.etlt.expression.op.define.Op_MINUS;
import org.etlt.expression.op.define.Op_MOD;
import org.etlt.expression.op.define.Op_MUTI;
import org.etlt.expression.op.define.Op_NEQ;
import org.etlt.expression.op.define.Op_NG;
import org.etlt.expression.op.define.Op_NOT;
import org.etlt.expression.op.define.Op_OR;
import org.etlt.expression.op.define.Op_PLUS;
import org.etlt.expression.op.define.Op_QUES;
import org.etlt.expression.op.define.Op_SELECT;

/**
 * supported operators are:
 * !,-(negative),*,/,%,+,-(minus)<br>
 *     <, <=,<,<=<br>
 *         ==, !=, &&,||<br>
 *             ?:(three factors select)<br>
 *                 #(insert into list>
 * @version 2.0
 */
public enum Operator {

    //逻辑否
    NOT("!", 80, 1),
    //取负
    NG("-", 80, 1),

    //算术乘
    MUTI("*", 70, 2),
    //算术除
    DIV("/", 70, 2),
    //算术除
    MOD("%", 70, 2),

    //算术加
    PLUS("+", 60, 2),
    //算术减
    MINUS("-", 60, 2),


    //逻辑小于
    LT("<", 50, 2),
    //逻辑小等于
    LE("<=", 50, 2),
    //逻辑大于
    GT(">", 50, 2),
    //逻辑大等于
    GE(">=", 50, 2),

    //逻辑等
    EQ("==", 40, 2),
    //逻辑不等
    NEQ("!=", 40, 2),

    //逻辑与
    AND("&&", 30, 2),

    //逻辑或
    OR("||", 20, 2),

    //集合添加
    APPEND("#", 10, 2),


    //三元选择
    QUES("?", 0, 0),
    COLON(":", 0, 0),
    SELECT("?:", 0, 3);

    private static final Set<String> OP_RESERVE_WORD = new HashSet<String>();

    static {

        OP_RESERVE_WORD.add(NOT.getToken());
        OP_RESERVE_WORD.add(NG.getToken());

        OP_RESERVE_WORD.add(MUTI.getToken());
        OP_RESERVE_WORD.add(DIV.getToken());
        OP_RESERVE_WORD.add(MOD.getToken());

        OP_RESERVE_WORD.add(PLUS.getToken());
        OP_RESERVE_WORD.add(MINUS.getToken());


        OP_RESERVE_WORD.add(LT.getToken());
        OP_RESERVE_WORD.add(LE.getToken());
        OP_RESERVE_WORD.add(GT.getToken());
        OP_RESERVE_WORD.add(GE.getToken());

        OP_RESERVE_WORD.add(EQ.getToken());
        OP_RESERVE_WORD.add(NEQ.getToken());

        OP_RESERVE_WORD.add(AND.getToken());

        OP_RESERVE_WORD.add(OR.getToken());

        OP_RESERVE_WORD.add(APPEND.getToken());

        OP_RESERVE_WORD.add(SELECT.getToken());
        OP_RESERVE_WORD.add(QUES.getToken());
        OP_RESERVE_WORD.add(COLON.getToken());
    }

    private static final HashMap<Operator, IOperatorExecution> OP_EXEC_MAP
            = new HashMap<Operator, IOperatorExecution>();

    static {

        OP_EXEC_MAP.put(NOT, new Op_NOT());
        OP_EXEC_MAP.put(NG, new Op_NG());

        OP_EXEC_MAP.put(MUTI, new Op_MUTI());
        OP_EXEC_MAP.put(DIV, new Op_DIV());
        OP_EXEC_MAP.put(MOD, new Op_MOD());

        OP_EXEC_MAP.put(PLUS, new Op_PLUS());
        OP_EXEC_MAP.put(MINUS, new Op_MINUS());

        OP_EXEC_MAP.put(LT, new Op_LT());
        OP_EXEC_MAP.put(LE, new Op_LE());
        OP_EXEC_MAP.put(GT, new Op_GT());
        OP_EXEC_MAP.put(GE, new Op_GE());

        OP_EXEC_MAP.put(EQ, new Op_EQ());
        OP_EXEC_MAP.put(NEQ, new Op_NEQ());

        OP_EXEC_MAP.put(AND, new Op_AND());

        OP_EXEC_MAP.put(OR, new Op_OR());

        OP_EXEC_MAP.put(APPEND, new Op_APPEND());

        OP_EXEC_MAP.put(SELECT, new Op_SELECT());
        OP_EXEC_MAP.put(QUES, new Op_QUES());
        OP_EXEC_MAP.put(COLON, new Op_COLON());

    }


    /**
     * An operator that determines whether a string is legal
     * @param tokenText
     * @return
     */
    public static boolean isLegalOperatorToken(String tokenText) {
        return OP_RESERVE_WORD.contains(tokenText);
    }

    private String token;

    private int priority;

    private int opType;

    Operator(String token, int priority, int opType) {
        this.token = token;
        this.priority = priority;
        this.opType = opType;
    }

    /**
     * Gets the character representation of the operator
     * for example：+  - equals && ==
     * @return String
     */
    public String getToken() {
        return this.token;
    }

    /**
     * Gets the priority of the operator
     * @return int
     */
    public int getPriority() {
        return this.priority;
    }

    /**
     * Operator type
     * unitary ！
     * binary && >=
     * @return int
     */
    public int getOpType() {
        return this.opType;
    }

    /**
     * Execute the operation and return the result token
     * @param args
     * @return Constant
     */
    public Literal execute(Literal[] args) throws IllegalExpressionException {
        IOperatorExecution opExec = OP_EXEC_MAP.get(this);
        if (opExec == null) {
            throw new IllegalExpressionException("operator not found: " + this.token);
        }
        return opExec.execute(args);
    }

    /**
     * Check whether operators and parameters are legal and executable
     *If it is legal, the token containing the execution result type is returned
     *If it is illegal, null is returned
     * @param opPosition Operator position
     * @param args
     * @return Constant
     */
    public Literal verify(int opPosition, BaseDataMeta[] args) throws IllegalExpressionException {
        IOperatorExecution opExec = OP_EXEC_MAP.get(this);
        if (opExec == null) {
            throw new IllegalExpressionException("operator not found: " + this.token);
        }
        return opExec.verify(opPosition, args);
    }

}
