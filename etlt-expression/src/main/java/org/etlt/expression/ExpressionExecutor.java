/**
 *
 */
package org.etlt.expression;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.etlt.expression.datameta.BaseDataMeta;
import org.etlt.expression.datameta.Literal;
import org.etlt.expression.datameta.Reference;
import org.etlt.expression.datameta.Variable;
import org.etlt.expression.datameta.BaseDataMeta.DataType;
import org.etlt.expression.format.ExpressionParser;
import org.etlt.expression.format.reader.StringTypeReader;
import org.etlt.expression.function.FunctionExecution;
import org.etlt.expression.function.FunctionExecutor;
import org.etlt.expression.op.Operator;


/**
 * execution flow:
 * {@link #analyze(String)} -> {@link #compile(List, VariableContext)} (List)} -> {@link #execute(List)}
 * @version 2.0
 */
public class ExpressionExecutor {

    private FunctionExecution functionExecution = new FunctionExecution();

    public ExpressionExecutor() {
    }

    public ExpressionExecutor(FunctionExecutor functionExecutor) {
        getFunctionExecution().setFunctionExecutor(functionExecutor);
    }

    public FunctionExecution getFunctionExecution() {
        return functionExecution;
    }

    public void setFunctionExecution(FunctionExecution functionExecution) {
        this.functionExecution = functionExecution;
    }

    /**
     * Analyze the expression and convert it into a Token object queue
     * @param expression
     * @return
     */
    public List<ExpressionToken> analyze(String expression) throws IllegalExpressionException {
        ExpressionParser expressionParser = new ExpressionParser();
        return expressionParser.getExpressionTokens(expression);
    }

