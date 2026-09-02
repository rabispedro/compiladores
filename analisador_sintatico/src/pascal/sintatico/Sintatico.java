package pascal.sintatico;

import pascal.lexico.ClasseToken;
import pascal.lexico.Lexico;
import pascal.lexico.Token;

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

	// <rotina> ::= <procedimento> | <funcao> | ε
	private void rotina() {
		if (isPalavraReservada("procedimento")) {
			procedimento();
		} else if (isPalavraReservada("funcao")) {
			funcao();
		}
	}

	// <procedimento> ::= procedure id {A04} <parametros> {A48} ; <corpo> {A56} ;
	// <rotina>
	private void procedimento() {
		if (isPalavraReservada("procedure")) {
			token = lexico.getNextToken();

			if (isPalavraReservada("id")) {
				// A{04}

				parametros();

				// A{48}

				token = lexico.getNextToken();
				if (token.getClasse() == ClasseToken.PONTO_VIRGULA) {
					corpo();

					// A{56}

					token = lexico.getNextToken();

					if (token.getClasse() == ClasseToken.PONTO_VIRGULA) {
						rotina();
					} else {
						erroSintatico("Faltou o ponto e virgula (;)");
					}
				} else {
					erroSintatico("Faltou o ponto e virgula (;)");
				}
			} else {
				erroSintatico("Faltou o 'IDENTIFICADOR'");
			}
		} else {
			erroSintatico("Faltou a palavra 'PROCEDURE'");
		}
	}

	private void parametros() {
	}

	// <funcao> ::= function id {A05} <parametros> {A48} : <tipo_funcao> {A47} ;
	// <corpo> {A56}
	private void funcao() {
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

	// ; <rotina>
	// <parametros> ::= ( <lista_parametros> ) | ε
	// <lista_parametros> ::= <lista_id> : <tipo_var> {A06} <cont_lista_par>
	// <cont_lista_par> ::= ; <lista_parametros> | ε
	// <lista_id> ::= id {A07} <cont_lista_id>
	// <cont_lista_id> ::= , <lista_id> | ε
	// <tipo_funcao> ::= integer

	private void erroSintatico(String mensagem) {
		System.err.println("Linha: " + token.getLinha() + ", Coluna: " + token.getColuna() + " ['"
				+ token.getValor().getTexto() + "']. Erro Sintático: " + mensagem);
	}

	private boolean isPalavraReservada(final String palavra) {
		return (token.getClasse() == ClasseToken.PALAVRA_RESERVADA
				&& token.getValor().getTexto().equalsIgnoreCase(palavra));
	}
}
