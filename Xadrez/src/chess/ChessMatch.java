package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.*;
import exception.BoardException;

import java.util.ArrayList;
import java.util.List;

public class ChessMatch {

    private int turn;
    private final Board board;
    private Color currentPlayer;
    private boolean check;

    private List<Piece> piecesOnTheBoard = new ArrayList<>();
    private List<Piece> capturedPieces = new ArrayList<>();

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

    public boolean getCheck() {
        return check;
    }

    /**
     * Obtém a matriz de peças do tabuleiro de xadrez.
     * <p>
     *
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
     * <p>
     * Este método converte a posição de origem do formato ChessPosition para Position, valida a posição de origem,
     * e retorna uma matriz booleana indicando os movimentos possíveis para a peça na posição de origem.
     * <p>
     *
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
     * <p>
     * Este método converte as posições de origem e destino do formato ChessPosition para Position,
     * valida a posição de origem, valida a posição de destino e realiza o movimento no tabuleiro.
     * Retorna a peça capturada, se houver, após o movimento, e avança para o próximo turno.
     * Em caso de xeque no próprio Rei após o movimento, desfaz o movimento e lança uma exceção.
     * <p>
     *
     * @param sourcePosition A posição de origem no formato ChessPosition.
     * @param targetPosition A posição de destino no formato ChessPosition.
     * @return A peça capturada, se houver, após o movimento.
     * @throws BoardException Se o movimento deixar o próprio Rei em xeque.
     */
    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        // Converte as posições de origem e destino para o formato Position
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();

        // Valida a posição de origem e de destino
        validateSourcePosition(source);
        validateTargetPosition(source, target);

        // Valida a posição de destino e realiza o movimento no tabuleiro
        Piece capturedPiece = makeMove(source, target);

        // Verifica se o movimento deixou o próprio Rei em xeque
        if (testCheck(currentPlayer)) {

            undoMove(source, target, capturedPiece); // Desfaz o movimento e lança uma exceção
            throw new BoardException("Não é permitido colocar o próprio Rei em xeque");
        }

        // Atualiza a flag 'check' indicando se o oponente está em xeque
        check = testCheck(opponent(currentPlayer));

        // Avança para o próximo turno
        nextTurn();

        // Retorna a peça capturada, se houver, após o movimento
        return (ChessPiece) capturedPiece;
    }


    /**
     * Desfaz um movimento de xadrez, restaurando o estado anterior ao movimento.
     * <p>
     * Este método desfaz um movimento anterior da posição de destino para a posição de origem no tabuleiro de xadrez,
     * restaurando quaisquer peças capturadas durante o movimento. Se uma peça foi capturada durante o movimento original,
     * ela será recolocada no tabuleiro na posição de destino.
     *
     * @param source        A posição original de origem do movimento.
     * @param target        A posição original de destino do movimento.
     * @param capturedPiece A peça capturada durante o movimento original, se houver.
     */
    private void undoMove(Position source, Position target, Piece capturedPiece) {
        ChessPiece movedPiece = (ChessPiece) board.removePiece(target);
        board.placePiece(movedPiece, source);

        if (capturedPiece != null) {
            board.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        }
    }

    /**
     * Valida a posição de origem para realizar um movimento de xadrez.
     * <p>Este método verifica se há uma peça na posição de origem, se a peça escolhida possui
     * movimentos possíveis e se a peça pertence ao jogador atual.
     * <p>Caso contrário, lança uma exceção apropriada.
     *
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

        if (capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }

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
     * Obtém a cor oponente em relação à cor fornecida.
     * <p>
     * Este método retorna a cor oposta à cor fornecida. Se a cor fornecida for BRANCA, retorna PRETA;
     * se a cor fornecida for PRETA, retorna BRANCA.
     *
     * @param color A cor para a qual deseja-se encontrar o oponente.
     * @return A cor oponente em relação à cor fornecida.
     */
    private Color opponent(Color color) {
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }


    /**
     * Obtém a peça do Rei da cor especificada no tabuleiro.
     * <p>
     * Este método percorre todas as peças no tabuleiro e retorna a instância da peça do Rei
     * associada à cor fornecida. Se não houver um Rei da cor especificada no tabuleiro, uma exceção
     * do tipo IllegalStateException será lançada.
     *
     * @param color A cor do Rei desejado (BRANCA ou PRETA).
     * @return A instância da peça do Rei associada à cor fornecida.
     * @throws IllegalStateException Se não houver um Rei da cor especificada no tabuleiro.
     */
    private ChessPiece king(Color color) {
        List<Piece> listaDePecas = piecesOnTheBoard.stream()
                .filter(x -> ((ChessPiece) x).getColor() == color)
                .toList();

        for (Piece peca : listaDePecas) {
            if (peca instanceof King) {
                return (ChessPiece) peca;
            }
        }

        throw new IllegalStateException("Não há um rei " + color + " no tabuleiro");
    }

    /**
     * Verifica se o Rei da cor especificada está em xeque.
     * <p>
     * Este método avalia se o Rei da cor fornecida está em uma posição vulnerável,
     * ou seja, se ele está sob ameaça de captura pelo oponente. O método verifica
     * todas as peças oponentes no tabuleiro e avalia se alguma delas pode se mover
     * para a posição atual do Rei. Se alguma peça do oponente puder alcançar a posição
     * do Rei, o método retorna verdadeiro indicando que o Rei está em xeque; caso contrário,
     * retorna falso indicando que o Rei está seguro.
     *
     * @param color A cor do Rei a ser verificado quanto à condição de xeque (BRANCA ou PRETA).
     * @return True se o Rei estiver em xeque, False caso contrário.
     */
    private boolean testCheck(Color color) {
        Position kingPosition = king(color).getChessPosition().toPosition();
        List<Piece> opponentPieces = piecesOnTheBoard.stream()
                .filter(x -> ((ChessPiece) x).getColor() == opponent(color))
                .toList();

        for (Piece p : opponentPieces) {
            boolean[][] mat = p.possibleMoves();
            if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
                return true;
            }
        }
        return false;
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
        piecesOnTheBoard.add(piece);
    }

    private void initialSetup() {
        placeNewPiece('h', 7, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE));

        placeNewPiece('b', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 8, new King(board, Color.BLACK));
    }
}

// Peças pretas
//        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
//        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
//        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
//        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
//        placeNewPiece('e', 8, new King(board, Color.BLACK));
//        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
//        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
//        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
////        for (char col = 'a'; col <= 'h'; col++)
////            placeNewPiece(col, 7, new Pawn(board, Color.BLACK));
//
//        // Peças brancas
//        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
//        placeNewPiece('b', 1, new Knight(board, Color.WHITE));
//        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
//        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
//        placeNewPiece('e', 1, new King(board, Color.WHITE));
//        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
//        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
//        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
////        for (char col = 'a'; col <= 'h'; col++)
////            placeNewPiece(col, 2, new Pawn(board, Color.WHITE));