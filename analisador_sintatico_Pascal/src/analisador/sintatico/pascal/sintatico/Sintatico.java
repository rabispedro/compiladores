package analisador.sintatico.pascal.sintatico;

import analisador.sintatico.pascal.lexico.ClasseToken;
import analisador.sintatico.pascal.lexico.Lexico;
import analisador.sintatico.pascal.lexico.Token;

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

				a01();
				if (token.getClasse() == ClasseToken.PONTO_VIRGULA) {
					token = lexico.getNextToken();
					corpo();

					if (token.getClasse() == ClasseToken.PONTO) {
						token = lexico.getNextToken();

						a45();
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

		a44();

		if (isPalavraReservada("begin")) {
			token = lexico.getNextToken();

			sentencas();

			if (isPalavraReservada("end")) {
				token = lexico.getNextToken();
				a46();
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
				a04();

				parametros();

				a48();

				token = lexico.getNextToken();
				if (token.getClasse() == ClasseToken.PONTO_VIRGULA) {
					token = lexico.getNextToken();

					corpo();

					a56();

					if (token.getClasse() == ClasseToken.PONTO_VIRGULA) {
						token = lexico.getNextToken();

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

	// <parametros> ::= ( <lista_parametros> ) | ε
	private void parametros() {
		if (token.getClasse() == ClasseToken.PAR_ESQUERDA) {
			token = lexico.getNextToken();

			lista_parametros();

			if (token.getClasse() == ClasseToken.PAR_DIREITA) {
				token = lexico.getNextToken();
			} else {
				erroSintatico("Faltou a palavra 'PARENTESE DIREITO'");
			}
		}
	}

	// <lista_parametros> ::= <lista_id> : <tipo_var> {A06} <cont_lista_par>
	private void lista_parametros() {
		lista_id();

		token = lexico.getNextToken();

		if (token.getClasse() == ClasseToken.DOIS_PONTOS) {
			token = lexico.getNextToken();

			tipo_var();

			a06();

			cont_lista_par();
		} else {
			erroSintatico("Faltou a palavra 'DOIS PONTOS'");
		}
	}

	// <cont_lista_par> ::= ; <lista_parametros> | ε
	private void cont_lista_par() {
		if (token.getClasse() == ClasseToken.PONTO_VIRGULA) {
			token = lexico.getNextToken();

			lista_parametros();
		}
	}

	// <lista_id> ::= id {A07} <cont_lista_id>
	private void lista_id() {
		if (token.getClasse() == ClasseToken.IDENTIFICADOR) {
			token = lexico.getNextToken();

			a07();

			cont_lista_id();
		} else {
			erroSintatico("Faltou a palavra 'IDENTIFICADOR'");
		}
	}

	// <cont_lista_id> ::= , <lista_id> | ε
	private void cont_lista_id() {
		if (token.getClasse() == ClasseToken.VIRGULA) {
			token = lexico.getNextToken();

			lista_id();
		}
	}

	// <tipo_funcao> ::= integer
	private void tipo_funcao() {
		if (isPalavraReservada("integer")) {
			token = lexico.getNextToken();
		} else {
			erroSintatico("Faltou a palavra 'INTEGER'");
		}
	}

	// <funcao> ::= function id {A05} <parametros> {A48} : <tipo_funcao> {A47} ;
	// <corpo> {A56}
	private void funcao() {
		if (isPalavraReservada("function")) {
			token = lexico.getNextToken();

			if (token.getClasse() == ClasseToken.IDENTIFICADOR) {
				token = lexico.getNextToken();

				a05();

				parametros();

				a48();

				token = lexico.getNextToken();

				if (token.getClasse() == ClasseToken.DOIS_PONTOS) {
					token = lexico.getNextToken();

					tipo_funcao();

					a47();

					token = lexico.getNextToken();

					if (token.getClasse() == ClasseToken.PONTO_VIRGULA) {
						corpo();

						a56();

						token = lexico.getNextToken();

						if (token.getClasse() == ClasseToken.PONTO_VIRGULA) {
							rotina();
						} else {
							erroSintatico("Faltou 'PONTO E VIRGULA'");
						}
					} else {
						erroSintatico("Faltou 'PONTO E VIRGULA'");
					}
				} else {
					erroSintatico("Faltou 'DOIS PONTOS'");
				}
			} else {
				erroSintatico("Faltou 'IDENTIFICADOR'");
			}
		} else {
			erroSintatico("Faltou 'FUNCTION'");
		}
	}

	// <sentencas> ::= <comando> <mais_sentencas>
	private void sentencas() {
		comando();

		mais_sentencas();
	}

	// <mais_sentencas> ::= ; <cont_sentencas>
	private void mais_sentencas() {
		if (token.getClasse() == ClasseToken.PONTO_VIRGULA) {
			token = lexico.getNextToken();

			cont_sentencas();
		} else {
			erroSintatico("Faltou a palavra 'PONTO VIRGULA'");
		}
	}

	// <cont_sentencas> ::= <sentencas> | ε
	private void cont_sentencas() {
		sentencas();
	}

	// <var_read> ::= id {A08} <mais_var_read>
	private void var_read() {
		if (token.getClasse() == ClasseToken.IDENTIFICADOR) {
			token = lexico.getNextToken();

			a08();

			mais_var_read();
		} else {
			erroSintatico("Faltou a palavra 'IDENTIFICADOR'");
		}
	}

	// <mais_var_read> ::= , <var_read> | ε
	private void mais_var_read() {
		if (token.getClasse() == ClasseToken.VIRGULA) {
			token = lexico.getNextToken();

			var_read();
		}
	}

	// <exp_write> ::= id {A09} <mais_exp_write> |
	// string {A59} <mais_exp_write> |
	// intnum {A43} <mais_exp_write>
	private void exp_write() {
		if (token.getClasse() == ClasseToken.IDENTIFICADOR) {
			token = lexico.getNextToken();

			a09();

			mais_exp_write();
		} else if (isPalavraReservada("STRING")) {
			token = lexico.getNextToken();

			a59();

			mais_exp_write();
		} else if (isPalavraReservada("INTNUM")) {
			token = lexico.getNextToken();

			a43();

			mais_exp_write();
		} else {
			erroSintatico("Faltou uma das palavras 'ID', 'STRING' ou 'INTNUM'");
		}
	}

	// <mais_exp_write> ::= , <exp_write> | ε
	private void mais_exp_write() {
		if (token.getClasse() == ClasseToken.VIRGULA) {
			token = lexico.getNextToken();

			exp_write();
		}
	}

	// <comando> ::=
	// read ( <var_read> ) |
	// write ( <exp_write> ) |
	// writeln ( <exp_write> ) {A61} |
	// for id {A57} := <expressao> {A11} to <expressao> {A12} do begin <sentencas>
	// end {A13} |
	// repeat {A14} <sentencas> until ( <expressao_logica> ) {A15} |
	// while {A16} ( <expressao_logica> ) {A17} do begin <sentencas> end {A18} |
	// if ( <expressao_logica> ) {A19} then begin <sentencas> end {A20} <pfalsa>
	// {A21} |
	// id {A49} := <expressao> {A22} |
	// <chamada_procedimento>
	private void comando() {
		if (isPalavraReservada("READ")) {
			token = lexico.getNextToken();

			if (token.getClasse() == ClasseToken.PAR_ESQUERDA) {
				token = lexico.getNextToken();

				var_read();

				if (token.getClasse() == ClasseToken.PAR_DIREITA) {
					token = lexico.getNextToken();
				} else {
					erroSintatico("Faltou a palavra 'PARENTESE DIREITO'");
				}
			} else {
				erroSintatico("Faltou a palavra 'PARENTESE ESQUERDO'");
			}
		} else if (isPalavraReservada("WRITE")) {
			token = lexico.getNextToken();

			if (token.getClasse() == ClasseToken.PAR_ESQUERDA) {
				token = lexico.getNextToken();

				exp_write();

				if (token.getClasse() == ClasseToken.PAR_DIREITA) {
					token = lexico.getNextToken();
				} else {
					erroSintatico("Faltou a palavra 'PARENTESE DIREITO'");
				}
			} else {
				erroSintatico("Faltou a palavra 'PARENTESE ESQUERDO'");
			}
		} else if (isPalavraReservada("WRITELN")) {
			token = lexico.getNextToken();

			if (token.getClasse() == ClasseToken.PAR_ESQUERDA) {
				token = lexico.getNextToken();

				exp_write();

				if (token.getClasse() == ClasseToken.PAR_DIREITA) {
					token = lexico.getNextToken();

					a61();
				} else {
					erroSintatico("Faltou a palavra 'PARENTESE DIREITO'");
				}
			} else {
				erroSintatico("Faltou a palavra 'PARENTESE ESQUERDO'");
			}
		} else if (isPalavraReservada("FOR")) {
			token = lexico.getNextToken();

			if (token.getClasse() == ClasseToken.IDENTIFICADOR) {
				token = lexico.getNextToken();

				a57();

				if (token.getClasse() == ClasseToken.ATRIBUICAO) {
					token = lexico.getNextToken();

					expressao();

					a11();

					if (isPalavraReservada("TO")) {
						token = lexico.getNextToken();

						expressao();

						a12();

						if (isPalavraReservada("DO")) {
							token = lexico.getNextToken();

							if (isPalavraReservada("BEGIN")) {
								token = lexico.getNextToken();

								sentencas();

								if (isPalavraReservada("END")) {
									token = lexico.getNextToken();

									a13();
								} else {
									erroSintatico("Faltou a palavra 'END'");
								}
							} else {
								erroSintatico("Faltou a palavra 'DO'");
							}
						} else {
							erroSintatico("Faltou a palavra 'DO'");
						}
					} else {
						erroSintatico("Faltou a palavra 'TO'");
					}
				} else {
					erroSintatico("Faltou a palavra 'ATRIBUICAO'");
				}
			} else {
				erroSintatico("Faltou a palavra 'IDENTIFICADOR'");
			}
		} else if (isPalavraReservada("REPEAT")) {
			token = lexico.getNextToken();

			a14();

			sentencas();

			if (isPalavraReservada("UNTIL")) {
				token = lexico.getNextToken();

				if (token.getClasse() == ClasseToken.PAR_ESQUERDA) {
					token = lexico.getNextToken();

					expressao_logica();

					if (token.getClasse() == ClasseToken.PAR_DIREITA) {
						token = lexico.getNextToken();

						a15();
					} else {
						erroSintatico("Faltou a palavra 'PARENTESE DIREITO'");
					}
				} else {
					erroSintatico("Faltou a palavra 'PARENTESE ESQUERDO'");
				}
			} else {
				erroSintatico("Faltou a palavra 'UNTIL'");
			}
		} else if (isPalavraReservada("WHILE")) {
			token = lexico.getNextToken();

			a16();

			if (token.getClasse() == ClasseToken.PAR_ESQUERDA) {
				token = lexico.getNextToken();

				expressao_logica();

				if (token.getClasse() == ClasseToken.PAR_DIREITA) {
					token = lexico.getNextToken();

					a17();

					if (isPalavraReservada("DO")) {
						token = lexico.getNextToken();

						if (isPalavraReservada("BEGIN")) {
							token = lexico.getNextToken();

							sentencas();

							if (isPalavraReservada("END")) {
								token = lexico.getNextToken();

								a18();
							} else {
								erroSintatico("Faltou a palavra 'END'");
							}
						} else {
							erroSintatico("Faltou a palavra 'BEGIN'");
						}
					} else {
						erroSintatico("Faltou a palavra 'DO'");
					}
				} else {
					erroSintatico("Faltou a palavra 'PARENTESE DIREITO'");
				}
			} else {
				erroSintatico("Faltou a palavra 'PARENTESE ESQUERDO'");
			}

		} else if (isPalavraReservada("IF")) {
			token = lexico.getNextToken();

			if (token.getClasse() == ClasseToken.PAR_ESQUERDA) {
				token = lexico.getNextToken();

				expressao_logica();

				if (token.getClasse() == ClasseToken.PAR_DIREITA) {
					token = lexico.getNextToken();

					a19();

					if (isPalavraReservada("THEN")) {
						token = lexico.getNextToken();

						if (isPalavraReservada("BEGIN")) {
							token = lexico.getNextToken();

							sentencas();

							if (isPalavraReservada("END")) {
								token = lexico.getNextToken();

								a20();

								pfalsa();

								a21();
							} else {
								erroSintatico("Faltou a palavra 'END'");
							}
						} else {
							erroSintatico("Faltou a palavra 'BEGIN'");
						}
					} else {
						erroSintatico("Faltou a palavra 'THEN'");
					}
				} else {
					erroSintatico("Faltou a palavra 'PARENTESE DIREITO'");
				}
			} else {
				erroSintatico("Faltou a palavra 'PARENTESE ESQUERDO'");
			}
		} else if (token.getClasse() == ClasseToken.IDENTIFICADOR) {
			token = lexico.getNextToken();

			a49();

			if (token.getClasse() == ClasseToken.ATRIBUICAO) {
				token = lexico.getNextToken();

				expressao();

				a22();
			} else {
				erroSintatico("Faltou a palavra 'ATRIBUICAO'");
			}
		} else {
			chamada_procedimento();
		}
	}

	// <chamada_procedimento> ::= id {A50} <argumentos> {A23}
	private void chamada_procedimento() {
		if (token.getClasse() == ClasseToken.IDENTIFICADOR) {
			token = lexico.getNextToken();

			a50();

			argumentos();

			a23();
		} else {
			erroSintatico("Faltou a palavra 'IDENTIFICADOR'");
		}
	}

	// <argumentos> ::= ( <lista_arg> ) | ε
	private void argumentos() {
		if (token.getClasse() == ClasseToken.PAR_ESQUERDA) {
			token = lexico.getNextToken();

			lista_arg();

			if (token.getClasse() == ClasseToken.PAR_DIREITA) {
				token = lexico.getNextToken();
			} else {
				erroSintatico("Faltou a palavra 'PARÊNTERE DIREITO'");
			}
		}
	}

	// <lista_arg> ::= <expressao> <cont_lista_arg>
	private void lista_arg() {
		expressao();

		cont_lista_arg();
	}

	// <cont_lista_arg> ::= , <lista_arg> | ε
	private void cont_lista_arg() {
		if (token.getClasse() == ClasseToken.VIRGULA) {
			token = lexico.getNextToken();

			lista_arg();
		}
	}

	// <pfalsa> ::= {A25} else begin <sentencas> end | ε
	private void pfalsa() {
		a25();

		if (isPalavraReservada("ELSE")) {
			token = lexico.getNextToken();

			if (isPalavraReservada("BEGIN")) {
				token = lexico.getNextToken();

				sentencas();

				if (isPalavraReservada("END")) {
					token = lexico.getNextToken();
				} else {
					erroSintatico("Faltou a palavra 'END'");
				}
			} else {
				erroSintatico("Faltou a palavra 'BEGIN'");
			}
		}
	}

	// <expressao_logica> ::= <termo_logico> <mais_expr_logica>
	private void expressao_logica() {
		termo_logico();

		mais_expr_logica();
	}

	// <mais_expr_logica> ::= or <termo_logico> {A26} <mais_expr_logica> | ε
	private void mais_expr_logica() {
		if (isPalavraReservada("OR")) {
			token = lexico.getNextToken();

			termo_logico();

			a26();

			mais_expr_logica();
		}
	}

	// <termo_logico> ::= <fator_logico> <mais_termo_logico>
	private void termo_logico() {
		fator_logico();

		mais_termo_logico();
	}

	// <mais_termo_logico> ::= and <fator_logico> {A27} <mais_termo_logico> | ε
	private void mais_termo_logico() {
		if (isPalavraReservada("AND")) {
			token = lexico.getNextToken();

			fator_logico();

			a27();

			mais_termo_logico();
		}
	}

	// <fator_logico> ::= <relacional> |
	// ( <expressao_logica> ) |
	// not <fator_logico> {A28} |
	// true {A29} |
	// false {A30}
	private void fator_logico() {
		if (token.getClasse() == ClasseToken.PAR_ESQUERDA) {
			token = lexico.getNextToken();

			expressao_logica();

			if (token.getClasse() == ClasseToken.PAR_DIREITA) {
				token = lexico.getNextToken();
			} else {
				erroSintatico("Faltou a palavra 'PARENTESE DIREITO'");
			}
		} else if (isPalavraReservada("NOT")) {
			token = lexico.getNextToken();

			fator_logico();

			a28();
		} else if (isPalavraReservada("TRUE")) {
			token = lexico.getNextToken();

			a29();
		} else if (isPalavraReservada("FALSE")) {
			token = lexico.getNextToken();

			a30();
		} else {
			relacional();
		}
	}

	// <relacional> ::= <expressao> = <expressao> {A31} |
	// <expressao> > <expressao> {A32} |
	// <expressao> >= <expressao> {A33} |
	// <expressao> < <expressao> {A34} |
	// <expressao> <= <expressao> {A35} |
	// <expressao> <> <expressao> {A36}
	private void relacional() {
		expressao();

		if (token.getClasse() == ClasseToken.IGUAL) {
			token = lexico.getNextToken();

			expressao();

			a31();
		} else if (token.getClasse() == ClasseToken.MAIOR) {
			token = lexico.getNextToken();

			expressao();

			a32();
		} else if (token.getClasse() == ClasseToken.MAIOR_IGUAL) {
			token = lexico.getNextToken();

			expressao();

			a33();
		} else if (token.getClasse() == ClasseToken.MENOR) {
			token = lexico.getNextToken();

			expressao();

			a34();
		} else if (token.getClasse() == ClasseToken.MENOR_IGUAL) {
			token = lexico.getNextToken();

			expressao();

			a35();
		} else if (token.getClasse() == ClasseToken.DIFERENTE) {
			token = lexico.getNextToken();

			expressao();

			a36();
		} else {
			erroSintatico("Faltou a palavra 'OPERADOR DE IGUALDADE OU DIFERENCA'");
		}
	}

	// <expressao> ::= <termo> <mais_expressao>
	private void expressao() {
		termo();

		mais_expressao();
	}

	// <mais_expressao> ::= + <termo> {A37} <mais_expressao> |
	// - <termo> {A38} <mais_expressao> | ε
	private void mais_expressao() {
		if (isPalavraReservada("+")) {
			token = lexico.getNextToken();

			termo();

			a37();

			mais_expressao();
		} else if (isPalavraReservada("-")) {
			token = lexico.getNextToken();

			a38();

			mais_expressao();
		}
	}

	// <termo> ::= <fator> <mais_termo>
	private void termo() {
		fator();

		mais_termo();
	}

	// <mais_termo> ::= * <fator> {A39} <mais_termo> |
	// / <fator> {A40} <mais_termo> | ε
	private void mais_termo() {
		if (isPalavraReservada("*")) {
			token = lexico.getNextToken();

			fator();

			a39();

			mais_termo();
		} else if (isPalavraReservada("/")) {
			token = lexico.getNextToken();

			fator();

			a40();

			mais_termo();
		}
	}

	// <fator> ::= id {A55} | intnum {A41} | ( <expressao> ) | id {A60} <argumentos>
	// {A42}
	private void fator() {
		if (token.getClasse() == ClasseToken.IDENTIFICADOR) {
			token = lexico.getNextToken();

			a55();
		} else if (isPalavraReservada("INTNUM")) {
			token = lexico.getNextToken();

			a41();
		} else if (token.getClasse() == ClasseToken.PAR_ESQUERDA) {
			token = lexico.getNextToken();

			expressao();

			if (token.getClasse() == ClasseToken.PAR_DIREITA) {
				token = lexico.getNextToken();
			} else {
				erroSintatico("Faltou a palavra 'PARENTESE DIREITO'");
			}
			// NOTE: perguntar sobre salto para professor
		} else if (token.getClasse() == ClasseToken.IDENTIFICADOR) {
			token = lexico.getNextToken();

			a60();

			argumentos();

			a42();
		} else {
			erroSintatico("Faltou uma das palavras 'ID', 'INTNUM', 'PARENTESE ESQUERDO'");
		}
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

			a03();

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
			a02();
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

	private void a01() {
	}

	private void a02() {
	}

	private void a03() {
	}

	private void a04() {
	}

	private void a05() {
	}

	private void a06() {
	}

	private void a07() {
	}

	private void a08() {
	}

	private void a09() {
	}

	private void a10() {
	}

	private void a11() {
	}

	private void a12() {
	}

	private void a13() {
	}

	private void a14() {
	}

	private void a15() {
	}

	private void a16() {
	}

	private void a17() {
	}

	private void a18() {
	}

	private void a19() {
	}

	private void a20() {
	}

	private void a21() {
	}

	private void a22() {
	}

	private void a23() {
	}

	private void a24() {
	}

	private void a25() {
	}

	private void a26() {
	}

	private void a27() {
	}

	private void a28() {
	}

	private void a29() {
	}

	private void a30() {
	}

	private void a31() {
	}

	private void a32() {
	}

	private void a33() {
	}

	private void a34() {
	}

	private void a35() {
	}

	private void a36() {
	}

	private void a37() {
	}

	private void a38() {
	}

	private void a39() {
	}

	private void a40() {
	}

	private void a41() {
	}

	private void a42() {
	}

	private void a43() {
	}

	private void a44() {
	}

	private void a45() {
	}

	private void a46() {
	}

	private void a47() {
	}

	private void a48() {
	}

	private void a49() {
	}

	private void a50() {
	}

	private void a51() {
	}

	private void a52() {
	}

	private void a53() {
	}

	private void a54() {
	}

	private void a55() {
	}

	private void a56() {
	}

	private void a57() {
	}

	private void a58() {
	}

	private void a59() {
	}

	private void a60() {
	}

	private void a61() {
	}
}
