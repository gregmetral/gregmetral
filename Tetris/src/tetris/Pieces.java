package tetris;

import java.awt.Color;

public class Pieces {
	
	private int[][] shape;
	private int[][][] shapes;
	private int currentShape;
	private Color color;
	private int x,y;
	
	public Pieces(int[][] shape, Color color) {
		
		this.shape = shape;
		this.color = color;
		shapes();

	}
	
	public void spawn() {
		currentShape = 3;
		 y = -1;
		 x = 4;
		 shape = shapes[currentShape];
	}
	
	public void shapes() {
		shapes = new int[4][][];
		
		for (int i = 0; i<4; i++) {
			int r = shape[0].length;
			int c = shape.length;
			shapes[i] = new int[r][c];
			
			for (int y= 0; y<r; y++) {
				for (int x = 0; x<c; x++) {
					shapes[i][y][x] = shape[c-x-1][y];
				}
			}
			shape = shapes[i];
		}
	}
	
	
	
	public void rotatePiece() {
		currentShape += 1;
		if (currentShape>3) {
			currentShape = 0;
		}
		shape = shapes[currentShape];
		
	}
	
	public void fallPiece() {
		this.y += 1;
	}
	
	public void goLeftPiece() {
		this.x -= 1;
	}
	
	public void goRightPiece() {
		this.x += 1;
	}
	
	public int height(){
		return this.shape.length;
	}
	
	public int length() {
		return this.shape[0].length;
	}
	
	public int[][] getShape(){
		return this.shape;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y ;
	}
	
	public int getLeftEdge() {
		return x;
	}
	
	public int getRightEdge() {
		return x + length();
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getCurrentShape() {
		return currentShape;
	}
	
	public void setCurrentShape(int x) {
		this.currentShape = x;
	}
	
	
}
