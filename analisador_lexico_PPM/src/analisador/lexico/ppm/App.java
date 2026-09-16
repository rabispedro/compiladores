package analisador.lexico.ppm;

import analisador.lexico.ppm.lexico.ClasseTokenPPM;
import analisador.lexico.ppm.lexico.LexicoPPM;
import analisador.lexico.ppm.lexico.Token;

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
