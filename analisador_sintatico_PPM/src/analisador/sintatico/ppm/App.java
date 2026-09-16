package analisador.sintatico.ppm;

import analisador.sintatico.ppm.lexico.LexicoPPM;
import analisador.sintatico.ppm.sintatico.Sintatico;

public class App {
    public static void main(String[] args) {
        LexicoPPM l = new LexicoPPM("analisador_lexico_PPM/java-logo.ppm");
        Sintatico s = new Sintatico(l);
    }
}
