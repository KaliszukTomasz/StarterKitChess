package com.capgemini.chess.algorithms.implementation;

import java.util.Arrays;
import java.util.List;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.BoardState;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.data.enums.Piece;
import com.capgemini.chess.algorithms.data.enums.PieceType;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.data.generated.MovesPathValidator;
import com.capgemini.chess.algorithms.data.generated.PieceForm;
import com.capgemini.chess.algorithms.implementation.exceptions.CoordinatesNotOnBoardException;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;
import com.capgemini.chess.algorithms.implementation.exceptions.KingInCheckException;
import com.capgemini.chess.algorithms.implementation.exceptions.NoKingOnBoardException;
import com.capgemini.chess.algorithms.implementation.exceptions.NullPieceOnFromPlaceException;
import com.capgemini.chess.algorithms.implementation.exceptions.OtherPieceOnRoadFromToException;
import com.capgemini.chess.algorithms.implementation.exceptions.PlaceToIsUnreachableException;

/**
 * Class for managing of basic operations on the Chess Board.
 *
 * @author Michal Bejm
 *
 */
public class BoardManager {

	private Board board = new Board();

	public BoardManager() {
		initBoard();
	}

	public BoardManager(List<Move> moves) {
		initBoard();
		for (Move move : moves) {
			addMove(move);
		}
	}

	public BoardManager(Board board) {
		this.board = board;
	}

	/**
	 * Getter for generated board
	 *
	 * @return board
	 */
	public Board getBoard() {
		return this.board;
	}

	/**
	 * Performs move of the chess piece on the chess board from one field to
	 * another.
	 *
	 * @param from
	 *            coordinates of 'from' field
	 * @param to
	 *            coordinates of 'to' field
	 * @return move object which includes moved piece and move type
	 * @throws InvalidMoveException
	 *             in case move is not valid
	 */
	public Move performMove(Coordinate from, Coordinate to) throws InvalidMoveException {

		Move move = validateMove(from, to);

		// if(isKingInCheck(board.getPieceAt(from).getColor()))
		// {
		// throw new KingInCheckException();//TODO, czy mogę tutaj wrzucić
		// sprawdzenie?
		// }//TODO do usunięcia!!!
		addMove(move);

		return move;
	}

	/**
	 * Calculates state of the chess board.
	 *
	 * @return state of the chess board
	 * @throws NoKingOnBoardException
	 */
	public BoardState updateBoardState() throws NoKingOnBoardException {

		Color nextMoveColor = calculateNextMoveColor();

		boolean isKingInCheck = isKingInCheck(nextMoveColor);
		boolean isAnyMoveValid = isAnyMoveValid(nextMoveColor);

		BoardState boardState;
		if (isKingInCheck) {
			if (isAnyMoveValid) {
				boardState = BoardState.CHECK;
			} else {
				boardState = BoardState.CHECK_MATE;
			}
		} else {
			if (isAnyMoveValid) {
				boardState = BoardState.REGULAR;
			} else {
				boardState = BoardState.STALE_MATE;
			}
		}
		this.board.setState(boardState);
		return boardState;
	}

	/**
	 * Checks threefold repetition rule (one of the conditions to end the chess
	 * game with a draw).
	 *
	 * @return true if current state repeated at list two times, false otherwise
	 */
	public boolean checkThreefoldRepetitionRule() {// TODO zrozum dokładnie co
													// sprawdza ta funkcja

		// there is no need to check moves that where before last capture/en
		// passant/castling
		int lastNonAttackMoveIndex = findLastNonAttackMoveIndex();
		List<Move> omittedMoves = this.board.getMoveHistory().subList(0, lastNonAttackMoveIndex);
		BoardManager simulatedBoardManager = new BoardManager(omittedMoves);

		int counter = 0;
		for (int i = lastNonAttackMoveIndex; i < this.board.getMoveHistory().size(); i++) {
			Move moveToAdd = this.board.getMoveHistory().get(i);
			simulatedBoardManager.addMove(moveToAdd);
			boolean areBoardsEqual = Arrays.deepEquals(this.board.getPieces(),
					simulatedBoardManager.getBoard().getPieces());
			if (areBoardsEqual) {
				counter++;
			}
		}

		return counter >= 2;
	}

	/**
	 * Checks 50-move rule (one of the conditions to end the chess game with a
	 * draw).
	 *
	 * @return true if no pawn was moved or not capture was performed during
	 *         last 50 moves, false otherwise
	 */
	public boolean checkFiftyMoveRule() {

		// for this purpose a "move" consists of a player completing his turn
		// followed by his opponent completing his turn
		if (this.board.getMoveHistory().size() < 100) {
			return false;
		}

		for (int i = this.board.getMoveHistory().size() - 1; i >= this.board.getMoveHistory().size() - 100; i--) {
			Move currentMove = this.board.getMoveHistory().get(i);
			PieceType currentPieceType = currentMove.getMovedPiece().getType();
			if (currentMove.getType() != MoveType.ATTACK || currentPieceType == PieceType.PAWN) {
				return false;
			}
		}

		return true;
	}

