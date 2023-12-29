package application;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class UI {

    public static final String ANSI_RESET = "\u001B[0m"; // Reseta a cor
    public static final String ANSI_BLACK = "\u001B[30m"; // Cor preta
    public static final String ANSI_RED = "\u001B[31m"; // Cor vermelha
    public static final String ANSI_GREEN = "\u001B[32m"; // Cor verde
    public static final String ANSI_YELLOW = "\u001B[33m"; // Cor amarela
    public static final String ANSI_BLUE = "\u001B[34m"; // Cor azul
    public static final String ANSI_PURPLE = "\u001B[35m"; // Cor roxa
    public static final String ANSI_CYAN = "\u001B[36m"; // Cor ciano
    public static final String ANSI_WHITE = "\u001B[37m"; // Cor branca

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m"; // Fundo preto
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m"; // Fundo vermelho
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m"; // Fundo verde
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m"; // Fundo amarelo
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m"; // Fundo azul
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m"; // Fundo roxo
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m"; // Fundo ciano
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m"; // Fundo branco

    /**
     * Limpa a tela.
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Lê uma posição de xadrez do Scanner fornecido.
     *
     * @param sc O Scanner a partir do qual ler a posição de xadrez.
     * @return O objeto ChessPosition representando a posição lida.
     * @throws InputMismatchException Se a entrada não representa uma posição de xadrez válida (por exemplo, "a1" a "h8").
     */
    public static ChessPosition readChessPosition(Scanner sc) {
        try {
            String s = sc.nextLine();
            char coluna = s.charAt(0);
            int linha = Integer.parseInt(s.substring(1));
            return new ChessPosition(coluna, linha);

        } catch (RuntimeException e) {
            throw new InputMismatchException("Erro ao ler a posição de xadrez. Valores válidos estão entre a1 e h8.");
        }
    }

    /**
     * Imprime o estado atual da partida de xadrez no console, incluindo o tabuleiro,
     * as peças capturadas, o turno e o jogador da vez. Além disso, se houver xeque,
     * imprime a mensagem "CHECK!".
     *
     * @param chessMatch A instância da partida de xadrez.
     * @param captured A lista de peças capturadas durante a partida.
     */
    public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
        printBoard(chessMatch.getPieces());
        System.out.println();
        printCapturedPieces(captured);
        System.out.println();
        System.out.println("Turn : " + chessMatch.getTurn());
        if (!chessMatch.getCheckMate()) {
            System.out.println("Waiting player: " + chessMatch.getCurrentPlayer());
            if (chessMatch.getCheck()) {
                System.out.println("CHECK!");
            }
        }
        else {
            System.out.println("CHECKMATE!");
            System.out.println("Winner: " + chessMatch.getCurrentPlayer());
        }
    }

    /**
     * Imprime o tabuleiro de xadrez no console, com coordenadas e peças destacadas por cor.
     *
     * @param pieces A matriz de peças que representa o estado atual do tabuleiro.
     */
    public static void printBoard(ChessPiece[][] pieces) {
        StringBuilder sb = new StringBuilder();

        // Adiciona as letras na parte superior do tabuleiro
        sb.append("  a b c d e f g h\n");

        for (int i = 0; i < pieces.length; i++) {
            // Adiciona o número da linha à esquerda
            sb.append((8 - i)).append(" ");

            // Adiciona as peças na linha atual
            for (int j = 0; j < pieces[i].length; j++) {
                printPiece(sb, pieces[i][j]);
            }

            // Adiciona o número da linha à direita
            sb.append(8 - i).append("\n");
        }

        // Adiciona as letras na parte inferior do tabuleiro
        sb.append("  a b c d e f g h");
        System.out.println(sb);
    }

    /**
     * Imprime uma peça de xadrez no console, destacando a cor da peça com ANSI codes.
     *
     * @param sb O StringBuilder que armazena a representação do tabuleiro.
     * @param piece A peça de xadrez a ser impressa.
     */
    private static void printPiece(StringBuilder sb, ChessPiece piece) {
        if (piece == null) {
            sb.append("- ");
        } else {
            String colorCode = (piece.getColor() == Color.WHITE) ? ANSI_WHITE : ANSI_YELLOW;
            sb.append(colorCode).append(piece).append(ANSI_RESET).append(" ");
        }
    }

    /**
     * Imprime o tabuleiro de xadrez no console, com destaque para os movimentos possíveis.
     *
     * @param pieces A matriz de peças que representa o estado atual do tabuleiro.
     * @param possibleMoves A matriz booleana indicando os movimentos possíveis.
     */
    public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
        System.out.println("  a b c d e f g h");

        for (int i = 0; i < pieces.length; i++) {
            System.out.print((8 - i) + " ");

            for (int j = 0; j < pieces[i].length; j++) {
                printPiece(pieces[i][j], possibleMoves[i][j]);
            }

            System.out.println(8 - i);
        }

        System.out.println("  a b c d e f g h");
    }

    /**
     * Imprime uma peça de xadrez no console, com destaque para movimentos possíveis.
     *
     * @param chessPiece A peça de xadrez a ser impressa.
     * @param background Indica se o fundo da peça deve ser destacado.
     */
    private static void printPiece(ChessPiece chessPiece, boolean background) {
        if (background) {
            System.out.print(ANSI_BLUE_BACKGROUND);
        }

        if (chessPiece == null) {
            System.out.print("-" + ANSI_RESET);
        } else {
            String colorCode = (chessPiece.getColor() == Color.WHITE) ? ANSI_WHITE : ANSI_YELLOW;
            System.out.print(colorCode + chessPiece + ANSI_RESET);
        }

        System.out.print(" ");
    }


    /**
     * Imprime as peças capturadas no console, destacando as peças brancas e pretas.
     <p>
     * Este método recebe uma lista de peças capturadas, filtra-as por cor (branca ou preta)
     * e imprime as peças brancas e pretas em cores diferentes no console.
     *
     * @param captured Lista de peças capturadas.
     */
    private static void printCapturedPieces(List<ChessPiece> captured) {
        
        // Filtra as peças capturadas por cor
        List<ChessPiece> white = captured.stream().filter(x -> x.getColor() == Color.WHITE).toList();
        List<ChessPiece> black = captured.stream().filter(x -> x.getColor() == Color.BLACK).toList();

        // Imprime o cabeçalho
        System.out.println("Captured pieces:");

        // Imprime as peças brancas
        System.out.print("White: ");
        System.out.print(ANSI_WHITE);
        System.out.println(Arrays.toString(white.toArray()));
        System.out.print(ANSI_RESET);

        // Imprime as peças pretas
        System.out.print("Black: ");
        System.out.print(ANSI_YELLOW);
        System.out.println(Arrays.toString(black.toArray()));
        System.out.print(ANSI_RESET);
    }
}