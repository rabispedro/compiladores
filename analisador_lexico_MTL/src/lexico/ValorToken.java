package lexico;

public class ValorToken {

    private String texto;
    private Number numero;
    
    public ValorToken(String texto) {
        this.texto = texto;
    }

    public ValorToken(Number numero) {
        this.numero = numero;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Number getNumero() {
        return numero;
    }

    public void setNumero(Number numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return ((texto != null) ? "texto=" + texto : "") + 
               ((numero != null) ? "numero=" + numero : "");
    }

}
