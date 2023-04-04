package tetris;
import piecetype.*;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
//the game panel is in the Panel / a part of the Panel


public class GamePanel extends JPanel{
	
	private int rows;
	private int columns;
	private int sizeCell;
	private Pieces piece;
	private Color[][] stoppedPieces;
	private boolean isGameOver;
	private int allClearedLines = 0;
	private int clearedLines;
	private Pieces[] pieces;
	private boolean stopPieceMovement = false;

	
	public GamePanel(){
		
		this.setBounds(100, 100, 450, 900);
		columns = 10;
		sizeCell = this.getBounds().width / columns;
		rows = 20;
		stoppedPieces = new Color[rows][columns];
		pieces = new Pieces[] {new I(), new J(), new L(), new O(), new S(), new T(), new Z()};
	}
	
	public void newPiece() {
		Random r = new Random();
		int selectPiece = r.nextInt(7);
		piece = pieces[selectPiece]; 
		piece.spawn();
		repaint();
	}
	
	public void removeLines(int row) {
		for (int j = 0; j < columns; j++) {
			stoppedPieces[row][j] = null;
			
		}
		
		for (int i = row; i > 0; i--) {
			for (int j = 0; j < columns; j++) {
				stoppedPieces[i][j] = stoppedPieces[i-1][j];
			}
		}
		for (int j = 0; j < columns; j++) {
			stoppedPieces[0][j] = null;
		}
		
	}
	
	public int findFullLines() {
		int clearedLines = 0;
		boolean fullLine;
		for (int i = rows - 1; i >= 0; i--) {
			fullLine = true;
			for (int j = 0; j < columns; j++) {
				if (stoppedPieces[i][j] == null) {
					fullLine = false;
					break;
				}
			}
			if (fullLine) {
				clearedLines++;
				removeLines(i);
				repaint();
				i++;  //i has to be +1 because when we remove a line and the stopped pieces go down, we have to re-check this line
			}
		}
		return clearedLines;
	}
	
	public boolean OOB() {
		if (piece.getY() < 0) {
			repaint();
			isGameOver = true;
			return true;
		}
		return false;
	}
	
	public void setStopPieceMovement(boolean newState) {
		this.stopPieceMovement = newState;
	}
	
	public boolean moveDownPiece() {
		if(checkBottom()) {
			
			piece.fallPiece();
			repaint();   //in paintComponent
			return true;
		
		}else {
			if (OOB()) {
				JOptionPane.showMessageDialog(this, "GAME OVER");
				return false;
			}else {
				atRestPiece();
				return false;
			}

		}
	}
	
	public void moveRightPiece() {
		if (isGameOver  || stopPieceMovement) {
			return;
		}
		if(checkRight()) {
			piece.goRightPiece();
			repaint();
		}
	}
	
	public void moveLeftPiece() {
		if (isGameOver || stopPieceMovement) {
			return;
		}
		if(checkLeft()) {
			piece.goLeftPiece();
			repaint();
		}

	}
	
	public void hardDrop() {
		if (isGameOver || stopPieceMovement) {
			return;
		}
		while(checkBottom()) {
			piece.fallPiece();
			
		}
		repaint();
	}
	
	
	public void rotate() {

		if (isGameOver || stopPieceMovement) {
			return;
		}
		piece.rotatePiece();
		if(piece.getLeftEdge() < 0) {
			piece.rotatePiece();
			piece.rotatePiece();
			piece.rotatePiece();
		}
		if(piece.getRightEdge() > 10) {
			piece.rotatePiece();
			piece.rotatePiece();
			piece.rotatePiece();
		}
		if(piece.height() + piece.getY() >= 20) {
			piece.rotatePiece();
			piece.rotatePiece();
			piece.rotatePiece();
		}
		
		int[][]shape = piece.getShape();
		for (int i=0; i<piece.length(); i++) {
			for (int j = piece.height() -1 ; j>=0; j--) {
				if(shape[j][i] != 0) {
					int x = i + piece.getX();
					int y = j + piece.getY();
					if (y<0) {
						break;
					}
					if (stoppedPieces[y][x] != null) {
						piece.rotatePiece();
						piece.rotatePiece();
						piece.rotatePiece();
					}
				}
			}
		}
		
		
		
		repaint();
	}
	