	// PRIVATE

	private void initBoard() {

		this.board.setPieceAt(Piece.BLACK_ROOK, new Coordinate(0, 7));
		this.board.setPieceAt(Piece.BLACK_KNIGHT, new Coordinate(1, 7));
		this.board.setPieceAt(Piece.BLACK_BISHOP, new Coordinate(2, 7));
		this.board.setPieceAt(Piece.BLACK_QUEEN, new Coordinate(3, 7));
		this.board.setPieceAt(Piece.BLACK_KING, new Coordinate(4, 7));
		this.board.setPieceAt(Piece.BLACK_BISHOP, new Coordinate(5, 7));
		this.board.setPieceAt(Piece.BLACK_KNIGHT, new Coordinate(6, 7));
		this.board.setPieceAt(Piece.BLACK_ROOK, new Coordinate(7, 7));

		for (int x = 0; x < Board.SIZE; x++) {
			this.board.setPieceAt(Piece.BLACK_PAWN, new Coordinate(x, 6));
		}

		this.board.setPieceAt(Piece.WHITE_ROOK, new Coordinate(0, 0));
		this.board.setPieceAt(Piece.WHITE_KNIGHT, new Coordinate(1, 0));
		this.board.setPieceAt(Piece.WHITE_BISHOP, new Coordinate(2, 0));
		this.board.setPieceAt(Piece.WHITE_QUEEN, new Coordinate(3, 0));
		this.board.setPieceAt(Piece.WHITE_KING, new Coordinate(4, 0));
		this.board.setPieceAt(Piece.WHITE_BISHOP, new Coordinate(5, 0));
		this.board.setPieceAt(Piece.WHITE_KNIGHT, new Coordinate(6, 0));
		this.board.setPieceAt(Piece.WHITE_ROOK, new Coordinate(7, 0));

		for (int x = 0; x < Board.SIZE; x++) {
			this.board.setPieceAt(Piece.WHITE_PAWN, new Coordinate(x, 1));
		}
	}

	private void addMove(Move move) {

		addRegularMove(move);

		if (move.getType() == MoveType.CASTLING) {
			addCastling(move);
		} else if (move.getType() == MoveType.EN_PASSANT) {
			addEnPassant(move);
		}

		this.board.getMoveHistory().add(move);
	}

	private void addRegularMove(Move move) {
		Piece movedPiece = this.board.getPieceAt(move.getFrom());
		this.board.setPieceAt(null, move.getFrom());
		this.board.setPieceAt(movedPiece, move.getTo());

		performPromotion(move, movedPiece);
	}

	private void performPromotion(Move move, Piece movedPiece) {
		if (movedPiece == Piece.WHITE_PAWN && move.getTo().getY() == (Board.SIZE - 1)) {
			this.board.setPieceAt(Piece.WHITE_QUEEN, move.getTo());
		}
		if (movedPiece == Piece.BLACK_PAWN && move.getTo().getY() == 0) {
			this.board.setPieceAt(Piece.BLACK_QUEEN, move.getTo());
		}
	}

	private void addCastling(Move move) {
		if (move.getFrom().getX() > move.getTo().getX()) {
			Piece rook = this.board.getPieceAt(new Coordinate(0, move.getFrom().getY()));
			this.board.setPieceAt(null, new Coordinate(0, move.getFrom().getY()));
			this.board.setPieceAt(rook, new Coordinate(move.getTo().getX() + 1, move.getTo().getY()));
		} else {
			Piece rook = this.board.getPieceAt(new Coordinate(Board.SIZE - 1, move.getFrom().getY()));
			this.board.setPieceAt(null, new Coordinate(Board.SIZE - 1, move.getFrom().getY()));
			this.board.setPieceAt(rook, new Coordinate(move.getTo().getX() - 1, move.getTo().getY()));
		}
	}

	private void addEnPassant(Move move) {
		Move lastMove = this.board.getMoveHistory().get(this.board.getMoveHistory().size() - 1);
		this.board.setPieceAt(null, lastMove.getTo());
	}

