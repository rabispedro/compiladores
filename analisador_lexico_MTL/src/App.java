import lexico.ClasseTokenMTL;
import lexico.LexicoMTL;
import lexico.Token;

public class App {
    public static void main(String[] args) throws Exception {
        LexicoMTL l = new LexicoMTL("cube.mtl");
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
