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
		if (token.getValor() != null) {
			material();
	
			materiais();
		}
	}

	// <material>::= KW_NEWMTL IDENTIFICADOR <propriedades>
	private void material() {
		if (isPalavraReservada("KW_NEWMTL") || isPalavraReservada("NEWMTL")) {
			token = lexico.getNextToken();

			if (token.getClasse() == ClasseTokenMTL.IDENTIFICADOR) {
				token = lexico.getNextToken();

				propriedades();
			} else {
				erroSintatico("Faltou 'IDENTIFICADOR'");
			}
		} else {
			erroSintatico("Faltou palavra reservada 'NEWMTL'");
		}
	}

	// <propriedades>::= <propriedade> <propriedades> | ε
	private void propriedades() {
		if (token.getValor() != null) {
			propriedade();
	
			propriedades();
		}
	}

	// <propriedade>::= <cor_amb> | <cor_difusa> | <cor_spec> | <exp_spec> |
	// <mod_ilum> | <mapa_textura>
	private void propriedade() {
		switch (token.getValor().getTexto().toLowerCase()) {
			case "ka" -> cor_amb();
			case "kd" -> cor_difusa();
			case "ks" -> cor_spec();
			case "ns" -> exp_spec();
			case "illum" -> mod_ilum();
			case "map_kd" -> mapa_textura();
			default -> erroSintatico("Token de propriedade inválido");
		}
	}

	// <cor_amb>::= KW_KA FLOAT FLOAT FLOAT
	private void cor_amb() {
		if (isPalavraReservada("KA")) {
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
			erroSintatico("Faltou palavra reservada 'KA'");
		}
	}

	// <cor_difusa>::= KW_KD FLOAT FLOAT FLOAT
	private void cor_difusa() {
		if (isPalavraReservada("KD")) {
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
			erroSintatico("Faltou palavra reservada 'KD'");
		}
	}

	// <cor_spec>::= KW_KS FLOAT FLOAT FLOAT
	private void cor_spec() {
		if (isPalavraReservada("KS")) {
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
			erroSintatico("Faltou palavra reservada 'KS'");
		}
	}

	// <exp_spec>::= KW_NS FLOAT
	private void exp_spec() {
		if (isPalavraReservada("NS")) {
			token = lexico.getNextToken();

			if (token.getClasse() == ClasseTokenMTL.FLOAT) {
				token = lexico.getNextToken();
			} else {
				erroSintatico("Faltou 'FLOAT'");
			}
		} else {
			erroSintatico("Faltou palavra reservada 'NS'");
		}
	}

	// <mod_ilum>::= KW_ILLUM INTEIRO
	private void mod_ilum() {
		if (isPalavraReservada("ILLUM")) {
			token = lexico.getNextToken();

			if (token.getClasse() == ClasseTokenMTL.INTEIRO) {
				token = lexico.getNextToken();
			} else {
				erroSintatico("Faltou 'INTEIRO'");
			}
		} else {
			erroSintatico("Faltou palavra reservada 'ILLUM'");
		}
	}

	// <mapa_textura>::= KW_MAP_KD IDENTIFICADOR
	private void mapa_textura() {
		if (isPalavraReservada("MAP_KD")) {
			token = lexico.getNextToken();

			if (token.getClasse() == ClasseTokenMTL.IDENTIFICADOR) {
				token = lexico.getNextToken();
			} else {
				erroSintatico("Faltou 'IDENTIFICADOR'");
			}
		} else {
			erroSintatico("Faltou palavra reservada 'MAP_KD'");
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
