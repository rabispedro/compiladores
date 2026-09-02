package obj;

import obj.lexico.ClasseTokenOBJ;
import obj.lexico.LexicoOBJ;
import obj.lexico.Token;

public class App {
    public static void main(String[] args) {
        LexicoOBJ l = new LexicoOBJ("analisador_lexico_OBJ/cube.obj");
        Token t;

        // int cont = 0;
        do {
            t = l.getNextToken();
            System.out.println(t);
            // cont++;
        } while (t.getClasse() != ClasseTokenOBJ.EOF);
    }
}
