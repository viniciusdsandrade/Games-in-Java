package application;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.InputMismatchException;
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
    public static @NotNull ChessPosition readChessPosition(Scanner sc) {
        try {
            String s = sc.nextLine();
            char coluna = s.charAt(0);
            int linha = Integer.parseInt(s.substring(1));
            return new ChessPosition(coluna, linha);

        } catch (RuntimeException e) {
            throw new InputMismatchException("Erro ao ler a posição de xadrez. Valores válidos estão entre a1 e h8.");
        }
    }
    
    public static void printMatch(ChessMatch chessMatch) {
        printBoard(chessMatch.getPieces());
        System.out.println();
        System.out.println("Turn: " + chessMatch.getTurn());
        System.out.println("Waiting player: " + chessMatch.getCurrentPlayer());
    }

    public static void printBoard(ChessPiece[][] pieces) {
        StringBuilder sb = new StringBuilder();

        sb.append("  a b c d e f g h\n"); // Adiciona as letras na parte superior
        for (int i = 0; i < pieces.length; i++) {
            sb.append((8 - i)).append(" ");
            for (int j = 0; j < pieces[i].length; j++) {
                printPiece(sb, pieces[i][j]);
            }
            sb.append(8 - i).append("\n"); // Adiciona o número da linha no lado direito
        }
        sb.append("  a b c d e f g h");
        System.out.println(sb);
    }

    private static void printPiece(StringBuilder sb, ChessPiece piece) {
        if (piece == null) {
            sb.append("- ");
        } else {
            String colorCode = (piece.getColor() == Color.WHITE) ? ANSI_WHITE : ANSI_YELLOW;
            sb.append(colorCode).append(piece).append(ANSI_RESET).append(" ");
        }
    }

    public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
        System.out.println("  a b c d e f g h"); // Adiciona as letras na parte superior

        for (int i = 0; i < pieces.length; i++) {
            System.out.print((8 - i) + " ");
            for (int j = 0; j < pieces[i].length; j++) {
                printPiece(pieces[i][j], possibleMoves[i][j]);
            }
            System.out.println(8 - i); // Adiciona o número da linha no lado direito
        }

        System.out.println("  a b c d e f g h");
    }

    private static void printPiece(ChessPiece chessPiece, boolean background) {
        if (background)
            System.out.print(ANSI_BLUE_BACKGROUND);

        if (chessPiece == null) {
            System.out.print("-" + ANSI_RESET);
        } else {
            if (chessPiece.getColor() == Color.WHITE)
                System.out.print(ANSI_WHITE + chessPiece + ANSI_RESET);
            else
                System.out.print(ANSI_YELLOW + chessPiece + ANSI_RESET);
        }
        System.out.print(" ");
    }
}