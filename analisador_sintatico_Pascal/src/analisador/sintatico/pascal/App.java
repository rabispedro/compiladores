package analisador.sintatico.pascal;

import analisador.sintatico.pascal.lexico.Lexico;
import analisador.sintatico.pascal.sintatico.Sintatico;

public class App {
    public static void main(String[] args) {
        Lexico l = new Lexico("analisador_sintatico/fibonacci.pas");
        Sintatico s = new Sintatico(l);
        s.analisar();
        System.out.println("Arquivo analisado com sucesso!");
    }
}