    /**
     * Convert normal expression word sequence into reverse Polish notation,
     * then check expression syntax
     * @param expTokens
     * @return
     */
    public List<ExpressionToken> compile(List<ExpressionToken> expTokens, VariableContext context) throws IllegalExpressionException {
        //1.初始化逆波兰式队列和操作符栈
        List<ExpressionToken> _RPNExpList = new ArrayList<ExpressionToken>();
        Stack<ExpressionToken> opStack = new Stack<ExpressionToken>();
        //初始化检查栈
        Stack<ExpressionToken> verifyStack = new Stack<ExpressionToken>();

        //2.出队列中从左向右依次便利token
        //2-1. 声明一个存储函数词元的临时变量
        ExpressionToken _function = null;

        for (ExpressionToken expToken : expTokens) {

            if (ExpressionToken.TokenType.TOKEN_TYPE_LITERAL == expToken.getTokenType()) {
                //读入一个常量，压入逆波兰式队列
                _RPNExpList.add(expToken);
                //同时压入校验栈
                verifyStack.push(expToken);

            } else if (ExpressionToken.TokenType.TOKEN_TYPE_VARIABLE == expToken.getTokenType()) {
                //if no context found, don't verify variable
                if (context != null) {
                    Variable var = context.getVariable(expToken.getVariable().getVariableName());
                    if (var == null) {
                        //当变量没有定义时，视为null型
                        expToken.getVariable().setDataType(DataType.DATATYPE_UNKNOWN);

                    } else if (var.getDataType() == null) {
                        throw new IllegalExpressionException("The expression is illegal, the variable \\\"\" + expToken.toString() + \"\\\" lacks definition; location:" + expToken.getStartPosition()
                                , expToken.toString()
                                , expToken.getStartPosition());
                    } else {
                        //设置Token中的变量类型定义
                        expToken.getVariable().setDataType(var.getDataType());
                    }
                }
                //读入一个变量，压入逆波兰式队列
                _RPNExpList.add(expToken);
                //同时压入校验栈
                verifyStack.push(expToken);


            } else if (ExpressionToken.TokenType.TOKEN_TYPE_OPERATOR == expToken.getTokenType()) {
                //读入一个操作符
                if (opStack.empty()) {//如果操作栈为空
                    if (Operator.COLON == expToken.getOperator()) {
                        //:操作符不可能单独出现，前面必须有对应的？
                        throw new IllegalExpressionException("When reading \":\", the corresponding \"?\" cannot be found in the operation stack. "
                                , expToken.toString()
                                , expToken.getStartPosition());

                    } else {
                        //一般操作符，则压入栈内；
                        opStack.push(expToken);
                    }
                } else {
                    boolean doPeek = true;
                    while (!opStack.empty() && doPeek) {
                        //如果栈不为空，则比较栈顶的操作符的优先级
                        ExpressionToken onTopOp = opStack.peek();

                        //如果栈顶元素是函数,直接将操作符压入栈
                        if (ExpressionToken.TokenType.TOKEN_TYPE_FUNCTION == onTopOp.getTokenType()) {
                            if (Operator.COLON == expToken.getOperator()) {
                                //:操作符不可能直接遇见函数，前面必须有对应的？
                                throw new IllegalExpressionException("When reading \":\", the corresponding \"?\" cannot be found in the operation stack."
                                        , expToken.toString()
                                        , expToken.getStartPosition());

                            } else {
                                opStack.push(expToken);
                                doPeek = false;
                            }

                        } else if (ExpressionToken.TokenType.TOKEN_TYPE_SPLITTER == onTopOp.getTokenType()
                                && "(".equals(onTopOp.getSplitter())) {//如果栈顶元素是(,直接将操作符压入栈
                            if (Operator.COLON == expToken.getOperator()) {
                                //:操作符不可能直接遇见(，前面必须有对应的？
                                throw new IllegalExpressionException("When reading \":\", the corresponding \"?\" cannot be found in the operation stack."
                                        , expToken.toString()
                                        , expToken.getStartPosition());

                            } else {
                                opStack.push(expToken);
                                doPeek = false;
                            }

                        } else if (ExpressionToken.TokenType.TOKEN_TYPE_OPERATOR == onTopOp.getTokenType()) {
                            //如果栈顶元素是操作符
                            if (expToken.getOperator().getPriority() > onTopOp.getOperator().getPriority()) {
                                if (Operator.COLON == expToken.getOperator()) {
                                    //注意：如果在后期的功能扩展中，存在有比:优先级更低的操作符
                                    //则必须在此处做出栈处理
                                } else {
                                    //当前操作符的优先级 > 栈顶操作符的优先级 ，则将当前操作符入站
                                    opStack.push(expToken);
                                    doPeek = false;
                                }

                            } else if (expToken.getOperator().getPriority() == onTopOp.getOperator().getPriority()) {
                                if (Operator.QUES == expToken.getOperator()) {
                                    //? , ?  -- >不弹出
                                    //?: , ? -- >不弹出
                                    opStack.push(expToken);
                                    doPeek = false;

                                } else if (Operator.COLON == expToken.getOperator()) {
                                    //? , : -- > 弹出？ ,将操作符转变成?: , 再压入栈
                                    if (Operator.QUES == onTopOp.getOperator()) {
                                        //弹出?
                                        opStack.pop();
                                        //将操作符转变成?:
                                        ExpressionToken opSelectToken = ExpressionToken.createOperatorToken(Operator.SELECT);
                                        opSelectToken.setStartPosition(onTopOp.getStartPosition());
                                        //再压入栈
                                        opStack.push(opSelectToken);
                                        doPeek = false;

                                    } else if (Operator.SELECT == onTopOp.getOperator()) { // ?: , : -->弹出?: ,执行校验
                                        //执行操作符校验
                                        ExpressionToken result = verifyOperator(onTopOp, verifyStack);
                                        //把校验结果压入检验栈
                                        verifyStack.push(result);
                                        //校验通过，弹出栈顶操作符，加入到逆波兰式队列
                                        opStack.pop();
                                        _RPNExpList.add(onTopOp);

                                    }
                                } else {
                                    //当前操作符的优先级 = 栈顶操作符的优先级,且执行顺序是从左到右的，则将栈顶的操作符弹出
                                    //执行操作符校验
                                    ExpressionToken result = verifyOperator(onTopOp, verifyStack);
                                    //把校验结果压入检验栈
                                    verifyStack.push(result);
                                    //校验通过，弹出栈顶操作符，加入到逆波兰式队列
                                    opStack.pop();
                                    _RPNExpList.add(onTopOp);

                                }
                            } else {
                                //当前操作符的优先级 < 栈顶操作符的优先级，则将栈顶的操作符弹出
                                //执行操作符校验
                                ExpressionToken result = verifyOperator(onTopOp, verifyStack);
                                //把校验结果压入检验栈
                                verifyStack.push(result);
                                //校验通过，弹出栈顶操作符，加入到逆波兰式队列
                                opStack.pop();
                                _RPNExpList.add(onTopOp);

                            }
                        }
                    }
                    //当前操作符的优先级 <= 栈内所有的操作符优先级
                    if (doPeek && opStack.empty()) {
                        if (Operator.COLON == expToken.getOperator()) {
                            //:操作符不可能直接入栈，前面必须有对应的？
                            throw new IllegalExpressionException("When reading \":\", the corresponding \"?\" cannot be found in the operation stack."
                                    , expToken.toString()
                                    , expToken.getStartPosition());

                        } else {
                            opStack.push(expToken);
                        }
                    }
                }

            } else if (ExpressionToken.TokenType.TOKEN_TYPE_FUNCTION == expToken.getTokenType()) {
                //遇到函数名称，则使用临时变量暂存下来，等待(的来临
                _function = expToken;

            } else if (ExpressionToken.TokenType.TOKEN_TYPE_SPLITTER == expToken.getTokenType()) {
                //处理读入的“(”
                if ("(".equals(expToken.getSplitter())) {
                    //如果此时_function != null,说明是函数的左括号
                    if (_function != null) {
                        //向逆波兰式队列压入"("
                        _RPNExpList.add(expToken);
                        //向校验栈压入
                        verifyStack.push(expToken);

                        //将"("及临时缓存的函数压入操作符栈,括号在前
                        opStack.push(expToken);
                        opStack.push(_function);

                        //清空临时变量
                        _function = null;

                    } else {
                        //说明是普通的表达式左括号
                        //将"("压入操作符栈
                        opStack.push(expToken);
                    }

                    //处理读入的“）”
                } else if (")".equals(expToken.getSplitter())) {

                    boolean doPop = true;

                    while (doPop && !opStack.empty()) {
                        // 从操作符栈顶弹出操作符或者函数，
                        ExpressionToken onTopOp = opStack.pop();
                        if (ExpressionToken.TokenType.TOKEN_TYPE_OPERATOR == onTopOp.getTokenType()) {
                            if (Operator.QUES == onTopOp.getOperator()) {
                                //)分割符不可能遇到？，这说明缺少：号
                                throw new IllegalExpressionException("When reading \")\", \"?\" is encountered in the operation stack, and the \":\" sign is missing"
                                        , onTopOp.toString()
                                        , onTopOp.getStartPosition());

                            } else {
                                //如果栈顶元素是普通操作符,执行操作符校验
                                ExpressionToken result = verifyOperator(onTopOp, verifyStack);
                                //把校验结果压入检验栈
                                verifyStack.push(result);

                                // 校验通过，则添加到逆波兰式对列
                                _RPNExpList.add(onTopOp);
                            }

                        } else if (ExpressionToken.TokenType.TOKEN_TYPE_FUNCTION == onTopOp.getTokenType()) {
                            // 如果遇到函数，则说明")"是函数的右括号
                            //执行函数校验
                            ExpressionToken result = verifyFunction(onTopOp, verifyStack);
                            //把校验结果压入检验栈
                            verifyStack.push(result);

                            //校验通过，添加")"到逆波兰式中
                            _RPNExpList.add(expToken);
                            //将函数加入逆波兰式
                            _RPNExpList.add(onTopOp);

                        } else if ("(".equals(onTopOp.getSplitter())) {
                            // 如果遇到"(", 则操作结束
                            doPop = false;
                        }
                    }

                    if (doPop && opStack.empty()) {
                        throw new IllegalExpressionException("when read \")\", not found \"(\" "
                                , expToken.getSplitter()
                                , expToken.getStartPosition());
                    }

                    //处理读入的“,”
                } else if (",".equals(expToken.getSplitter())) {
                    //依次弹出操作符栈中的所有操作符，压入逆波兰式队列，直到遇见函数词元
                    boolean doPeek = true;

                    while (!opStack.empty() && doPeek) {
                        ExpressionToken onTopOp = opStack.peek();

                        if (ExpressionToken.TokenType.TOKEN_TYPE_OPERATOR == onTopOp.getTokenType()) {
                            if (Operator.QUES == onTopOp.getOperator()) {
                                //,分割符不可能遇到？，这说明缺少：号
                                throw new IllegalExpressionException("found \",\", missing \"?\" , and \":\""
                                        , onTopOp.toString()
                                        , onTopOp.getStartPosition());

                            } else {
                                //弹出操作符栈顶的操作符
                                opStack.pop();
                                //执行操作符校验
                                ExpressionToken result = verifyOperator(onTopOp, verifyStack);
                                //把校验结果压入检验栈
                                verifyStack.push(result);
                                //校验通过，，压入逆波兰式队列
                                _RPNExpList.add(onTopOp);
                            }

                        } else if (ExpressionToken.TokenType.TOKEN_TYPE_FUNCTION == onTopOp.getTokenType()) {
                            //遇见函数词元,结束弹出
                            doPeek = false;

                        } else if (ExpressionToken.TokenType.TOKEN_TYPE_SPLITTER == onTopOp.getTokenType()
                                && "(".equals(onTopOp.getSplitter())) {
                            //在读入","时，操作符栈顶为"(",则报错
                            throw new IllegalExpressionException("found \",\"，\"(\",,missing, position：" + onTopOp.getStartPosition()
                                    , expToken.getSplitter()
                                    , expToken.getStartPosition());
                        }
                    }
                    //栈全部弹出，但没有遇见函数词元
                    if (doPeek && opStack.empty()) {
                        throw new IllegalExpressionException("found \",\", function name missing "
                                , expToken.getSplitter()
                                , expToken.getStartPosition());
                    }
                }
            }
        }

        //将操作栈内剩余的操作符逐一弹出，并压入逆波兰式队列
        while (!opStack.empty()) {
            ExpressionToken onTopOp = opStack.pop();

            if (ExpressionToken.TokenType.TOKEN_TYPE_OPERATOR == onTopOp.getTokenType()) {
                if (Operator.QUES == onTopOp.getOperator()) {
                    //遇到单一的剩下的？，这说明缺少：号
                    throw new IllegalExpressionException("\"?\" missing matched \":\""
                            , onTopOp.toString()
                            , onTopOp.getStartPosition());

                } else {
                    //执行操作符校验
                    ExpressionToken result = verifyOperator(onTopOp, verifyStack);
                    //把校验结果压入检验栈
                    verifyStack.push(result);

                    //校验成功,将操作符加入逆波兰式
                    _RPNExpList.add(onTopOp);
                }

            } else if (ExpressionToken.TokenType.TOKEN_TYPE_FUNCTION == onTopOp.getTokenType()) {
                //如果剩余是函数，则函数缺少右括号")"
                throw new IllegalExpressionException("function " + onTopOp.getFunctionName() + "missing \")\""
                        , onTopOp.getFunctionName()
                        , onTopOp.getStartPosition());

            } else if ("(".equals(onTopOp.getSplitter())) {
                //剩下的就只有“(”了，则说明表达式的算式缺少右括号")"
                throw new IllegalExpressionException("\"(\" missing matched \")\""
                        , onTopOp.getFunctionName()
                        , onTopOp.getStartPosition());
            }
        }

        //表达式校验完成，这是校验栈内应该只有一个结果,否则视为表达式不完成
        if (verifyStack.size() != 1) {
            StringBuffer errorBuffer = new StringBuffer();
            while (!verifyStack.empty()) {
                ExpressionToken onTop = verifyStack.pop();
                errorBuffer.append("\t").append(onTop.toString()).append("\r\n");
            }
            throw new IllegalExpressionException("not completed expression:" + errorBuffer);
        }

        return _RPNExpList;
    }


