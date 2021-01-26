/**
 *
 */
package org.etlt.expression.op.define;

import java.util.ArrayList;
import java.util.List;

import org.etlt.expression.IllegalExpressionException;
import org.etlt.expression.datameta.BaseDataMeta;
import org.etlt.expression.datameta.Literal;
import org.etlt.expression.datameta.Reference;
import org.etlt.expression.op.IOperatorExecution;
import org.etlt.expression.op.Operator;

/**
 * 集合添加操作
 * @version 2.0
 */
public class Op_APPEND implements IOperatorExecution {

    public static final Operator THIS_OPERATOR = Operator.APPEND;

    /* (non-Javadoc)
     * @see org.wltea.expression.op.IOperatorExecution#execute(org.wltea.expression.ExpressionToken[])
     */
    public Literal execute(Literal[] args) throws IllegalExpressionException {

        if (args == null || args.length != 2) {
            throw new IllegalArgumentException("操作符\"" + THIS_OPERATOR.getToken() + "参数个数不匹配");
        }
        Literal first = args[1];
        Literal second = args[0];
        if (first == null || second == null) {
            throw new NullPointerException("操作符\"" + THIS_OPERATOR.getToken() + "\"参数为空");
        }
        //如果第一参数为引用，则执行引用
        if (first.isReference()) {
            Reference firstRef = (Reference) first.getDataValue();
            first = firstRef.execute();
        }
        //如果第二参数为引用，则执行引用
        if (second.isReference()) {
            Reference secondRef = (Reference) second.getDataValue();
            second = secondRef.execute();
        }
        return append(first, second);
    }

    /* (non-Javadoc)
     * @see org.wltea.expression.op.IOperatorExecution#verify(int, org.wltea.expression.ExpressionToken[])
     */
    public Literal verify(int opPositin, BaseDataMeta[] args)
            throws IllegalExpressionException {

        if (args == null) {
            throw new IllegalArgumentException("运算操作符参数为空");
        }
        if (args.length != 2) {
            //抛异常
            throw new IllegalExpressionException("操作符\"" + THIS_OPERATOR.getToken() + "\"参数个数不匹配"
                    , THIS_OPERATOR.getToken()
                    , opPositin
            );
        }
        BaseDataMeta first = args[1];
        BaseDataMeta second = args[0];

        if (first == null || second == null) {
            throw new NullPointerException("操作符\"" + THIS_OPERATOR.getToken() + "\"参数为空");
        }
        //APPEND接受任何类型的参数，总是返回Collection类型的常量
        return new Literal(BaseDataMeta.DataType.DATATYPE_LIST, null);

    }

    /**
     * 合并两个常量对象
     * @param arg1
     * @param arg2
     */
    private Literal append(Literal arg1, Literal arg2) {
        if (arg1 == null || arg2 == null) {
            throw new IllegalArgumentException("操作符\"" + THIS_OPERATOR.getToken() + "\"参数丢失");
        }

        List<Object> resultCollection = new ArrayList<Object>();
        //合并参数一
        if (BaseDataMeta.DataType.DATATYPE_LIST == arg1.getDataType()) {
            if (arg1.getCollection() != null) {
                resultCollection.addAll(arg1.getCollection());
            }
        } else {
            Object object = arg1.toJavaObject();
            resultCollection.add(object);
        }
        //合并参数二
        if (BaseDataMeta.DataType.DATATYPE_LIST == arg2.getDataType()) {
            if (arg2.getCollection() != null) {
                resultCollection.addAll(arg2.getCollection());
            }
        } else {
            Object object = arg2.toJavaObject();
            resultCollection.add(object);
        }

        //构造新的collection 常量
        Literal result = new Literal(BaseDataMeta.DataType.DATATYPE_LIST, resultCollection);
        return result;
    }

}
