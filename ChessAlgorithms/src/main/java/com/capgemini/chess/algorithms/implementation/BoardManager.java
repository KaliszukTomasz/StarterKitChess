package com.capgemini.chess.algorithms.implementation;

import java.util.ArrayList;
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
import com.capgemini.chess.algorithms.data.generated.PieceFactory;
import com.capgemini.chess.algorithms.data.generated.PieceForm;
import com.capgemini.chess.algorithms.implementation.exceptions.CoordinatesNotOnBoardException;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidColorMovedPiece;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;
import com.capgemini.chess.algorithms.implementation.exceptions.KingInCheckException;
import com.capgemini.chess.algorithms.implementation.exceptions.KingMovedAlreadyAndCanstlingIsImpossible;
import com.capgemini.chess.algorithms.implementation.exceptions.KingWillBeBeatenDuringCastling;
import com.capgemini.chess.algorithms.implementation.exceptions.NoKingOnBoardException;
import com.capgemini.chess.algorithms.implementation.exceptions.NullPieceOnFromPlaceException;
import com.capgemini.chess.algorithms.implementation.exceptions.OtherPieceOnRoadFromToException;
import com.capgemini.chess.algorithms.implementation.exceptions.PlaceToIsUnreachableException;
import com.capgemini.chess.algorithms.implementation.exceptions.TheSameColorFromToPlacesException;

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
	 * @throws InvalidColorMovedPiece
	 */
	public Move performMove(Coordinate from, Coordinate to) throws InvalidMoveException, InvalidColorMovedPiece {

		Move move = validateMove(from, to);

		addMove(move);

		return move;
	}

	/**
	 * Calculates state of the chess board.
	 *
	 * @return state of the chess board
	 * @throws InvalidMoveException
	 */
	public BoardState updateBoardState() throws InvalidMoveException {

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
	public boolean checkThreefoldRepetitionRule() {

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

	private Move validateMove(Coordinate from, Coordinate to)
			throws InvalidMoveException, KingInCheckException, InvalidColorMovedPiece {

		checkIfCoordsAreOnBoard(from);
		checkIfCoordsAreOnBoard(to);
		checkIfPlaceFromIsNotEmpty(from, board);

		Move myMove = new Move();
		myMove.setFrom(from);
		myMove.setTo(to);
		myMove.setMovedPiece(getBoard().getPieceAt(from));
		checkItsGoodPieceColorToMove(from, board);
		myMove.setType(checkWhatTypeOfMove(myMove, board));
		PieceFactory pieceFactory = new PieceFactory();
		PieceForm pieceForm = pieceFactory.getPieceForm(myMove.getFrom(), board);
		if (pieceForm.validMove(myMove)) {
			if (pieceForm.checkRoadFromTo(myMove, getBoard())) {
				if (checkIfCastlingMoveIsCorrect(myMove, board)) {
					return myMove;
				} else {
					this.board.getMoveHistory().add(myMove);
					boolean kingInCheck = willBeKingInCheckAfterThisMove(myMove);
					this.board.getMoveHistory().remove(this.board.getMoveHistory().size() - 1);
					if (kingInCheck) {
						throw new KingInCheckException();
					} else {
						return myMove;
					}
				}
			} else {
				throw new OtherPieceOnRoadFromToException();
			}
		} else {
			throw new PlaceToIsUnreachableException();
		}
	}

	private void checkItsGoodPieceColorToMove(Coordinate from, Board board) throws InvalidColorMovedPiece {
		if (calculateNextMoveColor() != board.getPieceAt(from).getColor()) {
			throw new InvalidColorMovedPiece();
		}
	}

	private void checkIfPlaceFromIsNotEmpty(Coordinate from, Board board) throws NullPieceOnFromPlaceException {
		if (board.getPieceAt(from) == null) {
			throw new NullPieceOnFromPlaceException();
		}
	}

	private boolean checkIfCastlingMoveIsCorrect(Move myMove, Board board)
			throws KingWillBeBeatenDuringCastling, NoKingOnBoardException, InvalidColorMovedPiece {

		if (myMove.getType() == MoveType.CASTLING) {
			int directionX = (myMove.getTo().getX() - myMove.getFrom().getX()) / 2;
			myMove.setTo(new Coordinate(myMove.getFrom().getX() + directionX, myMove.getFrom().getY()));
			this.board.getMoveHistory().add(myMove);
			boolean kingInCheck = willBeKingInCheckAfterThisMove(myMove);
			this.board.getMoveHistory().remove(this.board.getMoveHistory().size() - 1);
			if (kingInCheck) {
				throw new KingWillBeBeatenDuringCastling();
			} else {
				myMove.setTo(new Coordinate(myMove.getFrom().getX() + 2 * directionX, myMove.getFrom().getY()));
				this.board.getMoveHistory().add(myMove);
				kingInCheck = willBeKingInCheckAfterThisMove(myMove);
				this.board.getMoveHistory().remove(this.board.getMoveHistory().size() - 1);
				if (kingInCheck) {
					throw new KingWillBeBeatenDuringCastling();
				} else {
					return true;
				}
			}
		}
		return false;
	}

	private void checkIfCoordsAreOnBoard(Coordinate cord) throws CoordinatesNotOnBoardException {
		if (cord.getX() > 7 || cord.getY() > 7 || cord.getX() < 0 || cord.getY() < 0) {
			throw new CoordinatesNotOnBoardException();
		}
	}

	private MoveType checkWhatTypeOfMove(Move move, Board board) throws InvalidMoveException {

		Color fromColor = board.getPieceAt(move.getFrom()).getColor();
		Color toColor = board.getPieceAt(move.getTo()) == null ? null : board.getPieceAt(move.getTo()).getColor();
		if (fromColor == null) {
			throw new InvalidMoveException();
		}
		if (checkIfThatMoveTypeIsEnPassant(move, board)) {
			return MoveType.EN_PASSANT;
		}
		if (checkIfThatMoveTypeIsCastling(move, board)) {
			return MoveType.CASTLING;
		}
		if (toColor == null) {
			return MoveType.ATTACK;
		} else {
			if (fromColor == toColor) {
				throw new TheSameColorFromToPlacesException();
			} else {
				return MoveType.CAPTURE;
			}
		}
	}

	private boolean checkIfThatMoveTypeIsEnPassant(Move move, Board board) {
		if (board.getMoveHistory() != null && board.getMoveHistory().size() > 0) {
			if (board.getMoveHistory().get(board.getMoveHistory().size() - 1) != null) {
				Move lastMoveInHistory = board.getMoveHistory().get(board.getMoveHistory().size() - 1);
				int lastMoveToX = lastMoveInHistory.getTo().getX();
				int lastMoveFromY = lastMoveInHistory.getFrom().getY();
				int lastMoveToY = lastMoveInHistory.getTo().getY();
				boolean enPassantCondition1 = PieceType.PAWN.equals(lastMoveInHistory.getMovedPiece().getType());
				boolean enPassantCondition2 = Math.abs(lastMoveFromY - lastMoveToY) == 2;
				boolean enPassantCondition3 = move.getFrom().getY() == lastMoveToY;
				boolean enPassantCondition4 = move.getTo().getX() == lastMoveToX;
				boolean enPassantCondition5 = move.getTo().getY() == lastMoveFromY + (lastMoveToY - lastMoveFromY) / 2;
				if (enPassantCondition1 && enPassantCondition2 && enPassantCondition3 && enPassantCondition4
						&& enPassantCondition5) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean checkIfThatMoveTypeIsCastling(Move move, Board board)
			throws KingMovedAlreadyAndCanstlingIsImpossible {

		boolean castlingCondition1 = (Math.abs(move.getFrom().getX() - move.getTo().getX()) == 2
				&& (move.getFrom().getY() - move.getTo().getY() == 0));
		boolean castlingCondition2 = PieceType.KING == board.getPieceAt(move.getFrom()).getType()
				&& board.getPieceAt(move.getTo()) == null;
		if (castlingCondition1 && castlingCondition2) {
			for (Move historyMove : this.board.getMoveHistory()) {
				if (PieceType.KING == historyMove.getMovedPiece().getType()) {
					throw new KingMovedAlreadyAndCanstlingIsImpossible();
				}
			}
			return true;
		} else {
			return false;
		}
	}

	private boolean willBeKingInCheckAfterThisMove(Move move) throws NoKingOnBoardException, InvalidColorMovedPiece {
		Color pieceColor = board.getPieceAt(move.getFrom()).getColor();
		Piece tempPieceTo = board.getPieceAt(move.getTo());
		Piece tempPieceFrom = board.getPieceAt(move.getFrom());
		board.setPieceAt(tempPieceFrom, move.getTo());
		board.setPieceAt(null, move.getFrom());
		Coordinate cordOfTheKing = checkWhereIsMyKing(pieceColor);
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
		board.setPieceAt(tempPieceFrom, move.getFrom());
		board.setPieceAt(tempPieceTo, move.getTo());
		return false;
	}

	private boolean isKingInCheck(Color kingColor) throws NoKingOnBoardException, InvalidColorMovedPiece {

		Coordinate cordOfTheKing = checkWhereIsMyKing(kingColor);
		this.board.getMoveHistory().add(null);
		for (int x = 0; x < Board.SIZE; x++) {
			for (int y = 0; y < Board.SIZE; y++) {
				Coordinate cord = new Coordinate(x, y);
				if (this.board.getPieceAt(cord) != null && this.board.getPieceAt(cord).getColor() != kingColor) {
					try {
						if (validateMove(cord, cordOfTheKing).getType() == MoveType.CAPTURE) {
							this.board.getMoveHistory().remove(this.board.getMoveHistory().size() - 1);
							return true;
						}
					} catch (InvalidMoveException e) {
					}
				}
			}
		}
		this.board.getMoveHistory().remove(this.board.getMoveHistory().size() - 1);
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

	private boolean isAnyMoveValid(Color nextMoveColor) throws InvalidMoveException {

		for (int x = 0; x < Board.SIZE; x++) {
			for (int y = 0; y < Board.SIZE; y++) {
				Coordinate cord = new Coordinate(x, y);
				if (this.board.getPieceAt(cord) != null && this.board.getPieceAt(cord).getColor() == nextMoveColor) {
					PieceFactory pieceFactory = new PieceFactory();
					PieceForm pieceForm = pieceFactory.getPieceForm(cord, this.board);
					ArrayList<Coordinate> arrayOfMoves = pieceForm.giveArrayToCheckIfAnyMoveValid(cord);
					for (Coordinate cordToCheck : arrayOfMoves) {
						try {
							if (this.validateMove(cord, cordToCheck) != null) {
								return true;
							}
						} catch (InvalidMoveException e) {
						}
					}
				}
			}
		}
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
