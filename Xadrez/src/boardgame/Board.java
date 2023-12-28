package boardgame;

import exception.BoardException;

public class Board {

    public int rows;
    public int columns;
    private Piece[][] pieces;

    public Board(int rows, int columns) {

        if (rows < 1 || columns < 1)
            throw new BoardException("Error creating board: there must be at least 1 row and 1 column");

        this.rows = rows;
        this.columns = columns;
        pieces = new Piece[rows][columns];
    }

    public int getRows() {
        return rows;
    }
    public int getColumns() {
        return columns;
    }
    
    /**
     * Obtém a peça na posição especificada do tabuleiro, utilizando coordenadas de linhas e colunas.
     *
     * @param row    Número da linha.
     * @param column Número da coluna.
     * @return Peça na posição especificada.
     * @throws BoardException Se a posição não estiver no tabuleiro.
     */
    public Piece piece(int row, int column) {
        if (!positionExists(row, column))
            throw new BoardException("Position not on the board");

        return pieces[row][column];
    }

    /**
     * Obtém a peça na posição especificada do tabuleiro, utilizando um objeto do tipo Position.
     *
     * @param position Posição da peça contendo atributos Row e Column.
     * @return Peça na posição especificada.
     * @throws BoardException Se a posição não estiver no tabuleiro.
     */
    public Piece piece(Position position) {
        if (!positionExists(position))
            throw new BoardException("Position not on the board");

        return pieces[position.getRow()][position.getColumn()];
    }
    
    /**
     * Coloca uma peça na posição especificada do tabuleiro.
     *
     * @param piece    Peça a ser colocada no tabuleiro.
     * @param position Posição onde a peça será colocada.
     * @throws BoardException Se já houver uma peça na posição especificada.
     */
    public void placePiece(Piece piece, Position position) {
        if (thereIsAPiece(position))
            throw new BoardException("There is already a piece on position " + position);

        pieces[position.getRow()][position.getColumn()] = piece;

        piece.position = position;
    }

    /**
     * Remove e retorna a peça da posição especificada do tabuleiro.
     *
     * @param position Posição da peça a ser removida.
     * @return Peça removida ou null se não houver peça na posição.
     * @throws BoardException Se a posição não estiver no tabuleiro.
     */
    public Piece removePiece(Position position) {
        // Verifica se a posição está dentro dos limites do tabuleiro
        if (!positionExists(position))
            throw new BoardException("Position not on the board");

        // Verifica se há uma peça na posição especificada
        if (piece(position) == null)
            return null;

        // Obtém a referência da peça na posição
        Piece aux = piece(position);

        // Remove a referência da posição da peça (peça não está mais no tabuleiro)
        aux.position = null;

        // Remove a peça do tabuleiro
        pieces[position.getRow()][position.getColumn()] = null;

        // Retorna a peça removida
        return aux;
    }

    /**
     * Verifica se há uma peça na posição especificada do tabuleiro.
     *
     * @param position Posição a ser verificada.
     * @return Verdadeiro se houver uma peça na posição, falso caso contrário.
     * @throws BoardException Se a posição não estiver no tabuleiro.
     */
    public boolean thereIsAPiece(Position position) {

        if (!positionExists(position))
            throw new BoardException("Position not on the board");

        return piece(position) != null;
    }

    /**
     * Verifica se a posição especificada existe no tabuleiro.
     *
     * @param position Posição a ser verificada.
     * @return Verdadeiro se a posição existir, falso caso contrário.
     */
    public boolean positionExists(Position position) {
        return positionExists(position.getRow(), position.getColumn());
    }

    /**
     * Verifica se a posição especificada existe no tabuleiro.
     *
     * @param row    Número da linha.
     * @param column Número da coluna.
     * @return Verdadeiro se a posição existir, falso caso contrário.
     */
    private boolean positionExists(int row, int column) {
        return row >= 0 && row < rows &&
                column >= 0 && column < columns;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("  a b c d e f g h\n"); // Adiciona as letras na parte superior
        for (int row = rows - 1; row >= 0; row--) {
            sb.append(row + 1).append(" "); // Adiciona o número da linha no lado esquerdo
            for (int col = 0; col < columns; col++) {
                Piece piece = pieces[row][col];
                sb.append((piece == null) ? "- " : piece + " ");
            }
            sb.append(row + 1).append("\n"); // Adiciona o número da linha no lado direito
        }
        sb.append("  a b c d e f g h");
        return sb.toString();
    }
}