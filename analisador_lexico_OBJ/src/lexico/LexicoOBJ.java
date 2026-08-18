package lexico;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;

public class LexicoOBJ {
    private String nomeArquivo;
    private BufferedReader br;
    private char caractere;
    private int linha;
    private int coluna;
    private List<Character> caracteresEspeciais = List.of('.', '_');

    public LexicoOBJ(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
        String caminhoArquivo = Paths.get(nomeArquivo).toAbsolutePath().toString();
        try {
            br = new BufferedReader(new FileReader(caminhoArquivo, StandardCharsets.UTF_8));
            caractere = (char) br.read();
        } catch (IOException ex) {
            System.out.println("Erro abrindo o arquivo MTL" + nomeArquivo);
            System.out.println("Caminho do arquivo: " + caminhoArquivo);
        }
        linha = 1;
        coluna = 1;
    }

    public Token getNextToken() {
        StringBuilder lexema;
        Token token;

        try {
            while (caractere != 65535) { // EOF
                lexema = new StringBuilder();
                token = new Token(linha, coluna);

                if (Character.isLetter(caractere)) {
                    while (Character.isLetterOrDigit(caractere) || caracteresEspeciais.contains(caractere)) {
                        lexema.append(caractere);
                        caractere = (char) br.read();
                        coluna++;
                    }

                    switch (lexema.toString()) {
                        case "mtllib" -> token.setClasse(ClasseTokenOBJ.KW_MTLLIB);
                        case "usemtl" -> token.setClasse(ClasseTokenOBJ.KW_USEMTL);
                        case "v" -> token.setClasse(ClasseTokenOBJ.KW_V);
                        case "vt" -> token.setClasse(ClasseTokenOBJ.KW_VT);
                        case "f" -> token.setClasse(ClasseTokenOBJ.KW_F);
                        case "g" -> token.setClasse(ClasseTokenOBJ.KW_G);
                        case "o" -> token.setClasse(ClasseTokenOBJ.KW_O);
                        case "vn" -> token.setClasse(ClasseTokenOBJ.KW_VN);
                        default -> {
                            token.setClasse(ClasseTokenOBJ.IDENTIFICADOR);
                            // System.err.println("Erro Lexico. Caractere Invalido.");
                            // System.exit(1);
                        }
                    }

                    token.setValor(new ValorToken(lexema.toString()));
                    return token;
                } else if (Character.isDigit(caractere) || caractere == '-') {
                    if (caractere == '-') {
                        lexema.append(caractere);
                        caractere = (char) br.read();
                        coluna++;
                    }

                    while (Character.isDigit(caractere)) {
                        lexema.append(caractere);
                        caractere = (char) br.read();
                        coluna++;
                    }

                    if (caractere == '.') {
                        lexema.append(caractere);
                        caractere = (char) br.read();
                        coluna++;

                        while (Character.isDigit(caractere)) {
                            lexema.append(caractere);
                            caractere = (char) br.read();
                            coluna++;
                        }

                        token.setClasse(ClasseTokenOBJ.FLOAT);
                        token.setValor(new ValorToken(Float.parseFloat(lexema.toString())));
                    } else {
                        token.setClasse(ClasseTokenOBJ.INTEIRO);
                        token.setValor(new ValorToken(Integer.parseInt(lexema.toString())));
                    }

                    return token;
                } else if (caractere == '/') {
                    caractere = (char) br.read();
                    coluna++;
                    token.setClasse(ClasseTokenOBJ.BARRA);
                    return token;
                } else if (caractere == ' ' || caractere == '\t') {
                    caractere = (char) br.read();
                    coluna++;
                } else if (caractere == '\n') {
                    linha++;
                    coluna = 1;
                    caractere = (char) br.read();
                } else if (caractere == '#') {
                    caractere = (char) br.read();
                    coluna++;
                    while (caractere != '\n' && caractere != 65535) {
                        caractere = (char) br.read();
                        coluna++;
                    }
                    if (caractere == '\n') {
                        linha++;
                        coluna = 1;
                        caractere = (char) br.read();
                    } else if (caractere == 65535) {
                        token = new Token(linha, coluna);
                        token.setClasse(ClasseTokenOBJ.EOF);
                        return token;
                    }
                } else {
                    System.out.println("caractere: " + caractere + ", lexema: " + lexema.toString());
                    System.err.println("Erro Lexico. Caractere Invalido.");
                    System.exit(1);
                }
            }
            token = new Token(linha, coluna);
            token.setClasse(ClasseTokenOBJ.EOF);
            return token;
        } catch (

        IOException e) {
            System.err.println("Não foi possível ler do arquivo: " + nomeArquivo);
        }
        return null;
    }

}
