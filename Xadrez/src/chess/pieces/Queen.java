package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Queen extends ChessPiece {
    public Queen(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "Q";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);

        moveInDirection(mat, p, -1, 0);  // vertical acima
        moveInDirection(mat, p, 1, 0);   // vertical abaixo
        moveInDirection(mat, p, 0, -1);  // horizontal à esquerda
        moveInDirection(mat, p, 0, 1);   // horizontal à direita
        moveInDirection(mat, p, -1, -1); // diagonal superior esquerda
        moveInDirection(mat, p, -1, 1);  // diagonal superior direita
        moveInDirection(mat, p, 1, -1);  // diagonal inferior esquerda
        moveInDirection(mat, p, 1, 1);   // diagonal inferior direita

        return mat;
    }

    /**
     * Move na direção especificada até encontrar uma peça ou alcançar os limites do tabuleiro.
     *
     * @param mat       A matriz de booleanos representando os possíveis movimentos.
     * @param p         A posição inicial.
     * @param rowChange A mudança na coordenada da linha.
     * @param colChange A mudança na coordenada da coluna.
     */
    private void moveInDirection(boolean[][] mat, Position p, int rowChange, int colChange) {
        p.setValues(position.getRow() + rowChange, position.getColumn() + colChange);

        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() + rowChange, p.getColumn() + colChange);
        }

        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }
    }

}
