package sintatico;

import lexico.ClasseToken;
import lexico.Lexico;
import lexico.Token;

public class Sintatico {
	private final Lexico lexico;
	private Token token;

	public Sintatico(Lexico lexico) {
		this.lexico = lexico;
	}

	public void analisar() {
		token = lexico.getNextToken();
		programa();

	}

	// <programa> ::= program id {A01} ; <corpo> . {A45}
	private void programa() {
		if (isPalavraReservada("program")) {
			token = lexico.getNextToken();
			if (token.getClasse() == ClasseToken.IDENTIFICADOR) {
				token = lexico.getNextToken();

				// {A01}
				if (token.getClasse() == ClasseToken.PONTO_VIRGULA) {
					token = lexico.getNextToken();
					corpo();

					if (token.getClasse() == ClasseToken.PONTO) {
						token = lexico.getNextToken();

						// A{45}
					} else {
						erroSintatico("Faltou o ponto final do programa");
					}
				} else {
					erroSintatico("Faltou ponto e vírgula (;) depois do identificador do programa");
				}
			} else {
				erroSintatico("Faltou o nome do programa");
			}
		} else {
			erroSintatico("Faltou começar o programa com 'PROGRAM'");
		}
	}

	// <corpo> ::= <declara> <rotina> {A44} begin <sentencas> end {A46}
	private void corpo() {
		declara();
		rotina();

		// A{44}

		if (isPalavraReservada("begin")) {
			token = lexico.getNextToken();

			sentencas();

			if (isPalavraReservada("end")) {
				token = lexico.getNextToken();
				// A{46}
			} else {
				erroSintatico("Faltou o 'END'");
			}
		} else {
			erroSintatico("Faltou o 'BEGIN'");
		}
	}

	// <declara> ::= var <dvar> <mais_dc> | ε
	private void declara() {
		if (isPalavraReservada("var")) {
			token = lexico.getNextToken();

			dvar();
			mais_dc();
		}
	}

	private void rotina() {
	}

	private void sentencas() {
	}

	// <tipo_var> ::= integer
	private void tipo_var() {
		if (isPalavraReservada("integer")) {
			token = lexico.getNextToken();
		} else {
			erroSintatico("Faltou o tipo 'INTEGER' da variável na declaração");
		}
	}

	// <mais_var> ::= , <variaveis> | ε
	private void mais_var() {
		if (token.getClasse() == ClasseToken.VIRGULA) {
			token = lexico.getNextToken();
			variaveis();
		}
	}

	// <variaveis> ::= id {A03} <mais_var>
	private void variaveis() {
		// if (isPalavraReservada())
		if (token.getClasse() == ClasseToken.IDENTIFICADOR) {
			token = lexico.getNextToken();
			// A{03}

			mais_var();
		} else {
			erroSintatico("Faltou 'IDENTIFICADOR'");
		}
	}

	// <dvar> ::= <variaveis> : <tipo_var> {A02}
	private void dvar() {
		variaveis();
		if (token.getClasse() == ClasseToken.DOIS_PONTOS) {
			token = lexico.getNextToken();

			tipo_var();
			// A{02}
		} else {
			erroSintatico("Faltou dois pontos (:) na declaração de variáveis");
		}
	}

	// <mais_dc> ::= ; <cont_dc>
	private void mais_dc() {
		if (token.getClasse() == ClasseToken.PONTO_VIRGULA) {
			token = lexico.getNextToken();

			cont_dc();
		} else {
			erroSintatico("Faltou ponto e vírgula (;) no final de uma declaração de variáveis");
		}
	}

	// <cont_dc> ::= <dvar> <mais_dc> | ε
	private void cont_dc() {
		if (token.getClasse() == ClasseToken.IDENTIFICADOR) {
			dvar();
			mais_dc();
		}
	}

	private void erroSintatico(String mensagem) {
		System.err.println("Linha: " + token.getLinha() + ", Coluna: " + token.getColuna() + " ['"
				+ token.getValor().getTexto() + "']. Erro Sintático: " + mensagem);
	}

	private boolean isPalavraReservada(final String palavra) {
		return (token.getClasse() == ClasseToken.PALAVRA_RESERVADA
				&& token.getValor().getTexto().equalsIgnoreCase(palavra));
	}
}