	private Move validateMove(Coordinate from, Coordinate to) throws InvalidMoveException, KingInCheckException {
		
		if (!checkIfCoordsAreOnBoard(from) || !checkIfCoordsAreOnBoard(to)) {
			throw new CoordinatesNotOnBoardException();
		}

		Move myMove = new Move();
		myMove.setFrom(from);
		myMove.setTo(to);
		if (board.getPieceAt(from) == null) {
			throw new NullPieceOnFromPlaceException();
		}

		myMove.setMovedPiece(getBoard().getPieceAt(from));

		Color colorPieceFrom = myMove.getMovedPiece().getColor();
		Color colorPieceTo = board.getPieceAt(to) == null ? null : getBoard().getPieceAt(to).getColor();
		PieceForm pieceForm;
		myMove.setType(checkWhatTypeOfMove(colorPieceFrom, colorPieceTo));
	
		MovesPathValidator actualyPath = new MovesPathValidator(getBoard(), myMove);
		pieceForm = actualyPath.pieceFormFabric();
		if (pieceForm.validMove(myMove)) {
			if (pieceForm.checkRoadFromTo(myMove, getBoard())) {
				boolean kingInCheck = willBeKingInCheckAfterThisMove(myMove);
				if (kingInCheck) {
					throw new KingInCheckException();
				} else {
					return myMove;
				}
			} else {
				throw new OtherPieceOnRoadFromToException();
			}

		} else {
			throw new PlaceToIsUnreachableException();
		}

	}

	private boolean checkIfCoordsAreOnBoard(Coordinate cord) {

		if (cord.getX() > 7 || cord.getY() > 7 || cord.getX() < 0 || cord.getY() < 0) {
			return false;
		} else {
			return true;
		}
	}

	private MoveType checkWhatTypeOfMove(Color fromColor, Color toColor) throws InvalidMoveException {
		if (fromColor == null) {
			throw new InvalidMoveException();
		}
		if (toColor == null) {
			return MoveType.ATTACK;
		} else {
			if (fromColor == toColor) {
				throw new InvalidMoveException("This is a Piece in the same color!!!");
			} else {
				return MoveType.CAPTURE;
			}
		}
	}

	private boolean willBeKingInCheckAfterThisMove(Move move) throws NoKingOnBoardException {
		Color pieceColor = board.getPieceAt(move.getFrom()).getColor();
		Coordinate cordOfTheKing = checkWhereIsMyKing(pieceColor);
		Piece tempPieceTo = board.getPieceAt(move.getTo());
		Piece tempPieceFrom = board.getPieceAt(move.getFrom());
		board.setPieceAt(tempPieceFrom, move.getTo());
		board.setPieceAt(null, move.getFrom());

		for (int x = 0; x < Board.SIZE; x++) {
			for (int y = 0; y < Board.SIZE; y++) {
				Coordinate cord = new Coordinate(x, y);
				if (board.getPieceAt(cord) != null && board.getPieceAt(cord).getColor() != pieceColor) {
					try {
						MoveType tempMoveType = this.validateMove(cord, cordOfTheKing).getType();
						if (tempMoveType == MoveType.CAPTURE) {
							board.setPieceAt(tempPieceFrom, move.getFrom());
							board.setPieceAt(tempPieceTo, move.getTo());
							return true;
						}
					} catch (InvalidMoveException e) {
					}
				}
			}
		}

		return false;

	}

	private boolean isKingInCheck(Color kingColor) throws NoKingOnBoardException {

		Coordinate cordOfTheKing = checkWhereIsMyKing(kingColor);

		for (int x = 0; x < Board.SIZE; x++) {
			for (int y = 0; y < Board.SIZE; y++) {
				Coordinate cord = new Coordinate(x, y);
				if (this.board.getPieceAt(cord) != null && this.board.getPieceAt(cord).getColor() != kingColor) {
					try {
						if (validateMove(cord, cordOfTheKing).getType() == MoveType.CAPTURE) {
							return true;
						}
					} catch (InvalidMoveException e) {

					}
				}
			}
		}
		return false;

	}

	private Coordinate checkWhereIsMyKing(Color kingColor) throws NoKingOnBoardException {

		for (int x = 0; x < Board.SIZE; x++) {
			for (int y = 0; y < Board.SIZE; y++) {
				Coordinate cord = new Coordinate(x, y);
				if (this.board.getPieceAt(cord) != null && this.board.getPieceAt(cord).getType() == PieceType.KING
						&& this.board.getPieceAt(cord).getColor() == kingColor) {
					return cord;
				}
			}
		}
		throw new NoKingOnBoardException();
	}

	private boolean isAnyMoveValid(Color nextMoveColor) {

		// TODO please add implementation here

		return false;
	}

	private Color calculateNextMoveColor() {
		if (this.board.getMoveHistory().size() % 2 == 0) {
			return Color.WHITE;
		} else {
			return Color.BLACK;
		}
	}

	private int findLastNonAttackMoveIndex() {
		int counter = 0;
		int lastNonAttackMoveIndex = 0;

		for (Move move : this.board.getMoveHistory()) {
			if (move.getType() != MoveType.ATTACK) {
				lastNonAttackMoveIndex = counter;
			}
			counter++;
		}

		return lastNonAttackMoveIndex;
	}

}
