package analisador.sintatico.mtl.sintatico;

import java.util.List;

import analisador.sintatico.mtl.lexico.ClasseTokenMTL;
import analisador.sintatico.mtl.lexico.LexicoMTL;
import analisador.sintatico.mtl.lexico.Token;

public class Sintatico {
	private final LexicoMTL lexico;
	private Token token;

	public Sintatico(LexicoMTL lexico) {
		this.lexico = lexico;
	}

	public void analisar() {
		token = lexico.getNextToken();
		arquivo_mtl();
	}

	// <arquivo_mtl>::= <materiais> EOF
	private void arquivo_mtl() {
		materiais();

		if (token.getClasse() == ClasseTokenMTL.EOF) {
			token = lexico.getNextToken();
		} else {
			erroSintatico("Faltou 'EOF'");
		}
	}

	// <materiais>::= <material> <materiais> | ε
	private void materiais() {
		material();

		materiais();
	}

	// <material>::= KW_NEWMTL IDENTIFICADOR <propriedades>
	private void material() {
		if (isPalavraReservada("KW_NEWMTL")) {
			token = lexico.getNextToken();

			if (token.getClasse() == ClasseTokenMTL.IDENTIFICADOR) {
				token = lexico.getNextToken();

				propriedades();
			} else {
				erroSintatico("Faltou 'IDENTIFICADOR'");
			}
		} else {
			erroSintatico("Faltou palavra reservada 'KW_NEWMTL'");
		}
	}

	// <propriedades>::= <propriedade> <propriedades> | ε
	private void propriedades() {
		propriedade();

		propriedades();
	}

	// <propriedade>::= <cor_amb> | <cor_difusa> | <cor_spec> | <exp_spec> |
	// <mod_ilum> | <mapa_textura>
	private void propriedade() {
		switch (token.getValor().getTexto()) {
			case "cor_amb" -> cor_amb();
			case "cor_difusa" -> cor_difusa();
			case "cor_spec" -> cor_spec();
			case "exp_spec" -> exp_spec();
			case "mod_ilum" -> mod_ilum();
			case "mapa_textura" -> mapa_textura();
			default -> erroSintatico("Token de propriedade inválido");
		}
	}

	// <cor_amb>::= KW_KA FLOAT FLOAT FLOAT
	private void cor_amb() {
		token = lexico.getNextToken();

		if (isPalavraReservada("KW_KA")) {
			token = lexico.getNextToken();

			if (token.getClasse() == ClasseTokenMTL.FLOAT) {
				token = lexico.getNextToken();

				if (token.getClasse() == ClasseTokenMTL.FLOAT) {
					token = lexico.getNextToken();

					if (token.getClasse() == ClasseTokenMTL.FLOAT) {
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
			erroSintatico("Faltou palavra reservada 'KW_KA'");
		}
	}

	// <cor_difusa>::= KW_KD FLOAT FLOAT FLOAT
	private void cor_difusa() {
		token = lexico.getNextToken();

		if (isPalavraReservada("KW_KD")) {
			token = lexico.getNextToken();

			if (token.getClasse() == ClasseTokenMTL.FLOAT) {
				token = lexico.getNextToken();

				if (token.getClasse() == ClasseTokenMTL.FLOAT) {
					token = lexico.getNextToken();

					if (token.getClasse() == ClasseTokenMTL.FLOAT) {
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
			erroSintatico("Faltou palavra reservada 'KW_KD'");
		}
	}

	// <cor_spec>::= KW_KS FLOAT FLOAT FLOAT
	private void cor_spec() {
		token = lexico.getNextToken();

		if (isPalavraReservada("KW_KS")) {
			token = lexico.getNextToken();

			if (token.getClasse() == ClasseTokenMTL.FLOAT) {
				token = lexico.getNextToken();

				if (token.getClasse() == ClasseTokenMTL.FLOAT) {
					token = lexico.getNextToken();

					if (token.getClasse() == ClasseTokenMTL.FLOAT) {
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
			erroSintatico("Faltou palavra reservada 'KW_KS'");
		}
	}

	// <exp_spec>::= KW_NS FLOAT
	private void exp_spec() {
		token = lexico.getNextToken();

		if (isPalavraReservada("KW_NS")) {
			token = lexico.getNextToken();

			if (token.getClasse() == ClasseTokenMTL.FLOAT) {
				token = lexico.getNextToken();
			} else {
				erroSintatico("Faltou 'FLOAT'");
			}
		} else {
			erroSintatico("Faltou palavra reservada 'KW_NS'");
		}
	}

	// <mod_ilum>::= KW_ILLUM INTEIRO
	private void mod_ilum() {
		token = lexico.getNextToken();

		if (isPalavraReservada("KW_ILLUM")) {
			token = lexico.getNextToken();

			if (token.getClasse() == ClasseTokenMTL.INTEIRO) {
				token = lexico.getNextToken();
			} else {
				erroSintatico("Faltou 'INTEIRO'");
			}
		} else {
			erroSintatico("Faltou palavra reservada 'KW_ILLUM'");
		}
	}

	// <mapa_textura>::= KW_MAP_KD IDENTIFICADOR
	private void mapa_textura() {
		token = lexico.getNextToken();

		if (isPalavraReservada("KW_MAP_KD")) {
			token = lexico.getNextToken();

			if (token.getClasse() == ClasseTokenMTL.IDENTIFICADOR) {
				token = lexico.getNextToken();
			} else {
				erroSintatico("Faltou 'IDENTIFICADOR'");
			}
		} else {
			erroSintatico("Faltou palavra reservada 'KW_MAP_KD'");
		}
	}

	private void erroSintatico(String mensagem) {
		System.err.println("Linha: " + token.getLinha() + ", Coluna: " + token.getColuna() + " ['"
				+ token.getValor().getTexto() + "']. Erro Sintático: " + mensagem);
	}

	private boolean isPalavraReservada(final String palavra) {
		var palavrasReservadas = List.of(
				ClasseTokenMTL.KW_ILLUM,
				ClasseTokenMTL.KW_KA,
				ClasseTokenMTL.KW_KD,
				ClasseTokenMTL.KW_KS,
				ClasseTokenMTL.KW_MAP_KD,
				ClasseTokenMTL.KW_NEWMTL,
				ClasseTokenMTL.KW_NS);

		return (palavrasReservadas.contains(token.getClasse())
				&& token.getValor().getTexto().equalsIgnoreCase(palavra));
	}
}
