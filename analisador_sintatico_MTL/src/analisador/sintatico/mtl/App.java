package analisador.sintatico.mtl;

import analisador.sintatico.mtl.lexico.LexicoMTL;
import analisador.sintatico.mtl.sintatico.Sintatico;

public class App {
    public static void main(String[] args) {
        var l = new LexicoMTL("analisador_lexico_MTL/cube.mtl");
        var s = new Sintatico(l);
        s.analisar();
        System.out.println("Arquivo analisado com sucesso!");
    }
}
