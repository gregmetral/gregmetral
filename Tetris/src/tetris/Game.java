package tetris;


public class Game {

		private static Panel panel;
		private static MainMenu mainMenu;
		
		public static void start(int x) {
			panel.setVisible(true);
			panel.start(x);
		}
		

		
		public static void main(String[] args) {
			startGame();
			
		}
		
		public static void startGame() {
			java.awt.EventQueue.invokeLater(new Runnable() { 
				public void run() {
					panel = new Panel();
					mainMenu = new MainMenu();
					mainMenu.setVisible(true);
				}
					
			});

		}
 
}