    /**
     * execute reverse Polish Notation
     * @return
     */
    @Deprecated
    public Literal execute(List<ExpressionToken> _RPNExpList) throws IllegalExpressionException {
        //初始化编译栈
        Stack<ExpressionToken> compileStack = new Stack<ExpressionToken>();

        for (ExpressionToken expressionToken : _RPNExpList) {

            if (ExpressionToken.TokenType.TOKEN_TYPE_LITERAL == expressionToken.getTokenType()) {
                //读取一个常量，压入栈
                compileStack.push(expressionToken);

            } else if (ExpressionToken.TokenType.TOKEN_TYPE_VARIABLE == expressionToken.getTokenType()) {
                //读取一个变量
                //从上下文获取变量的实际值，将其转化成常量Token，压入栈
                Variable varWithValue = VariableContainer.getVariable(expressionToken.getVariable().getVariableName());
                if (varWithValue != null) {
                    //生成一个有值常量，varWithValue.getDataValue有可能是空值
                    ExpressionToken constantToken = ExpressionToken.createLiteralToken(
                            varWithValue.getDataType()
                            , varWithValue.getDataValue());
                    compileStack.push(constantToken);

                } else {
                    //throw new IllegalStateException("变量\"" +expressionToken.getVariable().getVariableName() + "\"不是上下文合法变量" );
                    //当变量没有定义时，视为null型
                    ExpressionToken constantToken = ExpressionToken.createLiteralToken(
                            DataType.DATATYPE_UNKNOWN
                            , null);
                    compileStack.push(constantToken);
                }


            } else if (ExpressionToken.TokenType.TOKEN_TYPE_OPERATOR == expressionToken.getTokenType()) {
                Operator operator = expressionToken.getOperator();
                //判定几元操作符
                int opType = operator.getOpType();
                //取得相应的参数个数
                Literal[] args = new Literal[opType];
                ExpressionToken argToken = null;
                for (int i = 0; i < opType; i++) {
                    if (!compileStack.empty()) {
                        argToken = compileStack.pop();
                        if (ExpressionToken.TokenType.TOKEN_TYPE_LITERAL == argToken.getTokenType()) {
                            args[i] = argToken.getConstant();
                        } else {
                            //如果取出的Token不是常量，则抛出错误
                            throw new IllegalStateException("Operator" + operator.getToken() + "Unable to find corresponding parameter or insufficient number of parameters; location:" + +expressionToken.getStartPosition());
                        }
                    } else {
                        //栈已经弹空，没有取道操作符对应的操作数
                        throw new IllegalStateException("Operator" + operator.getToken() + "Unable to find corresponding parameter or insufficient number of parameters; location:" +
                                +expressionToken.getStartPosition());
                    }
                }
                //构造引用常量对象
                Reference ref = new Reference(getFunctionExecution(), expressionToken, args);
                ExpressionToken resultToken = ExpressionToken.createReference(ref);
                //将引用对象压入栈
                compileStack.push(resultToken);

            } else if (ExpressionToken.TokenType.TOKEN_TYPE_FUNCTION == expressionToken.getTokenType()) {

                if (!compileStack.empty()) {

                    ExpressionToken onTop = compileStack.pop();
                    //检查在遇到函数词元后，执行栈中弹出的第一个词元是否为“）”
                    if (")".equals(onTop.getSplitter())) {

                        boolean doPop = true;
                        List<Literal> argsList = new ArrayList<Literal>();
                        ExpressionToken parameter = null;
                        //弹出函数的参数，直到遇到"("时终止
                        while (doPop && !compileStack.empty()) {
                            parameter = compileStack.pop();

                            if (ExpressionToken.TokenType.TOKEN_TYPE_LITERAL == parameter.getTokenType()) {
                                argsList.add(parameter.getConstant());
                            } else if ("(".equals(parameter.getSplitter())) {
                                doPop = false;
                            } else {
                                //在函数中遇到的既不是常量，也不是"(",则报错
                                throw new IllegalStateException("function" + expressionToken.getFunctionName() + "Illegal parameter encountered during execution" +
                                        parameter.toString());
                            }
                        }

                        if (doPop && compileStack.empty()) {
                            //操作栈以空，没有找到函数的左括号（
                            throw new IllegalStateException("函数" + expressionToken.getFunctionName() + "执行时没有找到应有的\"(\"");
                        }

                        //执行函数
                        Literal[] arguments = new Literal[argsList.size()];
                        arguments = argsList.toArray(arguments);
                        //构造引用常量对象
                        Reference ref = new Reference(getFunctionExecution(), expressionToken, arguments);
                        ExpressionToken resultToken = ExpressionToken.createReference(ref);
                        //将引用对象压入栈
                        compileStack.push(resultToken);

                    } else {
                        //没有找到应该存在的右括号
                        throw new IllegalStateException("函数" + expressionToken.getFunctionName() + "执行时没有找到应有的\")\"");

                    }

                } else {
                    //没有找到应该存在的右括号
                    throw new IllegalStateException("函数" + expressionToken.getFunctionName() + "执行时没有找到应有的\")\"");
                }

            } else if (ExpressionToken.TokenType.TOKEN_TYPE_SPLITTER == expressionToken.getTokenType()) {
                //读取一个分割符，压入栈，通常是"("和")"
                compileStack.push(expressionToken);

            }
        }

        //表达式编译完成，这是编译栈内应该只有一个编译结果
        if (compileStack.size() == 1) {
            ExpressionToken token = compileStack.pop();
            Literal result = token.getConstant();
            //执行Reference常量
            if (result.isReference()) {
                Reference resultRef = (Reference) result.getDataValue();
                resultRef.setFunctionExecution(getFunctionExecution());
                return resultRef.execute(null);

            } else {
                //返回普通的常量
                return result;
            }
        } else {
            StringBuffer errorBuffer = new StringBuffer("\r\n");
            while (!compileStack.empty()) {
                ExpressionToken onTop = compileStack.pop();
                errorBuffer.append("\t").append(onTop.toString()).append("\r\n");
            }
            throw new IllegalStateException("Incomplete expression. Result status exception:" + errorBuffer);
        }
    }

