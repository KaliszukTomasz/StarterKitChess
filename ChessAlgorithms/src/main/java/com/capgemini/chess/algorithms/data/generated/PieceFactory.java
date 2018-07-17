package com.capgemini.chess.algorithms.data.generated;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.PieceType;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

public class PieceFactory {
	
	public PieceForm getPieceForm(Coordinate cord, Board board) throws InvalidMoveException {
		if (board.getPieceAt(cord).getType() == PieceType.KNIGHT) {
			PieceForm pieceFormOnThisPlace = new Knight();
			return pieceFormOnThisPlace;
		} else if (board.getPieceAt(cord).getType() == PieceType.BISHOP) {
			PieceForm pieceFormOnThisPlace = new Bishop();
			return pieceFormOnThisPlace;

		} else if (board.getPieceAt(cord).getType() == PieceType.ROOK) {
			PieceForm pieceFormOnThisPlace = new Rook();
			return pieceFormOnThisPlace;

		} else if (board.getPieceAt(cord).getType() == PieceType.QUEEN) {
			PieceForm pieceFormOnThisPlace = new Queen();
			return pieceFormOnThisPlace;

		} else if (board.getPieceAt(cord).getType() == PieceType.KING) {
			PieceForm pieceFormOnThisPlace = new King();
			return pieceFormOnThisPlace;

		} else if (board.getPieceAt(cord).getType() == PieceType.PAWN) {
			if (board.getPieceAt(cord).getColor() == Color.BLACK) {
				PieceForm pieceFormOnThisPlace = new Pawn();
				return pieceFormOnThisPlace;

			} else {// (colorFrom == Color.WHITE)
				PieceForm pieceFormOnThisPlace = new Pawn();
				return pieceFormOnThisPlace;

			}

		}
		return null;
	}
}
