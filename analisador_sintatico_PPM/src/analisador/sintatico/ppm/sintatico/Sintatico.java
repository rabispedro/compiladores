package analisador.sintatico.ppm.sintatico;

import analisador.sintatico.ppm.lexico.ClasseTokenPPM;
import analisador.sintatico.ppm.lexico.LexicoPPM;
import analisador.sintatico.ppm.lexico.Token;

public class Sintatico {
	private static final int BATCH_SIZE = 100;

	private final LexicoPPM lexico;
	private Token token;

	private int calls = 0;
	private boolean isDispatching = false;

	public Sintatico(LexicoPPM lexico) {
		this.lexico = lexico;
	}

	public void analisar() {
		token = lexico.getNextToken();
		imagem_ppm();
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
		if (isPalavraReservada("MAGIC") || isPalavraReservada("P3")) {
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
		} else {
			erroSintatico("FALTOU 'MAGIC'");
		}
	}

	// <lista_pixels>::= <pixel> <mais_pixels>
	private void lista_pixels() {
		while (token.getClasse() != ClasseTokenPPM.EOF) {
			pixel();

			mais_pixels();
		}
	}

	// <mais_pixels>::= <pixel> <mais_pixels> | ε
	private void mais_pixels() {
		if (token.getValor() != null) {
			// Call Stack não pode ser maior que BATCH_SIZE
			if (isDispatching) {
				calls--;

				if (calls == 0)
					isDispatching = false;
			} else {
				calls++;

				if (calls == BATCH_SIZE)
					isDispatching = true;

				pixel();

				mais_pixels();
			}
		}
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
		System.exit(2);
	}

	private boolean isPalavraReservada(final String palavra) {
		return (token.getClasse() == ClasseTokenPPM.PalavraReservada
				&& token.getValor().getTexto().equalsIgnoreCase(palavra));
	}
}
