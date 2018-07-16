package com.capgemini.chess.algorithms.data.generated;

import java.util.List;
import java.util.function.Predicate;

import org.junit.runner.FilterFactoryParams;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.*;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

public class MovesPathValidator {
	// fabryka zawiera jedną metodę, która w zaleznosci od emuma zwraca nam
	// implementacje....
	private Board board;
	private Coordinate cordFrom;
	private Coordinate cordTo;
	private PieceType type;
	private Color colorFrom;
	private PieceForm pieceFormOnThisPlace;
	private Move move;

	public MovesPathValidator(Board board, Move move) {
		this.cordFrom = move.getFrom();
		this.cordTo = move.getTo();
		this.board = board;
		this.type = board.getPieceAt(cordFrom).getType();
		this.move = move;
	}

	// @SuppressWarnings("unchecked")
	// public ArrayList<Coordinate>
	// filterPathsInBoardDimension(ArrayList<Coordinate> list) {
	// return (ArrayList<Coordinate>) list.stream().filter(dimensionFilter);
	// }

	public PieceForm pieceFormFabric() throws InvalidMoveException {
		if (this.type == PieceType.KNIGHT) {
			this.pieceFormOnThisPlace = new Knight();
			return this.pieceFormOnThisPlace;
		} else if (this.type == PieceType.BISHOP) {
			this.pieceFormOnThisPlace = new Bishop();
			return this.pieceFormOnThisPlace;

		} else if (this.type == PieceType.ROOK) {
			this.pieceFormOnThisPlace = new Rook();
			return this.pieceFormOnThisPlace;

		} else if (this.type == PieceType.QUEEN) {
			this.pieceFormOnThisPlace = new Queen();
			return this.pieceFormOnThisPlace;

		} else if (this.type == PieceType.KING) {
			this.pieceFormOnThisPlace = new King();
			return this.pieceFormOnThisPlace;

		} else if (this.type == PieceType.PAWN) {
			if (colorFrom == Color.BLACK) {
				this.pieceFormOnThisPlace = new Pawn();
				return this.pieceFormOnThisPlace;

			} else {// (colorFrom == Color.WHITE)
				this.pieceFormOnThisPlace = new Pawn();
				return this.pieceFormOnThisPlace;

			}

		}
		return null;
	}
}
