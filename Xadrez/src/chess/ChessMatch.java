package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.*;
import exception.BoardException;

public class ChessMatch {

    private int turn;
    private final Board board;
    private Color currentPlayer;
    
    public ChessMatch() {
        board = new Board(8, 8);
        turn = 1;
        currentPlayer = Color.WHITE;
        initialSetup();
    }

    public int getTurn() {
        return turn;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Obtém a matriz de peças do tabuleiro de xadrez.
     *<p>
     * @return Uma matriz bidimensional de objetos ChessPiece representando o estado atual do tabuleiro de xadrez.
     * Cada elemento da matriz corresponde a uma peça na posição correspondente do tabuleiro.
     * As peças podem ser nulas se não houver peça na posição correspondente.
     * @see ChessPiece
     */
    public ChessPiece[][] getPieces() {
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                mat[i][j] = (ChessPiece) board.piece(i, j);
            }
        }
        return mat;
    }

    /**
     * Obtém uma matriz de movimentos possíveis para a peça na posição de origem no tabuleiro de xadrez.
     *<p>
     * Este método converte a posição de origem do formato ChessPosition para Position, valida a posição de origem,
     * e retorna uma matriz booleana indicando os movimentos possíveis para a peça na posição de origem.
     *<p>
     * @param sourcePosition A posição de origem no formato ChessPosition.
     * @return Uma matriz booleana representando os movimentos possíveis da peça na posição de origem.
     */
    public boolean[][] possibleMoves(ChessPosition sourcePosition) {
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();
    }

    /**
     * Executa um movimento de xadrez a partir da posição de origem para a posição de destino.
     *<p>
     * Este método converte as posições de origem e destino do formato ChessPosition para Position,
     * valida a posição de origem, valida a posição de destino e realiza o movimento no tabuleiro.
     * Retorna a peça capturada, se houver, após o movimento, e avança para o próximo turno.
     *<p>
     * @param sourcePosition A posição de origem no formato ChessPosition.
     * @param targetPosition A posição de destino no formato ChessPosition.
     * @return A peça capturada, se houver, após o movimento.
     */
    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target);
        nextTurn();
        return (ChessPiece) capturedPiece;
    }
    
    /**
     * Valida a posição de origem para realizar um movimento de xadrez.
     * <p>Este método verifica se há uma peça na posição de origem, se a peça escolhida possui
     * movimentos possíveis e se a peça pertence ao jogador atual.
     * <p>Caso contrário, lança uma exceção apropriada.
     * @param position A posição de origem a ser validada.
     * @throws BoardException Se não houver uma peça na posição de origem,
     *                        se a peça escolhida não tiver movimentos possíveis,
     *                        ou se a peça não pertencer ao jogador atual.
     * @see BoardException
     */
    private void validateSourcePosition(Position position) {

        if (!board.thereIsAPiece(position))
            throw new BoardException("There is no piece on source position");

        if (!board.piece(position).isThereAnyPossibleMove())
            throw new BoardException("There is no possible moves for the chosen piece");

        if (currentPlayer != ((ChessPiece) board.piece(position)).getColor())
            throw new BoardException("The chosen piece is not yours");
    }
    
    /**
     * Valida a posição de destino para realizar um movimento de xadrez a partir de uma posição de origem.
     * <p>
     * Este método verifica se a peça na posição de origem pode se mover para a posição de destino.
     * <p>Caso contrário, lança uma exceção indicando que a peça escolhida não pode se mover para a posição
     * de destino especificada.
     *
     * @param source A posição de origem da peça.
     * @param target A posição de destino para onde a peça está tentando se mover.
     * @throws BoardException Se a peça escolhida não puder se mover para a posição de destino.
     * @see BoardException
     */
    private void validateTargetPosition(Position source, Position target) {

        if (!board.piece(source).possibleMove(target))
            throw new BoardException("The chosen piece can't move to target position");
    }

    /**
     * Realiza um movimento de xadrez a partir da posição de origem para a posição de destino.
     * <p>
     * Este método move a peça da posição de origem para a posição de destino no tabuleiro de xadrez,
     * removendo quaisquer peças adversárias presentes na posição de destino. Retorna a peça capturada,
     * se houver, após o movimento.
     *
     * @param source A posição de origem da peça.
     * @param target A posição de destino para onde a peça está se movendo.
     * @return A peça capturada, se houver, após o movimento.
     */
    private Piece makeMove(Position source, Position target) {
        ChessPiece movedPiece = (ChessPiece) board.removePiece(source);
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(movedPiece, target);

        return capturedPiece;
    }

    /**
     * Avança para o próximo turno da partida de xadrez.
     * <p>
     * Este método incrementa o contador de turnos e alterna o jogador atual entre branco e preto.
     */
    private void nextTurn() {
        turn++;
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    /**
     * Coloca uma nova peça no tabuleiro usando coordenadas no formato (coluna, linha).
     *
     * @param column Coluna onde a peça será colocada. Deve estar no intervalo de 'a' a 'h'.
     * @param row    Linha onde a peça será colocada. Deve estar no intervalo de 1 a 8.
     * @param piece  Peça a ser colocada no tabuleiro.
     * @throws IllegalArgumentException Se a coluna ou a linha estiverem fora dos intervalos válidos.
     * @throws BoardException           Se houver uma peça na posição especificada ou se a posição não estiver no tabuleiro.
     * @see ChessPosition
     */
    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
    }

    private void initialSetup() {
        // Peças pretas
        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
//        for (char col = 'a'; col <= 'h'; col++)
//            placeNewPiece(col, 7, new Pawn(board, Color.BLACK));

        // Peças brancas
        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('b', 1, new Knight(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
//        for (char col = 'a'; col <= 'h'; col++)
//            placeNewPiece(col, 2, new Pawn(board, Color.WHITE));
    }
}