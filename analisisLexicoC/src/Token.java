package analisisLexicoC.src;

public record Token(int id, String tipo, String lexema) {
    @Override
    public String toString() {
        
        if (id == 27 || id == 28 || id == 29) {
            return "[" + id + ", '" + lexema + "']";
        }
       
        return "[" + id + "]";
    }
}