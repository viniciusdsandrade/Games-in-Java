package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece {

    public Knight(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "N";
    }

    /**
     * Gera uma matriz de booleanos indicando os possíveis movimentos do cavalo no tabuleiro de xadrez.
     * O cavalo pode realizar movimentos em "L", pulando sobre outras peças. Os movimentos são representados
     * por deslocamentos relativos, permitindo movimentos em diversas direções.
     *
     * @return Matriz de booleanos representando os possíveis movimentos do cavalo.
     */
    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        // Movimentos em L
        int[][] moves = {
                {-1, -2}, {-2, -1}, // Movimento acima
                {-2, 1}, {-1, 2},   // Movimento à esquerda
                {1, 2}, {2, 1},     // Movimento abaixo
                {2, -1}, {1, -2}    // Movimento à direita
        };

        Position p = new Position(0, 0);

        for (int[] move : moves) {
            p.setValues(position.getRow() + move[0], position.getColumn() + move[1]);

            // Verifica se a posição é válida e se o cavalo pode mover para lá
            if (getBoard().positionExists(p) && canMove(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }
        }

        return mat;
    }

    /**
     * Verifica se o cavalo pode mover para a posição especificada.
     * O cavalo pode mover para uma posição se ela estiver vazia (sem peça) ou contiver uma peça adversária.
     *
     * @param p A posição a ser verificada.
     * @return true se o cavalo pode mover para a posição, false caso contrário.
     */
    private boolean canMove(Position p) {
        // Obtém a peça na posição especificada no tabuleiro
        ChessPiece piece = (ChessPiece) getBoard().piece(p);

        // Retorna true se a posição estiver vazia ou se contiver uma peça adversária
        return piece == null || piece.getColor() != getColor();
    }

}