    /**
     * execute reverse Polish Notation, with a context
     * @param context
     * @return
     */
    public Literal execute(List<ExpressionToken> _RPNExpList, VariableContext context) {
        //初始化编译栈
        Stack<ExpressionToken> compileStack = new Stack<ExpressionToken>();

        for (ExpressionToken expressionToken : _RPNExpList) {

            if (ExpressionToken.TokenType.TOKEN_TYPE_LITERAL == expressionToken.getTokenType()) {
                //读取一个常量，压入栈
                compileStack.push(expressionToken);

            } else if (ExpressionToken.TokenType.TOKEN_TYPE_VARIABLE == expressionToken.getTokenType()) {
                //读取一个变量
                //从上下文获取变量的实际值，将其转化成常量Token，压入栈
                Variable varWithValue = context.getVariable(expressionToken.getVariable().getVariableName());
                if (varWithValue != null) {
                    //生成一个有值常量，varWithValue.getDataValue有可能是空值
                    ExpressionToken constantToken = ExpressionToken.createLiteralToken(
                            varWithValue.getDataType()
                            , varWithValue.getDataValue());
                    compileStack.push(constantToken);

                } else {
                    //throw new IllegalStateException("变量\"" +expressionToken.getVariable().getVariableName() + "\"不是上下文合法变量" );
                    //当变量没有定义时，视为null型
                    ExpressionToken constantToken = ExpressionToken.createLiteralToken(
                            DataType.DATATYPE_UNKNOWN
                            , null);
                    compileStack.push(constantToken);
                }


            } else if (ExpressionToken.TokenType.TOKEN_TYPE_OPERATOR == expressionToken.getTokenType()) {
                Operator operator = expressionToken.getOperator();
                //判定几元操作符
                int opType = operator.getOpType();
                //取得相应的参数个数
                Literal[] args = new Literal[opType];
                ExpressionToken argToken = null;
                for (int i = 0; i < opType; i++) {
                    if (!compileStack.empty()) {
                        argToken = compileStack.pop();
                        if (ExpressionToken.TokenType.TOKEN_TYPE_LITERAL == argToken.getTokenType()) {
                            args[i] = argToken.getConstant();
                        } else {
                            //如果取出的Token不是常量，则抛出错误
                            throw new IllegalStateException("Operator" + operator.getToken() + "Unable to find corresponding parameter or insufficient number of parameters; location:" + +expressionToken.getStartPosition());
                        }
                    } else {
                        //栈已经弹空，没有取道操作符对应的操作数
                        throw new IllegalStateException("Operator" + operator.getToken() + "Unable to find corresponding parameter or insufficient number of parameters; location:" +
                                +expressionToken.getStartPosition());
                    }
                }
                //构造引用常量对象
                Reference ref = new Reference(getFunctionExecution(), expressionToken, args);
                ExpressionToken resultToken = ExpressionToken.createReference(ref);
                //将引用对象压入栈
                compileStack.push(resultToken);

            } else if (ExpressionToken.TokenType.TOKEN_TYPE_FUNCTION == expressionToken.getTokenType()) {

                if (!compileStack.empty()) {

                    ExpressionToken onTop = compileStack.pop();
                    //检查在遇到函数词元后，执行栈中弹出的第一个词元是否为“）”
                    if (")".equals(onTop.getSplitter())) {

                        boolean doPop = true;
                        List<Literal> argsList = new ArrayList<Literal>();
                        ExpressionToken parameter = null;
                        //弹出函数的参数，直到遇到"("时终止
                        while (doPop && !compileStack.empty()) {
                            parameter = compileStack.pop();

                            if (ExpressionToken.TokenType.TOKEN_TYPE_LITERAL == parameter.getTokenType()) {
                                argsList.add(parameter.getConstant());
                            } else if ("(".equals(parameter.getSplitter())) {
                                doPop = false;
                            } else {
                                //在函数中遇到的既不是常量，也不是"(",则报错
                                throw new IllegalStateException("function" + expressionToken.getFunctionName() + "Illegal parameter encountered during execution" +
                                        parameter.toString());
                            }
                        }

                        if (doPop && compileStack.empty()) {
                            //操作栈以空，没有找到函数的左括号（
                            throw new IllegalStateException("函数" + expressionToken.getFunctionName() + "执行时没有找到应有的\"(\"");
                        }

                        //执行函数
                        Literal[] arguments = new Literal[argsList.size()];
                        arguments = argsList.toArray(arguments);
                        //构造引用常量对象
                        Reference ref = new Reference(getFunctionExecution(), expressionToken, arguments);
                        ExpressionToken resultToken = ExpressionToken.createReference(ref);
                        //将引用对象压入栈
                        compileStack.push(resultToken);

                    } else {
                        //没有找到应该存在的右括号
                        throw new IllegalStateException("函数" + expressionToken.getFunctionName() + "执行时没有找到应有的\")\"");

                    }

                } else {
                    //没有找到应该存在的右括号
                    throw new IllegalStateException("函数" + expressionToken.getFunctionName() + "执行时没有找到应有的\")\"");
                }

            } else if (ExpressionToken.TokenType.TOKEN_TYPE_SPLITTER == expressionToken.getTokenType()) {
                //读取一个分割符，压入栈，通常是"("和")"
                compileStack.push(expressionToken);

            }
        }

        //表达式编译完成，这是编译栈内应该只有一个编译结果
        if (compileStack.size() == 1) {
            ExpressionToken token = compileStack.pop();
            Literal result = token.getConstant();
            //执行Reference常量
            if (result.isReference()) {
                Reference resultRef = (Reference) result.getDataValue();
                resultRef.setFunctionExecution(getFunctionExecution());
                return resultRef.execute(context);

            } else {
                //返回普通的常量
                return result;
            }
        } else {
            StringBuffer errorBuffer = new StringBuffer("\r\n");
            while (!compileStack.empty()) {
                ExpressionToken onTop = compileStack.pop();
                errorBuffer.append("\t").append(onTop.toString()).append("\r\n");
            }
            throw new IllegalStateException("Incomplete expression. Result status exception:" + errorBuffer);
        }
    }

