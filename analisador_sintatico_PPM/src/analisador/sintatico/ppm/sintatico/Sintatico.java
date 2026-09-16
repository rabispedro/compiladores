package analisador.sintatico.ppm.sintatico;

import analisador.sintatico.ppm.lexico.ClasseTokenPPM;
import analisador.sintatico.ppm.lexico.LexicoPPM;
import analisador.sintatico.ppm.lexico.Token;

public class Sintatico {
	private final LexicoPPM lexico;
	private Token token;

	public Sintatico(LexicoPPM lexico) {
		this.lexico = lexico;
	}

	// <imagem_ppm>::= <cabecalho> <lista_pixels> EOF
	private void imagem_ppm() {
		cabecalho();

		lista_pixels();

		if (token.getClasse() == ClasseTokenPPM.EOF) {
			token = lexico.getNextToken();
		} else {
			erroSintatico("FALTOU 'EOF'");
		}
	}

	// <cabecalho>::= MAGIC NUMERO NUMERO NUMERO
	private void cabecalho() {
		if (isPalavraReservada("MAGIC")) {
			token = lexico.getNextToken();

			if (token.getClasse() == ClasseTokenPPM.NumeroInteiro) {
				token = lexico.getNextToken();

				if (token.getClasse() == ClasseTokenPPM.NumeroInteiro) {
					token = lexico.getNextToken();

					if (token.getClasse() == ClasseTokenPPM.NumeroInteiro) {
						token = lexico.getNextToken();

					} else {
						erroSintatico("FALTOU 'NUMERO INTEIRO'");
					}
				} else {
					erroSintatico("FALTOU 'NUMERO INTEIRO'");
				}
			} else {
				erroSintatico("FALTOU 'NUMERO INTEIRO'");
			}
		}
	}

	// <lista_pixels>::= <pixel> <mais_pixels>
	private void lista_pixels() {
		pixel();

		mais_pixels();
	}

	// <mais_pixels>::= <pixel> <mais_pixels> | ε
	private void mais_pixels() {
		pixel();

		mais_pixels();
	}

	// <pixel> ::= NUMERO NUMERO NUMERO
	private void pixel() {
		if (token.getClasse() == ClasseTokenPPM.NumeroInteiro) {
			token = lexico.getNextToken();

			if (token.getClasse() == ClasseTokenPPM.NumeroInteiro) {
				token = lexico.getNextToken();

				if (token.getClasse() == ClasseTokenPPM.NumeroInteiro) {
					token = lexico.getNextToken();

				} else {
					erroSintatico("FALTOU 'NUMERO INTEIRO'");
				}
			} else {
				erroSintatico("FALTOU 'NUMERO INTEIRO'");
			}
		} else {
			erroSintatico("FALTOU 'NUMERO INTEIRO'");
		}
	}

	private void erroSintatico(String mensagem) {
		System.err.println("Linha: " + token.getLinha() + ", Coluna: " + token.getColuna() + " ['"
				+ token.getValor().getTexto() + "']. Erro Sintático: " + mensagem);
	}

	private boolean isPalavraReservada(final String palavra) {
		return (token.getClasse() == ClasseTokenPPM.PalavraReservada
				&& token.getValor().getTexto().equalsIgnoreCase(palavra));
	}
}
