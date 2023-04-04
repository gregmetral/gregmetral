package piecetype;

import java.awt.Color;

import tetris.Pieces;

public class O extends Pieces{
	public O() {
		super(new int[][] {{1,1},{1,1}}, Color.YELLOW);
		
	}
	
	@Override
	public void rotatePiece() {
		return;
	}
}
