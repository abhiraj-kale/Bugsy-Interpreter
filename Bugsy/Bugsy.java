package Bugsy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Bugsy {
    static boolean hadError = false;

    public static void main(String[] args) throws IOException {
        if(args.length > 1 ){
            System.out.println("Usage bugsy [script]");
            System.exit(64);
        }else if(args.length==1)
            runFile(args[0]);
        else runPrompt();
    }
    private static void runFile(String path) throws IOException{
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
    }
    private static void runPrompt() throws IOException {
        InputStreamReader in = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(in);
        for(;;){
            System.out.print(">> ");
            String line = reader.readLine();
            run(line);
            hadError = false;
        }
    }
    private static void run(String source){
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        for(Token token : tokens){
            System.out.print(token+",");
        }
        if(hadError) System.exit(65);
    }
    static void error(int line, String message){
        report(line,"",message);
    }

    private static void report(int line, String where, String message){
        System.err.println("[line : " + line + "] Error"+where+": "+message);
        hadError = true;
    }
}
