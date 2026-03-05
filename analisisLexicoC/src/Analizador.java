package analisisLexicoC.src;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Analizador {
    public static void main(String[] args) {
        String fuente = "IF imp_bru <= 20000 THEN imp_net := imp_bru ELSE imp_net := imp_bru - ( 15 * imp_bru ) ; WRITE ( 'Importe a pagar' ) ; WRITE ( imp_net )";
        
        System.out.println("CÓDIGO FUENTE:\n" + fuente + "\n");
        
        List<Token> tokens = escanear(fuente);

        // PASO 1: Componentes (Lexemas)
        System.out.println("--- PASO 1 (Componentes/Lexemas) ---");
        tokens.forEach(t -> System.out.print("[" + t.lexema() + "] "));
        System.out.println("\n");

        // PASO 2: Asignación Técnica
        System.out.println("--- PASO 2 (Asignación Técnica) ---");
        for (Token t : tokens) {
            if (t.tipo().equals("ID") || t.tipo().equals("CTE") || t.tipo().equals("CADENA")) {
                // Para estos, ponemos la categoría (ID, CTE, CADENA)
                System.out.print("[" + t.tipo() + " = " + t.id() + "] ");
            } else {
                
                System.out.print("[\"" + t.lexema() + "\" = " + t.id() + "] ");
            }
        }
        System.out.println("\n");

        // PASO 3: Secuencia Numérica
        System.out.println("--- PASO 3 (Secuencia de números) ---");
        tokens.forEach(t -> System.out.print("[" + t.id() + "] "));
        System.out.println("\n");

        // PASO 4: Token-Atributo 
        System.out.println("--- PASO 4 (Pares Token-Atributo) ---");
        tokens.forEach(t -> System.out.print(t + " "));
        System.out.println("\n");
    }

    public static List<Token> escanear(String entrada) {
        List<Token> tokens = new ArrayList<>();
        String regex = "'[^']*'|<=|:=|\\w+|[\\(\\);\\-\\*]";
        Matcher matcher = Pattern.compile(regex).matcher(entrada);

        while (matcher.find()) {
            String lexema = matcher.group();
            
            if (lexema.equalsIgnoreCase("IF"))         tokens.add(new Token(59, "IF", lexema));
            else if (lexema.equalsIgnoreCase("THEN"))  tokens.add(new Token(60, "THEN", lexema));
            else if (lexema.equalsIgnoreCase("ELSE"))  tokens.add(new Token(61, "ELSE", lexema));
            else if (lexema.equalsIgnoreCase("WRITE")) tokens.add(new Token(62, "WRITE", lexema));
            else if (lexema.equals("<="))              tokens.add(new Token(81, "OP_REL", lexema));
            else if (lexema.equals(":="))              tokens.add(new Token(85, "ASIG", lexema));
            else if (lexema.equals("-"))               tokens.add(new Token(71, "RESTA", lexema));
            else if (lexema.equals("*"))               tokens.add(new Token(72, "MULT", lexema));
            else if (lexema.equals("("))               tokens.add(new Token(74, "PAR_A", lexema));
            else if (lexema.equals(")"))               tokens.add(new Token(75, "PAR_C", lexema));
            else if (lexema.equals(";"))               tokens.add(new Token(76, "PYC", lexema));
            else if (lexema.matches("\\d+"))           tokens.add(new Token(28, "CTE", lexema));
            else if (lexema.startsWith("'"))           tokens.add(new Token(29, "CADENA", lexema));
            else                                       tokens.add(new Token(27, "ID", lexema));
        }
        return tokens;
    }
}