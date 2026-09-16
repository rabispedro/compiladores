package analisador.lexico.obj;

import analisador.lexico.obj.lexico.ClasseTokenOBJ;
import analisador.lexico.obj.lexico.LexicoOBJ;
import analisador.lexico.obj.lexico.Token;

public class App {
    public static void main(String[] args) {
        LexicoOBJ l = new LexicoOBJ("analisador_lexico_OBJ/cube.obj");
        Token t;

        do {
            t = l.getNextToken();
            System.out.println(t);
        } while (t.getClasse() != ClasseTokenOBJ.EOF);
    }
}
