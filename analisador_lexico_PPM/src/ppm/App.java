package ppm;

import ppm.lexico.ClasseTokenPPM;
import ppm.lexico.LexicoPPM;
import ppm.lexico.Token;

public class App {
    public static void main(String[] args) {
        LexicoPPM l = new LexicoPPM("analisador_lexico_PPM/java-logo.ppm");
        Token t;

        int cont = 0;
        do {
            t = l.getNextToken();
            System.out.println(t);
            cont++;
            if (cont == 15)
                break;
        } while (t.getClasse() != ClasseTokenPPM.EOF);

    }
}
