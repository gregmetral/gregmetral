package GUI;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;



public class EndingMenu extends JFrame {
	
	public EndingMenu(HashMap<Integer, Integer> playerMap) {
		
		int nbPlayers = playerMap.size();
		// set default screen settings
		getContentPane().setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(100+150*nbPlayers,140);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);

		//convert map to a List
		List<Entry<Integer, Integer>> list = new LinkedList<Map.Entry<Integer, Integer>>(playerMap.entrySet());

		//sorting the list with a comparator
		Collections.sort(list, new Comparator<Entry<Integer, Integer>>() {
			public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		//convert sortedMap back to Map
		Map<Integer, Integer> sortedMap = new LinkedHashMap<Integer, Integer>();
		for (Entry<Integer, Integer> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		
		int j = 0;
			
		for (Integer i : sortedMap.keySet()) {
			

			JLabel playerName = new JLabel("joueur " +(i + 1));
			playerName.setBounds(50 + 150*j, 20, 70, 20);
			getContentPane().add(playerName);
			playerName.setVisible(true);
			
			JLabel playerPoints = new JLabel(sortedMap.get(i) + " points");
			playerPoints.setBounds(50 + 150*j, 60, 70, 20);
			getContentPane().add(playerPoints);
			playerPoints.setVisible(true);
			
			
			if(i==0) {
				JLabel results = new JLabel((i + 1) +"er");
				results.setBounds(16 + 150*i, 11, 70, 20);
				getContentPane().add(results);
				results.setVisible(true);
			}else {
				JLabel results = new JLabel((i + 1) +"eme");
				results.setBounds(16 + 150*i, 11, 70, 20);
				getContentPane().add(results);
				results.setVisible(true);
			}


			
			j+=1;
		}
			
	}

}