    /**
     * Converts the expression word list to a string
     * @param tokenList
     * @return
     */
    public String tokensToString(List<ExpressionToken> tokenList) {
        StringBuffer expressionText = new StringBuffer();
        for (ExpressionToken token : tokenList) {

            ExpressionToken.TokenType tokenType = token.getTokenType();

            if (ExpressionToken.TokenType.TOKEN_TYPE_LITERAL == tokenType) {

                Literal c = token.getConstant();
                if (BaseDataMeta.DataType.DATATYPE_BOOLEAN == c.getDataType()) {
                    expressionText.append(c.getDataValueText()).append(" ");

                } else if (BaseDataMeta.DataType.DATATYPE_DATE == c.getDataType()) {
                    expressionText.append("[").append(c.getDataValueText()).append("] ");

                } else if (BaseDataMeta.DataType.DATATYPE_DOUBLE == c.getDataType()) {
                    expressionText.append(c.getDataValueText()).append(" ");

                } else if (BaseDataMeta.DataType.DATATYPE_FLOAT == c.getDataType()) {
                    expressionText.append(c.getDataValueText()).append("F ");

                } else if (BaseDataMeta.DataType.DATATYPE_INT == c.getDataType()) {
                    expressionText.append(c.getDataValueText()).append(" ");

                } else if (BaseDataMeta.DataType.DATATYPE_LONG == c.getDataType()) {
                    expressionText.append(c.getDataValueText()).append("L ");

                } else if (BaseDataMeta.DataType.DATATYPE_UNKNOWN == c.getDataType()) {
                    expressionText.append(c.getDataValueText()).append(" ");

                } else if (BaseDataMeta.DataType.DATATYPE_STRING == c.getDataType()) {
                    expressionText.append(StringTypeReader.START_MARK).append(c.getDataValueText()).append("' ");

                }

            } else if (ExpressionToken.TokenType.TOKEN_TYPE_VARIABLE == tokenType) {
                expressionText.append(token.getVariable().getVariableName()).append(" ");

            } else if (ExpressionToken.TokenType.TOKEN_TYPE_FUNCTION == tokenType) {
                expressionText.append('$').append(token.getFunctionName()).append(" ");

            } else if (ExpressionToken.TokenType.TOKEN_TYPE_OPERATOR == tokenType) {
                expressionText.append(token.getOperator().toString()).append(" ");

            } else if (ExpressionToken.TokenType.TOKEN_TYPE_SPLITTER == tokenType) {
                expressionText.append(token.getSplitter()).append(" ");

            }
        }
        return expressionText.toString();
    }

