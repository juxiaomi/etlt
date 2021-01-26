package org.etlt.expression;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CmdMain {
    public static void main(String[] args) throws IOException {
        CmdMain main = new CmdMain();
        main.hint();
        for(String input = main.readLine(); input != null; input = main.readLine()){
            try {
                System.out.println(main.compiler.evaluate(input));
            }catch (IllegalExpressionException e){
                e.printStackTrace();
            }
            main.hint();
        }
    }

    private ExpressionCompiler compiler = new ExpressionCompiler();

    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public String readLine() throws IOException {
        for(String input = reader.readLine(); input !=null; input = reader.readLine()){
            return input;
        }
        return null;
    }

    private void hint(){
        System.out.print("please input expression:");
    }
}
