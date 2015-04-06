package client.search;

import java.awt.*;


public class SearchGUI {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {		
			public void run() {
				SearchFrame frame = new SearchFrame();
				frame.setController(new SearchController(frame));
				frame.setVisible(true);
			}
		});
	}

}
