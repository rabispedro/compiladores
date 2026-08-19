import lexico.Lexico;
import sintatico.Sintatico;

public class App {
    public static void main(String[] args) throws Exception {
        Lexico l = new Lexico("teste.pas");
        Sintatico s = new Sintatico(l);
        s.analisar();
    }
}
