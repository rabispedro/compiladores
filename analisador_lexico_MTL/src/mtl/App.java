package mtl;

import mtl.lexico.ClasseTokenMTL;
import mtl.lexico.LexicoMTL;
import mtl.lexico.Token;

public class App {
    public static void main(String[] args) {
        LexicoMTL l = new LexicoMTL("analisador_lexico_MTL/cube.mtl");
        Token t;

        // int cont = 0;
        do {
            t = l.getNextToken();
            System.out.println(t);
            // cont++;
            // if (cont == 10) break;
        } while (t.getClasse() != ClasseTokenMTL.EOF);
    }
}
