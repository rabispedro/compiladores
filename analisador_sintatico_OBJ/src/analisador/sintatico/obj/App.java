package analisador.sintatico.obj;

import analisador.sintatico.obj.lexico.LexicoOBJ;
import analisador.sintatico.obj.sintatico.Sintatico;

public class App {
    public static void main(String[] args) {
        LexicoOBJ l = new LexicoOBJ("analisador_lexico_OBJ/cube.obj");
        Sintatico s = new Sintatico(l);
        s.analisar();
        System.out.println("Arquivo analisado com sucesso!");
    }
}
