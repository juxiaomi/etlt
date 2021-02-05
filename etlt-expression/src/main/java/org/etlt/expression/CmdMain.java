package org.etlt.expression;

import org.etlt.expression.function.FunctionFactory;
import org.etlt.expression.function.FunctionInvoker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CmdMain {

    public static final String COMMAND_PREFIX = "\\";

    public static final String NEXT_LINE = "\r\n";

    public static void main(String[] args) throws IOException {
        CmdMain main = new CmdMain();
        main.initCommanders();
        main.hint();
        for (String input = main.readLine(); input != null; input = main.readLine()) {
            if (main.isCommand(input)) {
                Commander commander = main.findCommander(input);
                if (commander == null) {
                    System.out.println("commander not found: " + input);
                } else {
                    commander.execute();
                }
            } else {
                try {
                    System.out.println(main.compiler.evaluate(input));
                } catch (IllegalExpressionException e) {
                    e.printStackTrace();
                }
            }
            main.hint();
        }
    }

    private ExpressionCompiler compiler = new ExpressionCompiler();

    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public String readLine() throws IOException {
        for (String input = reader.readLine(); input != null; input = reader.readLine()) {
            return input;
        }
        return null;
    }

    private boolean isCommand(String text) {
        return text.trim().startsWith(COMMAND_PREFIX);
    }

    private void hint() {
        System.out.print("please input expression or command (started with \\):");
    }

    public void initCommanders() {
        // -- help commander
        Commander help = (new Commander("help", () -> {
            StringBuilder stringBuilder = new StringBuilder();
            commanders.values().forEach((e) -> stringBuilder.append(e).append(NEXT_LINE));
            System.out.println(stringBuilder.toString());
        }));
        help.helpMessage = "show help message";
        addCommander(help);
        // -- functions
        Commander listFunctions = (new Commander("functions", () -> {
            StringBuilder stringBuilder = new StringBuilder();
            FunctionFactory functionFactory = FunctionFactory.getInstance();
            List<FunctionInvoker> functionInvokers = functionFactory.getAllFunctions();
            functionInvokers.forEach((e) ->{
                stringBuilder.append(e.getName().toUpperCase()).append(" - ").append(e.getHelp()).append(NEXT_LINE);
            });
            System.out.println(stringBuilder.toString());
        }));
        listFunctions.helpMessage = "show all functions";
        addCommander(listFunctions);
    }

    private void addCommander(Commander commander) {
        this.commanders.put(commander.text, commander);
    }

    protected Commander findCommander(String text) {
        String com = text.trim();
        if (com.startsWith(COMMAND_PREFIX))
            com = com.substring(1);
        return this.commanders.get(com);
    }

    private Map<String, Commander> commanders = new HashMap<String, Commander>();

    static class Commander {
        Commander(String text, Runnable runnable) {
            this.text = text;
            this.runnable = runnable;
        }

        final String text;

        final Runnable runnable;

        String helpMessage;

        public void execute() {
            this.runnable.run();
        }

        public String toString() {
            return this.text + " - " + this.helpMessage;
        }
    }
}