    /**
     * Convert the expression (formatted) into a list of words
     * @param tokenExpression
     * @return
     */
    public List<ExpressionToken> stringToTokens(String tokenExpression) throws IllegalExpressionException {
        List<ExpressionToken> tokens = new ArrayList<ExpressionToken>();

        char[] expChars = tokenExpression.toCharArray();
        //字符串扫描状态，0：普通 ； 1：日期 ； 2：字符串 3：转义符
        int status = 0;
        StringBuilder tokenBuffer = new StringBuilder();
        for (int i = 0; i < expChars.length; i++) {
            //读入空格
            if (' ' == expChars[i]) {
                if (status == 0) {
                    //一般情况下，读入空格，分割token
                    addToken(tokenBuffer.toString(), tokens);
                    //清空buffer
                    tokenBuffer.setLength(0);
                } else if (status == 1 || status == 2) {
                    tokenBuffer.append(expChars[i]);
                } else {
                    throw new IllegalExpressionException("非法的转义符\"" + expChars[i] + "\" ，位置：" + i);
                }
            } else if ('[' == expChars[i]) {//读入'['
                if (status == 0) {
                    status = 1;//进入日期
                    tokenBuffer.append(expChars[i]);
                } else if (status == 1) {
                    throw new IllegalExpressionException("非法的日期开始字符，位置：" + i);
                } else if (status == 2) {
                    tokenBuffer.append(expChars[i]);
                } else {
                    throw new IllegalExpressionException("非法的转义符\"" + expChars[i] + "\" ，位置：" + i);
                }

            } else if (']' == expChars[i]) {//读入']'
                if (status == 0) {
                    throw new IllegalExpressionException("非法的日期结束字符，位置：" + i);
                } else if (status == 1) {
                    status = 0; //离开日期
                    tokenBuffer.append(expChars[i]);

                } else if (status == 2) {
                    tokenBuffer.append(expChars[i]);
                } else {
                    throw new IllegalExpressionException("非法的转义符\"" + expChars[i] + "\" ，位置：" + i);
                }

            } else if (StringTypeReader.START_MARK == expChars[i]) {//读入'"'
                if (status == 0) {
                    status = 2;//进入字符串
                    tokenBuffer.append(expChars[i]);
                } else if (status == 1) {
                    throw new IllegalExpressionException("非法的日期字符\"" + expChars[i] + "\" ，位置：" + i);
                } else if (status == 2) {
                    status = 0; //离开字符串
                    tokenBuffer.append(expChars[i]);
                } else {
                    status = 2; //转义”号，离开转义，变为字符串状态
                    tokenBuffer.append(expChars[i]);
                }

            } else if ('\\' == expChars[i]) {//读入'\'
                if (status == 0) {
                    throw new IllegalExpressionException("非法的字符\"" + expChars[i] + "\" ，位置：" + i);
                } else if (status == 1) {
                    throw new IllegalExpressionException("非法的日期字符\"" + expChars[i] + "\" ，位置：" + i);
                } else if (status == 2) {
                    status = 3; //进入转义状态
                    tokenBuffer.append(expChars[i]);
                } else {
                    status = 2; //转义\号，离开转义，变为字符串状态
                    tokenBuffer.append(expChars[i]);
                }

            } else {//读入其他字符
                if (status == 0 || status == 1 || status == 2) {
                    tokenBuffer.append(expChars[i]);
                } else {
                    throw new IllegalExpressionException("非法的转义符\"" + expChars[i] + "\" ，位置：" + i);
                }
            }

        }

        tokenBuffer.trimToSize();
        if (tokenBuffer.length() > 0) {
            addToken(tokenBuffer.toString(), tokens);
        }
        return tokens;
    }