	private boolean checkBottom() {
		if (piece.getY() + piece.height() == 20) {
			return false;
		}
		
		int[][]shape = piece.getShape();
		
		for (int i=0; i<piece.length(); i++) {
			for (int j = piece.height() - 1; j>=0; j--) {
				if(shape[j][i] != 0) {
					int x = i + piece.getX();
					int y = 1 + j + piece.getY();
					if (y<0) {
						break;
					}
					if (stoppedPieces[y][x] != null) {
						return false;
					}
					break;
				}
			}
		}
		return true;
	}
	
	private boolean checkLeft() {
		if (piece.getLeftEdge() == 0) {
			return false;
		}
		int[][]shape = piece.getShape();
		
		for (int i=0; i<piece.height(); i++) {
			for (int j = 0; j<piece.length(); j++) {
				if(shape[i][j] != 0) {
					int x = j + piece.getX() - 1;
					int y = i + piece.getY();
					if (y<0) {
						break;
					}
					if (stoppedPieces[y][x] != null) {
						return false;
					}
					break;
				}
			}
		}
		return true;
	}
	
	private boolean checkRight() {
		if (piece.getRightEdge() == 10) {
			return false;
		}
		int[][]shape = piece.getShape();
		
		for (int i=0; i<piece.height(); i++) {
			for (int j = piece.length() - 1; j>=0; j--) {
				if(shape[i][j] !=0) {
					int x = j + piece.getX() + 1;
					int y = i + piece.getY();
					if (y<0) {
						break;
					}
					if (stoppedPieces[y][x] != null) {
						return false;
					}
					break;
				}
			}
		}
		return true;
	}
	

	
	public void atRestPiece() {
		
		int[][] shape = piece.getShape();
		int Y = piece.getY();
		int X = piece.getX();
		int height = piece.height();
		int length = piece.length();
		Color color = piece.getColor();
		for (int i = 0 ; i < height; i++) {
			for (int j = 0; j < length; j++) { 
				if (shape[i][j] == 1) {
					stoppedPieces[i + Y][j + X] = color;

				}
			}
		}
		clearedLines = findFullLines();
		allClearedLines += clearedLines;
		
		
	}
	
	public int getAllClearedLines() {
		return this.allClearedLines;
	}
	
	public int getClearedLines() {
		return this.clearedLines;
	}
	
	public void drawPiece(Graphics g) {
		for (int i = 0 ; i < piece.height(); i++) {
			for (int j = 0; j < piece.length(); j++) { 
				if (piece.getShape()[i][j] == 1) {
					int y = (piece.getY() + i) * sizeCell;
					int x = (piece.getX() + j) * sizeCell;
					g.setColor(piece.getColor());
					g.fillRect(x, y, sizeCell, sizeCell);
					g.setColor(Color.black);
					g.drawRect(x, y, sizeCell, sizeCell);
				}
			}
		}
	}
	
	public void drawStoppedPieces(Graphics g) {
		Color color;
		for (int i = 0 ; i < rows; i++) {
			for (int j = 0; j < columns; j++) { 
				color = stoppedPieces[i][j];
				if(color != null) {
					int x = j * sizeCell;
					int y = i * sizeCell;
					g.setColor(color);
					g.fillRect(x, y, sizeCell, sizeCell);
					g.setColor(Color.black);
					g.drawRect(x, y, sizeCell, sizeCell);
				}
			}
		}
	}
	

	
	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
        drawPiece(g);
		drawStoppedPieces(g);
        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                g.drawRect(x * sizeCell, y * sizeCell, sizeCell, sizeCell);
            }
        }

        
	}
	
}
