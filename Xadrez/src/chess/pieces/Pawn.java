package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {
    
    public Pawn(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "P";
    }

    /**
     * Gera uma matriz de booleanos indicando os possíveis movimentos do peão no tabuleiro.
     * O peão pode mover-se para frente, capturar peças na diagonal e realizar um movimento inicial duplo.
     * A matriz gerada representa os possíveis destinos que o peão pode alcançar.
     *
     * @return Matriz de booleanos representando os possíveis movimentos do peão.
     */
    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);
        
        if (getColor() == Color.WHITE) {
            // WHITE
            addForwardMove(mat, p, -1); // Movimento para frente

            if (getMoveCount() == 0)
                addForwardMove(mat, p, -2); // Movimento inicial duplo

            addCaptureMove(mat, p, -1, -1); // Captura na diagonal esquerda

            addCaptureMove(mat, p, -1, 1);  // Captura na diagonal direita
        } else {
            // BLACK
            addForwardMove(mat, p, 1); // Movimento para frente

            if (getMoveCount() == 0)
                addForwardMove(mat, p, 2); // Movimento inicial duplo

            addCaptureMove(mat, p, 1, -1); // Captura na diagonal esquerda
            addCaptureMove(mat, p, 1, 1);  // Captura na diagonal direita
        }

        return mat;
    }

    /**
     * Adiciona um movimento para frente à matriz de movimentos possíveis.
     *
     * @param mat       A matriz de booleanos representando os possíveis movimentos.
     * @param p         A posição a ser verificada.
     * @param rowChange A mudança na coordenada da linha (positiva para baixo, negativa para cima).
     */
    private void addForwardMove(boolean[][] mat, Position p, int rowChange) {
        p.setValues(position.getRow() + rowChange, position.getColumn());
        if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }
    }

    /**
     * Adiciona um movimento de captura à matriz de movimentos possíveis.
     *
     * @param mat       A matriz de booleanos representando os possíveis movimentos.
     * @param p         A posição a ser verificada.
     * @param rowChange A mudança na coordenada da linha (positiva para baixo, negativa para cima).
     * @param colChange A mudança na coordenada da coluna (positiva para a direita, negativa para a esquerda).
     */
    private void addCaptureMove(boolean[][] mat, Position p, int rowChange, int colChange) {
        p.setValues(position.getRow() + rowChange, position.getColumn() + colChange);
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }
    }
}