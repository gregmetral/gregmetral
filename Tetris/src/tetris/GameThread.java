package tetris;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;



//we need the thread to make the computer wait and do other things at the same time
public class GameThread extends Thread{ 
	
	private GamePanel gamePanel;
	private Panel score;
	private Panel level;
	private Panel linesCompleted;
	private Panel timerLabel;
	private Panel pause;
	private Panel highScore;
	private Scanner scan;
	private FileWriter fw;
	private int speed;
	private int lvl = 1;
	private boolean combo = false;
	private int currentScore;
	private long startTime = System.currentTimeMillis();
	private boolean gamePause = false;
	
	
	public GameThread(GamePanel gamePanel, Panel score, Panel level, Panel linesCompleted, Panel timerLabel, int x, Panel restart, Panel pause, Panel highScore)  {
	
		this.gamePanel = gamePanel;
		this.score = score;
		this.level = level;
		this.linesCompleted = linesCompleted;
		this.timerLabel = timerLabel;
		this.currentScore = x;
		this.pause = pause;
		this.highScore = highScore;


				
	}
	

	
	public void setCurrentScore(int x) {
		this.currentScore = x;
	}
	
	@Override
	public void run() {

		
		
		
		while(true) {


			score.updateScore(currentScore);
			long gameTime = System.currentTimeMillis() - startTime;
			long seconds = gameTime / 1000;
			long secondsDisplay = seconds % 60;
			long minutes = seconds / 60;
			timerLabel.updateTimerLabel(minutes, secondsDisplay); 
			gamePanel.newPiece();
			while(this.gamePanel.moveDownPiece() == true) {

				while (pause.getPause()) {
					try {
						gamePanel.setStopPieceMovement(true);
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				if (currentScore < 1000) {
					speed = 1000;
					lvl = 1;
				}else if(currentScore < 3000) {
					speed = 600;
					lvl = 2;
				}else if(currentScore < 6000) {
					speed = 350;
					lvl = 3;
				}else if(currentScore < 10000) {
					speed = 150;
					lvl = 4;
				}else if(currentScore >= 10000) {
					speed = 50;
					lvl = 5;
				}
				level.updateLevel(lvl);
				
			
				try {       // no idea, eclipse told me to do so 
					gamePanel.setStopPieceMovement(false);
					Thread.sleep(speed);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
			}
			if (gamePanel.OOB()) {
				File file = new File("HIGHSCORE.txt");
				try {
					scan = new Scanner(file);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				int i =Integer.parseInt(scan.nextLine());  
				
				if (i < currentScore) {
					try {
						String s = String.valueOf(currentScore);
						fw = new FileWriter(file);
						fw.write(s);
						fw.close();
					} catch (IOException e) {
						e.printStackTrace();
					} 
					
				}
				break;
				
			}
			linesCompleted.updateLinesCompleted(gamePanel.getAllClearedLines());
			
			switch (gamePanel.getClearedLines()) {
			case 1 :
				currentScore += 100;
				combo = false;
				break;
			case 2 :
				currentScore += 200;
				combo = false;
				break;
			case 3 : 
				currentScore += 300;
				combo = false;
				break;
			case 4 :
				if(combo) {
					currentScore += 1200;
				}else {
					currentScore += 800;
					combo = true;
				}
			}
			
			score.updateScore(currentScore);


		}
	}
}
