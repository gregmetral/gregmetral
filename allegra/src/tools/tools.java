package tools;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.JPanel;

public class tools {
    public static void setImage(Component button, int value){
        JButton b = (JButton) button;
        b.setIcon(new ImageIcon("ressources\\"+value+".jpg"));
    }

    /**
     * convert matrix coord to JFrame coord
     * @param x
     * @param y
     * @param indexPlayer
     * @return
     */
    public static int[] convert(int x, int y, int indexPlayer){
        // second value corresponds to current player or neighbor
        // 1: current player, 2: neighbor

        // if top matrix
        if (indexPlayer < 3) {
            if (x < 4) { // regular coord
                return new int[] {x + y*4, 1};
            }else{ // neighbor coord
                return new int[] {x*y, 2};
            }
        }else{
            if (x < 4) { // regular coord
                x = 3-x;
                return new int[] {x + y*4, 1};
            }else{ // neighbor coord
                return new int[] {x*(y+1)-1, 2};
            }
        }
    }

    /**
     * convert JFrame coord to matrix coord
     * @param i
     * @param indexPlayer
     * @return
     */
    public static int[] convert(int i, int indexPlayer){
        int x, y; 
        // top matrix
        if (indexPlayer< 3) {
            y = i/4;
            x = i - (y*4);
            return new int[] {x, y};
        }else{ // bottom matrix
            y = i/4;
            x = (y+1)*4 - (i+1);
            return new int[] {x, y};
        }
    }

    public static void setEnabled(JPanel panel, boolean enabled) {
        Component[] components = panel.getComponents();
        for (Component comp : components) {
            comp.setEnabled(enabled);
        }
    }

    public static void setPartialDisable(JPanel panel, int indexplayer) {
        Component[] components = panel.getComponents();
        int columnShifter= (indexplayer > 2) ? 1 : 0;

        // top matrix: make left column accessible
            for (int i = 0; i < components.length; i++) {
                if ((i+columnShifter)%4 != 0){
                    components[i].setEnabled(false);
                }else{
                    components[i].setEnabled(true);
                }
            }
    }
}