    /**
     * Convert the substring to token and add it to the list
     * @param tokenString
     * @param tokens
     * @throws IllegalExpressionException
     */
    private void addToken(String tokenString, List<ExpressionToken> tokens) throws IllegalExpressionException {

        ExpressionToken token = null;
        //null
        if (ExpressionTokenHelper.isNull(tokenString)) {
            token = ExpressionToken.createLiteralToken(BaseDataMeta.DataType.DATATYPE_UNKNOWN, null);
            tokens.add(token);

        } else
            //boolean
            if (ExpressionTokenHelper.isBoolean(tokenString)) {
                token = ExpressionToken.createLiteralToken(BaseDataMeta.DataType.DATATYPE_BOOLEAN, Boolean.valueOf(tokenString));
                tokens.add(token);

            } else
                //integer
                if (ExpressionTokenHelper.isInteger(tokenString)) {
                    token = ExpressionToken.createLiteralToken(BaseDataMeta.DataType.DATATYPE_INT, Integer.valueOf(tokenString));
                    tokens.add(token);

                } else
                    //long
                    if (ExpressionTokenHelper.isLong(tokenString)) {
                        token = ExpressionToken.createLiteralToken(BaseDataMeta.DataType.DATATYPE_LONG, Long.valueOf(tokenString.substring(0, tokenString.length() - 1)));
                        tokens.add(token);

                    } else
                        //float
                        if (ExpressionTokenHelper.isFloat(tokenString)) {
                            token = ExpressionToken.createLiteralToken(BaseDataMeta.DataType.DATATYPE_FLOAT, Float.valueOf(tokenString.substring(0, tokenString.length() - 1)));
                            tokens.add(token);

                        } else
                            //double
                            if (ExpressionTokenHelper.isDouble(tokenString)) {
                                token = ExpressionToken.createLiteralToken(BaseDataMeta.DataType.DATATYPE_DOUBLE, Double.valueOf(tokenString));
                                tokens.add(token);

                            } else
                                //Date
                                if (ExpressionTokenHelper.isDateTime(tokenString)) {
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    try {
                                        token = ExpressionToken.createLiteralToken(BaseDataMeta.DataType.DATATYPE_DATE, sdf.parse(tokenString.substring(1, tokenString.length() - 1)));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                        throw new IllegalExpressionException("日期参数格式错误");
                                    }
                                    tokens.add(token);

                                } else
                                    //String
                                    if (ExpressionTokenHelper.isString(tokenString)) {
                                        token = ExpressionToken.createLiteralToken(BaseDataMeta.DataType.DATATYPE_STRING, tokenString.substring(1, tokenString.length() - 1));
                                        tokens.add(token);
                                    } else
                                        //分割符
                                        if (ExpressionTokenHelper.isSplitor(tokenString)) {
                                            token = ExpressionToken.createSplitterToken(tokenString);
                                            tokens.add(token);

                                        } else
                                            //函数
                                            if (ExpressionTokenHelper.isFunction(tokenString)) {
                                                token = ExpressionToken.createFunctionToken(tokenString.substring(1, tokenString.length()));
                                                tokens.add(token);

                                            } else
                                                //操作符
                                                if (ExpressionTokenHelper.isOperator(tokenString)) {
                                                    Operator operator = Operator.valueOf(tokenString);
                                                    token = ExpressionToken.createOperatorToken(operator);
                                                    tokens.add(token);

                                                } else {
                                                    //剩下的都应该是变量，这个判断依赖于生成的RPN是正确的前提
                                                    //变量,在boolean型和null型判别后，只要是字母打头的，不是$的就是变量
                                                    token = ExpressionToken.createVariableToken(tokenString);
                                                    tokens.add(token);

                                                }
    }

