import lexico.ClasseTokenOBJ;
import lexico.LexicoOBJ;
import lexico.Token;

public class App {
    public static void main(String[] args) throws Exception {
        LexicoOBJ l = new LexicoOBJ("cube.obj");
        Token t;

        // int cont = 0;
        do {
            t = l.getNextToken();
            System.out.println(t);
            // cont++;
            // if (cont == 10) break;
        } while (t.getClasse() != ClasseTokenOBJ.EOF);

    }
}
