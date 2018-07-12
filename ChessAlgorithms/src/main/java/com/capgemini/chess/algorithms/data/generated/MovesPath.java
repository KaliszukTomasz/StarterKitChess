package com.capgemini.chess.algorithms.data.generated;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.enums.*;

public class MovesPath {

	private PieceType type;
	private Coordinate cord;
	private PieceType[][] pathsOnBoard;
	// private PieceType tempPiece;

	public MovesPath(PieceType type, Coordinate cord) {
		this.type = type;
		this.cord = cord;
		this.pathsOnBoard = new PieceType[8][8];

	}

	public PieceType[][] calculatePath() {

		if (this.type == PieceType.KNIGHT) {
			if (cord.getX() > 1 && cord.getX() < 6 && cord.getY() > 1 && cord.getY() < 6) {
				this.pathsOnBoard[cord.getX() + 2][cord.getY() + 1] = PieceType.KNIGHT;
				this.pathsOnBoard[cord.getX() + 2][cord.getY() - 1] = PieceType.KNIGHT;
				this.pathsOnBoard[cord.getX() - 2][cord.getY() + 1] = PieceType.KNIGHT;
				this.pathsOnBoard[cord.getX() - 2][cord.getY() - 1] = PieceType.KNIGHT;

				this.pathsOnBoard[cord.getX() + 1][cord.getY() + 2] = PieceType.KNIGHT;
				this.pathsOnBoard[cord.getX() + 1][cord.getY() - 2] = PieceType.KNIGHT;
				this.pathsOnBoard[cord.getX() - 1][cord.getY() + 2] = PieceType.KNIGHT;
				this.pathsOnBoard[cord.getX() - 1][cord.getY() - 2] = PieceType.KNIGHT;

			}//pełne warunki na zakres ruchów konia;
			
		}

		return this.pathsOnBoard;

	}

	// if()

}
