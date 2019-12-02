package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class JLang {

    static boolean hadError = false;

    public static void main(String[] args) throws IOException {
	// write your code here
        if (args.length > 1) {
            System.out.println("Usage: jLang [script]");
            System.exit(64);
        } else if (args.length == 1) {
            runFile(args[0]);
        } else {
            runPrompt();
        }
    }

    private static void runFile(String path) throws IOException{
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));

        // Indicate an error in the exit code
        if (hadError) System.exit(65);
    }

    public static void runPrompt() throws IOException{
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for(;;){
            System.out.println(">");
            run(reader.readLine());
            hadError = false;
        }
    }

    private static void run(String source){
            Scanner scanner = new Scanner(source);
            List<Token> tokens = scanner.scanTokens();

            for(Token token : tokens){
                System.out.println(token);
            }
    }

    static void error(int line, String message){
        report(line, ", ", message);
    }

    /**
     * The method reports the bugs in the line number of program i.e. function
     * Where ever an unexpected token is identified.
     * In the form -> [Line 165] Error function main(String args[],) <-- Here
     * @param line     - The line number
     * @param where    - The entity name for example - function name
     * @param message  - Issue
     */
    private static void report(String line, String where, String message){
        System.err.println(
                "[Line "+line + "] Error"+ where + ": "+ message
        );
    }
}
