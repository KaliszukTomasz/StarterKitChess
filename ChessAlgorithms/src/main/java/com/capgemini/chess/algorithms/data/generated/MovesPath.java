package com.capgemini.chess.algorithms.data.generated;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.runner.FilterFactoryParams;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.enums.*;

public class MovesPath {

	private PieceType type;
	private Coordinate cord;
	// private PieceType[][] pathsOnBoard;
	private Predicate<Coordinate> dimensionFilter = (
			cord) -> (cord.getX() < 8 && cord.getX() >= 0 && cord.getY() < 8 && cord.getY() >= 0);

	// private ArrayList<Coordinate> listOfPossibleMoves;
	// private PieceType tempPiece;

	public MovesPath(PieceType type, Coordinate cord) {
		this.type = type;
		this.cord = cord;
		// this.pathsOnBoard = new PieceType[8][8];
		// this.listOfPossibleMoves = new ArrayList<Coordinate>();

	}

	@SuppressWarnings("unchecked")
	public ArrayList<Coordinate> filterPathsInBoardDimension(ArrayList<Coordinate> list) {
		return (ArrayList<Coordinate>) list.stream().filter(dimensionFilter);
	}

	public ArrayList<Coordinate> calculatePath() {
		ArrayList<Coordinate> listOfPossibleMoves = new ArrayList<>();

		if (this.type == PieceType.KNIGHT) {
			listOfPossibleMoves.add(new Coordinate(cord.getX() + 2, cord.getY() + 1));
			listOfPossibleMoves.add(new Coordinate(cord.getX() + 2, cord.getY() - 1));
			listOfPossibleMoves.add(new Coordinate(cord.getX() - 2, cord.getY() + 1));
			listOfPossibleMoves.add(new Coordinate(cord.getX() - 2, cord.getY() - 1));
			listOfPossibleMoves.add(new Coordinate(cord.getX() + 1, cord.getY() + 2));
			listOfPossibleMoves.add(new Coordinate(cord.getX() + 1, cord.getY() - 2));
			listOfPossibleMoves.add(new Coordinate(cord.getX() - 1, cord.getY() + 2));
			listOfPossibleMoves.add(new Coordinate(cord.getX() - 1, cord.getY() - 2));
		} else if (this.type == PieceType.BISHOP) {

		} else if (this.type == PieceType.ROOK) {

		} else if (this.type == PieceType.QUEEN) {

		} else if (this.type == PieceType.KING) {

		} else if (this.type == PieceType.PAWN){
			
		}

		return listOfPossibleMoves;
	}
	// for(Coordinate cord : listOfPossibleMoves){
	// if(cord.getX()<0 || cord.getX()>7 ||cord.getY()<0||cord.getY()>7){
	// listOfPossibleMoves.remove(cord);
	// }
	// }
	// listOfPossibleMoves.removeIf(cord -> cord.getX()<0 || cord.getX>7);
	// || cord.getX()>7 ||cord.getY()<0||cord.getY()>7);

	// if (cord.getX() > 1 && cord.getX() < 6 && cord.getY() > 1 && cord.getY()
	// < 6) {
	// this.pathsOnBoard[cord.getX() + 2][cord.getY() + 1] = PieceType.KNIGHT;
	// this.pathsOnBoard[cord.getX() + 2][cord.getY() - 1] = PieceType.KNIGHT;
	// this.pathsOnBoard[cord.getX() - 2][cord.getY() + 1] = PieceType.KNIGHT;
	// this.pathsOnBoard[cord.getX() - 2][cord.getY() - 1] = PieceType.KNIGHT;
	//
	// this.pathsOnBoard[cord.getX() + 1][cord.getY() + 2] = PieceType.KNIGHT;
	// this.pathsOnBoard[cord.getX() + 1][cord.getY() - 2] = PieceType.KNIGHT;
	// this.pathsOnBoard[cord.getX() - 1][cord.getY() + 2] = PieceType.KNIGHT;
	// this.pathsOnBoard[cord.getX() - 1][cord.getY() - 2] = PieceType.KNIGHT;
	//
	// }//pełne warunki na zakres ruchów konia;

	// return this.pathsOnBoard;
	//
	// }

	// if()

}