    /**
     * Perform operator verification
     * @param opToken
     * @param verifyStack
     * @return
     */
    private ExpressionToken verifyOperator(ExpressionToken opToken, Stack<ExpressionToken> verifyStack) throws IllegalExpressionException {
        //判定几元操作符
        Operator op = opToken.getOperator();
        int opType = op.getOpType();
        //取得相应的参数个数
        BaseDataMeta[] args = new BaseDataMeta[opType];
        ExpressionToken argToken = null;
        for (int i = 0; i < opType; i++) {
            if (!verifyStack.empty()) {
                argToken = verifyStack.pop();

                if (ExpressionToken.TokenType.TOKEN_TYPE_LITERAL == argToken.getTokenType()) {
                    args[i] = argToken.getConstant();

                } else if (ExpressionToken.TokenType.TOKEN_TYPE_VARIABLE == argToken.getTokenType()) {
                    args[i] = argToken.getVariable();

                } else {
                    //如果取到的Token不是常量，也不是变量，则抛出错误
                    throw new IllegalExpressionException("表达式不合法，操作符\"" + op.getToken() + "\"参数错误;位置：" + argToken.getStartPosition()
                            , opToken.toString()
                            , opToken.getStartPosition());
                }

            } else {
                //栈已经弹空，没有取道操作符对应的操作数
                throw new IllegalExpressionException("表达式不合法，操作符\"" + op.getToken() + "\"找不到相应的参数，或参数个数不足;"
                        , opToken.toString()
                        , opToken.getStartPosition());
            }
        }
        //执行操作符校验，并返回校验
        Literal result = op.verify(opToken.getStartPosition(), args);
        return ExpressionToken.createLiteralToken(result);

    }

    /**
     * Perform function verification
     * @param functionToken
     * @param verifyStack
     * @return
     */
    private ExpressionToken verifyFunction(ExpressionToken functionToken, Stack<ExpressionToken> verifyStack) throws IllegalExpressionException {
        if (!verifyStack.empty()) {
            boolean doPop = true;
            List<BaseDataMeta> args = new ArrayList<BaseDataMeta>();
            ExpressionToken parameter = null;
            //弹出函数的参数，直到遇到"("时终止
            while (doPop && !verifyStack.empty()) {
                parameter = verifyStack.pop();
                if (ExpressionToken.TokenType.TOKEN_TYPE_LITERAL == parameter.getTokenType()) {
                    //常量
                    args.add(parameter.getConstant());
                } else if (ExpressionToken.TokenType.TOKEN_TYPE_VARIABLE == parameter.getTokenType()) {
                    args.add(parameter.getVariable());
                } else if ("(".equals(parameter.getSplitter())) {
                    doPop = false;
                } else {
                    //没有找到应该存在的右括号
                    throw new IllegalExpressionException("Illegal expression, invalid function " + functionToken.getFunctionName() + " illegal parameter encountered" + parameter.toString() + ";position:" + parameter.getStartPosition()
                            , functionToken.toString()
                            , functionToken.getStartPosition());
                }
            }
            if (doPop && verifyStack.empty()) {
                //操作栈以空，没有找到函数的左括号（
                throw new IllegalExpressionException("Illegal expression, invalid function " + functionToken.getFunctionName() + " missing\"(\", position:" + (functionToken.getStartPosition() + functionToken.toString().length())
                        , functionToken.toString()
                        , functionToken.getStartPosition());
            }
            //校验函数
            BaseDataMeta[] arguments = new BaseDataMeta[args.size()];
            arguments = args.toArray(arguments);
            Literal result = getFunctionExecution().verify(functionToken.getFunctionName(), functionToken.getStartPosition(), arguments);
            return ExpressionToken.createLiteralToken(result);
        } else {
            //没有找到应该存在的右括号
            throw new IllegalExpressionException("Illegal expression, invalid function " + functionToken.getFunctionName() + " incomplete"
                    , functionToken.toString()
                    , functionToken.getStartPosition());
        }
    }

}
