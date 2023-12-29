package boardgame;

public abstract class Piece {

    protected Position position;
    private Board board;

    public Piece(Board board) {
        this.board = board;
        position = null;
    }

    protected Board getBoard() {
        return board;
    }

    public abstract boolean[][] possibleMoves();

    /**
     * Verifica se existe algum movimento possível para a peça.
     *
     * @return true se houver pelo menos um movimento possível, false caso contrário.
     */
    public boolean isThereAnyPossibleMove() {
        boolean[][] mat = possibleMoves();
        for (boolean[] booleans : mat) {
            for (int j = 0; j < mat.length; j++) {
                if (booleans[j]) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Verifica se há um movimento possível para a peça na posição especificada.
     *
     * @param position A posição para a qual se deseja verificar o movimento.
     * @return true se houver um movimento possível na posição, false caso contrário.
     */
    public boolean possibleMove(Position position) {
        return possibleMoves()[position.getRow()][position.getColumn()];
    }
}