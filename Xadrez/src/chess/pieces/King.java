package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

    public King(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "K";
    }

    /**
     * Verifica se a peça pode se mover para a posição especificada.
     *
     * @param position A posição a ser verificada.
     * @return true se a peça pode se mover para a posição, false caso contrário.
     */
    private boolean canMove(Position position) {
        ChessPiece p = (ChessPiece) getBoard().piece(position);
        return p == null || p.getColor() != getColor();
    }

    /**
     * Gera uma matriz de booleanos indicando os possíveis movimentos do rei no tabuleiro.
     * O rei pode mover-se para qualquer posição adjacente (acima, abaixo, esquerda, direita
     * ou em qualquer diagonal) desde que não haja peças bloqueando o caminho.
     *
     * @return Matriz de booleanos representando os possíveis movimentos do rei.
     */
    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);

        int[][] directions = {
                {-1, 0},    // acima
                {1, 0},     // abaixo
                {0, -1},    // esquerda
                {0, 1},     // direita
                {-1, -1},   // diagonal superior esquerda
                {-1, 1},    // diagonal superior direita
                {1, -1},    // diagonal inferior esquerda
                {1, 1}      // diagonal inferior direita
        };

        for (int[] direction : directions) {
            p.setValues(position.getRow() + direction[0], position.getColumn() + direction[1]);

            // se a posição existe e pode mover
            if (getBoard().positionExists(p) && canMove(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }
        }
        return mat;
    }
}