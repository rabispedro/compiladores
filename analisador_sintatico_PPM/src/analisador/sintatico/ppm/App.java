package analisador.sintatico.ppm;

import analisador.sintatico.ppm.lexico.LexicoPPM;
import analisador.sintatico.ppm.sintatico.Sintatico;

public class App {
    public static void main(String[] args) {
        var l = new LexicoPPM("analisador_sintatico_PPM/java-logo.ppm");

        var s = new Sintatico(l);
        s.analisar();
        System.out.println("Arquivo analisado com sucesso!");
    }
}
