package analisador.sintatico.obj.sintatico;

import java.util.List;

import analisador.sintatico.obj.lexico.ClasseTokenOBJ;
import analisador.sintatico.obj.lexico.LexicoOBJ;
import analisador.sintatico.obj.lexico.Token;

public class Sintatico {
	private final LexicoOBJ lexico;
	private Token token;

	public Sintatico(LexicoOBJ lexico) {
		this.lexico = lexico;
	}

	// <arquivo_obj>::= <linhas_obj> EOF
	private void arquivo_obj() {
		linhas_obj();

		if (token.getClasse() == ClasseTokenOBJ.EOF) {
			token = lexico.getNextToken();
		} else {
			erroSintatico("Faltou 'EOF'");
		}
	}

	// <linhas_obj>::= <comando> <linhas_obj> | ε
	private void linhas_obj() {
		comando();

		linhas_obj();
	}

	// <comando>::= <importa_mtl>
	// | <usa_mtl>
	// | <def_objeto>
	// | <def_grupo>
	// | <def_vertice>
	// | <def_uv>
	// | <def_normal>
	// | <def_face>
	private void comando() {
		switch (token.getValor().getTexto()) {
			case "importa_mtl" -> importa_mtl();
			case "usa_mtl" -> usa_mtl();
			case "def_objeto" -> def_objeto();
			case "def_grupo" -> def_grupo();
			case "def_vertice" -> def_vertice();
			case "def_uv" -> def_uv();
			case "def_normal" -> def_normal();
			case "def_face" -> def_face();
			default -> erroSintatico("Faltou palavra de 'COMANDO'");
		}
	}

	// <importa_mtl> ::= KW_MTLLIB IDENTIFICADOR
	private void importa_mtl() {
		token = lexico.getNextToken();

		if (isPalavraReservada("KW_MTLLIB")) {
			token = lexico.getNextToken();

			if (token.getClasse() == ClasseTokenOBJ.IDENTIFICADOR) {
				token = lexico.getNextToken();
			} else {
				erroSintatico("Faltou 'IDENTIFICADOR'");
			}
		} else {
			erroSintatico("Faltou palavra reservada 'KW_MTLLIB'");
		}
	}

	// <usa_mtl> ::= KW_USEMTL IDENTIFICADOR
	private void usa_mtl() {
		token = lexico.getNextToken();

		if (isPalavraReservada("KW_USEMTL")) {
			token = lexico.getNextToken();

			if (token.getClasse() == ClasseTokenOBJ.IDENTIFICADOR) {
				token = lexico.getNextToken();
			} else {
				erroSintatico("Faltou 'IDENTIFICADOR'");
			}
		} else {
			erroSintatico("Faltou palavra reservada 'KW_USEMTL'");
		}
	}

	// <def_objeto> ::= KW_O IDENTIFICADOR
	private void def_objeto() {
		token = lexico.getNextToken();

		if (isPalavraReservada("KW_O")) {
			token = lexico.getNextToken();

			if (token.getClasse() == ClasseTokenOBJ.IDENTIFICADOR) {
				token = lexico.getNextToken();
			} else {
				erroSintatico("Faltou 'IDENTIFICADOR'");
			}
		} else {
			erroSintatico("Faltou palavra reservada 'KW_O'");
		}
	}

	// <def_grupo> ::= KW_G IDENTIFICADOR
	private void def_grupo() {
		token = lexico.getNextToken();

		if (isPalavraReservada("KW_G")) {
			token = lexico.getNextToken();

			if (token.getClasse() == ClasseTokenOBJ.IDENTIFICADOR) {
				token = lexico.getNextToken();
			} else {
				erroSintatico("Faltou 'IDENTIFICADOR'");
			}
		} else {
			erroSintatico("Faltou palavra reservada 'KW_G'");
		}
	}

	// <def_vertice> ::= KW_V FLOAT FLOAT FLOAT
	private void def_vertice() {
		token = lexico.getNextToken();

		if (isPalavraReservada("KW_V")) {
			token = lexico.getNextToken();

			if (token.getClasse() == ClasseTokenOBJ.FLOAT) {
				token = lexico.getNextToken();

				if (token.getClasse() == ClasseTokenOBJ.FLOAT) {
					token = lexico.getNextToken();

					if (token.getClasse() == ClasseTokenOBJ.FLOAT) {
						token = lexico.getNextToken();
					} else {
						erroSintatico("Faltou 'FLOAT'");
					}
				} else {
					erroSintatico("Faltou 'FLOAT'");
				}
			} else {
				erroSintatico("Faltou 'FLOAT'");
			}
		} else {
			erroSintatico("Faltou palavra reservada 'KW_V'");
		}
	}

