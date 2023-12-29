package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece {

    public Bishop(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "B";
    }

    /**
     * Gera uma matriz de booleanos indicando os possíveis movimentos do bispo no tabuleiro.
     * O bispo pode mover-se em diagonais, tanto para cima quanto para baixo, desde que não haja
     * peças bloqueando o caminho, e ele pode capturar peças adversárias.
     *
     * @return Matriz de booleanos representando os possíveis movimentos do bispo.
     */
    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);

        // Movimentos diagonais
        addDiagonalMoves(mat, p, -1, -1); // diagonal superior esquerda
        addDiagonalMoves(mat, p, -1, 1);  // diagonal superior direita
        addDiagonalMoves(mat, p, 1, -1);  // diagonal inferior esquerda
        addDiagonalMoves(mat, p, 1, 1);   // diagonal inferior direita

        return mat;
    }

    /**
     * Adiciona movimentos diagonais à matriz de movimentos possíveis.
     *
     * @param mat       A matriz de booleanos representando os possíveis movimentos.
     * @param p         A posição a ser verificada.
     * @param rowChange A mudança na coordenada da linha (positiva para baixo, negativa para cima).
     * @param colChange A mudança na coordenada da coluna (positiva para a direita, negativa para a esquerda).
     */
    private void addDiagonalMoves(boolean[][] mat, Position p, int rowChange, int colChange) {
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