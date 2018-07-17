package com.capgemini.chess.algorithms.data.generated;

import java.util.ArrayList;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.data.enums.Piece;
import com.capgemini.chess.algorithms.data.enums.PieceType;
import com.capgemini.chess.algorithms.implementation.BoardManager;
import com.capgemini.chess.algorithms.implementation.exceptions.NoRookInTheCornerInCastlingException;
import com.capgemini.chess.algorithms.implementation.exceptions.OtherPieceOnRoadFromToException;

public class King implements PieceForm {

	@Override
	public boolean validMove(Move move) {
		int fromX = move.getFrom().getX();
		int toX = move.getTo().getX();
		int fromY = move.getFrom().getY();
		int toY = move.getTo().getY();

		if (MoveType.CASTLING == move.getType()) {
			return (Math.abs(fromX - toX) == 2 && Math.abs(fromY - toY) == 0);// w
																				// celu
																				// potwierdzenia
		}
		return (Math.abs(fromX - toX) <= 1 && Math.abs(fromY - toY) <= 1);
	}

	@Override
	public boolean checkRoadFromTo(Move move, Board board) throws OtherPieceOnRoadFromToException, NoRookInTheCornerInCastlingException {
		if (MoveType.CASTLING == move.getType()) {
			int directionX = (move.getTo().getX() - move.getFrom().getX()) / 2;
			if(directionX > 0){
				if(board.getPieceAt(new Coordinate(7, move.getFrom().getY()))==null?true:PieceType.ROOK !=board.getPieceAt(new Coordinate(7, move.getFrom().getY())).getType() ){
					throw new NoRookInTheCornerInCastlingException();
				}
			}
			else{
				if(board.getPieceAt(new Coordinate(0, move.getFrom().getY()))==null?true:PieceType.ROOK != board.getPieceAt(new Coordinate(0, move.getFrom().getY())).getType() ){
					throw new NoRookInTheCornerInCastlingException();
				}
			}
			if (board.getPieceAt(new Coordinate(move.getFrom().getX() + directionX, move.getFrom().getY())) == null) {
				return true;
			}
			return false;
		} else {
			return true;
		}
	}

	@Override
	public ArrayList<Coordinate> giveArrayToCheckIfAnyMoveValid(Coordinate cord) {
		int fromX = cord.getX();
		int fromY = cord.getY();
		ArrayList<Coordinate> arrayList = new ArrayList<>();
		arrayList.add(new Coordinate(fromX + 1, fromY + 1));
		arrayList.add(new Coordinate(fromX - 1, fromY + 1));
		arrayList.add(new Coordinate(fromX - 1, fromY - 1));
		arrayList.add(new Coordinate(fromX + 1, fromY - 1));
		arrayList.add(new Coordinate(fromX, fromY - 1));
		arrayList.add(new Coordinate(fromX + 1, fromY));
		arrayList.add(new Coordinate(fromX, fromY + 1));
		arrayList.add(new Coordinate(fromX - 1, fromY));

		return arrayList;
	}

}