	// <def_uv> ::= KW_VT FLOAT FLOAT
	private void def_uv() {
		token = lexico.getNextToken();

		if (isPalavraReservada("KW_VT")) {
			token = lexico.getNextToken();

			if (token.getClasse() == ClasseTokenOBJ.FLOAT) {
				token = lexico.getNextToken();

				if (token.getClasse() == ClasseTokenOBJ.FLOAT) {
					token = lexico.getNextToken();
				} else {
					erroSintatico("Faltou 'FLOAT'");
				}
			} else {
				erroSintatico("Faltou 'FLOAT'");
			}
		} else {
			erroSintatico("Faltou palavra reservada 'KW_VT'");
		}

	}

	// <def_normal> ::= KW_VN FLOAT FLOAT FLOAT
	private void def_normal() {
		token = lexico.getNextToken();

		if (isPalavraReservada("KW_VN")) {
			token = lexico.getNextToken();

			if (token.getClasse() == ClasseTokenOBJ.FLOAT) {
				token = lexico.getNextToken();

				if (token.getClasse() == ClasseTokenOBJ.FLOAT) {
					token = lexico.getNextToken();

					if (token.getClasse() == ClasseTokenOBJ.FLOAT) {
						token = lexico.getNextToken();
					} else {
						erroSintatico("Faltou 'FLOAT'");
					}
				} else {
					erroSintatico("Faltou 'FLOAT'");
				}
			} else {
				erroSintatico("Faltou 'FLOAT'");
			}
		} else {
			erroSintatico("Faltou palavra reservada 'KW_VN'");
		}
	}

	// <def_face> ::= KW_F <conjunto> <conjunto> <conjunto>
	private void def_face() {
		token = lexico.getNextToken();

		if (isPalavraReservada("KW_F")) {
			token = lexico.getNextToken();

			conjunto();

			conjunto();

			conjunto();
		} else {
			erroSintatico("Faltou palavra reservada 'KW_F'");
		}
	}

	// <conjunto> ::= INTEIRO <complemento_face>
	private void conjunto() {
		if (token.getClasse() == ClasseTokenOBJ.INTEIRO) {
			token = lexico.getNextToken();

			complemento_face();
		} else {
			erroSintatico("Faltou 'INTEIRO'");
		}
	}

	// <complemento_face> ::= BARRA <dado_textura_ou_normal> | ε
	private void complemento_face() {
		if (token.getClasse() == ClasseTokenOBJ.BARRA) {
			token = lexico.getNextToken();

			dado_textura_ou_normal();
		}
	}

	// <dado_textura_ou_normal> ::= BARRA INTEIRO | INTEIRO <complemento_normal>
	private void dado_textura_ou_normal() {
		if (token.getClasse() == ClasseTokenOBJ.BARRA) {
			token = lexico.getNextToken();

			if (token.getClasse() == ClasseTokenOBJ.INTEIRO) {
				token = lexico.getNextToken();
			} else {
				erroSintatico("Faltou 'INTEIRO'");
			}
		} else if (token.getClasse() == ClasseTokenOBJ.INTEIRO) {
			token = lexico.getNextToken();

			complemento_normal();
		} else {
			erroSintatico("Faltou 'BARRA' ou 'INTEIRO'");
		}
	}

	// <complemento_normal> ::= BARRA INTEIRO | ε
	private void complemento_normal() {
		if (token.getClasse() == ClasseTokenOBJ.BARRA) {
			token = lexico.getNextToken();

			if (token.getClasse() == ClasseTokenOBJ.INTEIRO) {
				token = lexico.getNextToken();
			} else {
				erroSintatico("Faltou 'INTEIRO'");
			}
		}
	}

	private void erroSintatico(String mensagem) {
		System.err.println("Linha: " + token.getLinha() + ", Coluna: " + token.getColuna() + " ['"
				+ token.getValor().getTexto() + "']. Erro Sintático: " + mensagem);
	}

	private boolean isPalavraReservada(final String palavra) {
		var palavrasReservadas = List.of(
				ClasseTokenOBJ.KW_F,
				ClasseTokenOBJ.KW_G,
				ClasseTokenOBJ.KW_MTLLIB,
				ClasseTokenOBJ.KW_O,
				ClasseTokenOBJ.KW_USEMTL,
				ClasseTokenOBJ.KW_V,
				ClasseTokenOBJ.KW_VN,
				ClasseTokenOBJ.KW_VT);

		return (palavrasReservadas.contains(token.getClasse())
				&& token.getValor().getTexto().equalsIgnoreCase(palavra));
	}
}
