package tetris;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.border.LineBorder;

public class Panel extends JFrame implements KeyListener{
	
	private GamePanel gamePanel;
	private JLabel score;
	private JLabel level;
	private JLabel linesCompleted;
	private JLabel timerLabel;
	private JButton restart;
	private JButton pause;
	private JLabel highScore;
	private int test;
	private boolean gamePause = false;
	private Clip clip;
	private File wavFile;
	private Scanner scan;

	public Panel() {
		getContentPane().setBackground(Color.GRAY);
		this.getContentPane().setLayout(null);//now the setBorder() works
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800,1000);
        this.setLocationRelativeTo(null);
        this.setResizable(false); 


		score = new JLabel("SCORE     0");
		score.setSize(200, 50);
		score.setBorder(new LineBorder(new Color(255,255,255)));
		score.setLocation(550, 50);
		score.setBackground(Color.WHITE);
		Font font = new Font("Verdana", Font.BOLD, 18);
		score.setFont(font);
		score.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(score);
	
		level = new JLabel("LEVEL     1");
		level.setSize(130, 50);
		level.setBorder(new LineBorder(new Color(255,255,255)));
		level.setLocation(550, 120);
		level.setBackground(Color.WHITE);
		level.setFont(font);
		level.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(level);
		
		linesCompleted = new JLabel("LINES    0");
		linesCompleted.setSize(130, 50);
		linesCompleted.setBorder(new LineBorder(new Color(255,255,255)));
		linesCompleted.setLocation(550, 190);
		linesCompleted.setBackground(Color.WHITE);
		linesCompleted.setFont(font);
		linesCompleted.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(linesCompleted);
		
		timerLabel = new JLabel("TIME    0min 0sec");
		timerLabel.setSize(200, 50);
		timerLabel.setBorder(new LineBorder(new Color(255,255,255)));
		timerLabel.setLocation(550, 260);
		timerLabel.setBackground(Color.WHITE);
		timerLabel.setFont(font);
		timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(timerLabel);
        
		gamePanel = new GamePanel();
		gamePanel.setSize(450, 900);
		gamePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		gamePanel.setBackground(Color.WHITE);
		gamePanel.setLocation(68, 37);
		getContentPane().add(gamePanel);
		
		restart = new JButton("restart");
		restart.setSize(200, 50);
		restart.setLocation(550, 600);
		restart.setBackground(Color.WHITE);
		restart.setFont(font);
		getContentPane().add(restart);
		restart.setFocusable(false);
		restart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clip.stop();
				gamePause = true;
				setVisible(false);
				Game.startGame();
				
			}
		});
		
		try {
			Random r = new Random();
			int musicSelect = r.nextInt(3);
			switch (musicSelect) {
			case 0 :
				wavFile = new File("swagsophone.wav");
				break;
			case 1 : 
				wavFile = new File("Tetris_Theme.wav");
				break;
			case 2 : 
				wavFile = new File("Travelers_8bit.wav");
				break;
				
			}
		    clip = AudioSystem.getClip();
		    clip.open(AudioSystem.getAudioInputStream(wavFile));
		    clip.start();
		    clip.loop(clip.LOOP_CONTINUOUSLY);
		    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		    gainControl.setValue(-40);
		} catch (Exception e) {
		    System.out.println(e);
		}

		
		pause = new JButton("pause");
		pause.setSize(200, 50);
		pause.setLocation(550, 700);
		pause.setBackground(Color.WHITE);
		pause.setFont(font);
		getContentPane().add(pause);
		pause.setFocusable(false);
		pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (gamePause == false) {
					gamePause = true;
					pause.setText("play");

				}else {

					gamePause = false;
					pause.setText("pause");
				}

			}
		});
		
		highScore = new JLabel("HIGH SCORE   ");
		highScore.setSize(230, 50);
		highScore.setBorder(new LineBorder(new Color(255,255,255)));
		highScore.setLocation(530, 400);
		highScore.setBackground(Color.WHITE);
		highScore.setFont(font);
		highScore.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(highScore);
		
		addKeyListener(this);

		File read = new File("HIGHSCORE.txt");
		try {
			scan = new Scanner(read);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		updateHighScore(scan.nextLine());
	
	}

	public static void main(String[] args) {
		
		java.awt.EventQueue.invokeLater(new Runnable() { 
			public void run() {
				
				new Panel().setVisible(true);
			}
			
		});
	}
	
	public void start(int x){
		this.test = x;
		new GameThread(gamePanel, this, this, this, this, x, this, this, this).start();

	}
	
	public boolean getPause() {
		return this.gamePause;
	}
	
	public void updateTimerLabel(long minutes, long seconds) {
		timerLabel.setText("TIME    " + minutes +"m " + seconds + "s");
	}
	
	public void updateScore(int currentScore) {
		score.setText("SCORE     " + currentScore);
	}
	
	public void updateLevel(int currentLevel) {
		level.setText("LEVEL     " + currentLevel);
	}
	
	public void updateLinesCompleted(int currentLinesCompleted) {
		linesCompleted.setText("LINES    " + currentLinesCompleted);
	}
	
	public void updateHighScore(String currentHighScore) {
		highScore.setText("HIGH SCORE   " + currentHighScore);
	}
	
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
           gamePanel.moveRightPiece();
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
           gamePanel.moveLeftPiece();
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            gamePanel.rotate();
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            gamePanel.hardDrop();
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
