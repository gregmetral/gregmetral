package piecetype;

import java.awt.Color;

import tetris.Pieces;

public class I extends Pieces{

	public I() {
		super(new int[][] {{1,1,1,1}}, Color.CYAN);
	}
	
	
	@Override
	public void rotatePiece() {
		super.rotatePiece();
		
		if (this.length() == 1 ) {
			this.setX(this.getX() + 1);
			this.setY(this.getY() - 1);
		} else {
			this.setX(this.getX() - 1);
			this.setY(this.getY() + 1);
		}
	}
}
