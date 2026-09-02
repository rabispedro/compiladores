package pascal;

import pascal.lexico.ClasseToken;
import pascal.lexico.Lexico;
import pascal.lexico.Token;
import pascal.sintatico.Sintatico;

public class App {
    public static void main(String[] args) {
        Lexico l = new Lexico("analisador_sintatico/teste.pas");
        Token t;

        do {
            t = l.getNextToken();
            System.out.println(t);
        } while (t.getClasse() != ClasseToken.EOF);

        l = new Lexico("analisador_sintatico/teste.pas");

        Sintatico s = new Sintatico(l);
        s.analisar();
        System.out.println("Arquivo analisado com sucesso!");
    }
}
