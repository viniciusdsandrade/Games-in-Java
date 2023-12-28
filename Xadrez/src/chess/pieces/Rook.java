package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece {

    public Rook(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "R";
    }

    /**
     * Gera uma matriz de booleanos indicando os possíveis movimentos da torre no tabuleiro.
     * A torre pode mover-se verticalmente (acima e abaixo) e horizontalmente (esquerda e direita)
     * desde que não haja peças bloqueando o caminho, e ela pode capturar peças adversárias.
     *
     * @return Matriz de booleanos representando os possíveis movimentos da torre.
     */
    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);

        int[][] directions = {
                {-1, 0}, // acima
                {1, 0}, // abaixo
                {0, -1}, // esquerda
                {0, 1}, // direita
        };

        for (int[] direction : directions) {
            p.setValues(position.getRow() + direction[0], position.getColumn() + direction[1]);

            // enquanto a posição existir e não houver peça
            while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
                p.setValues(p.getRow() + direction[0], p.getColumn() + direction[1]);
            }

            // se a posição existe e há uma peça adversária
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }
        }
        return mat;
    }
}