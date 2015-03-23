package client;

import java.awt.*;


public class SearchGUI {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {		
			public void run() {
				SearchFrame frame = new SearchFrame();
				frame.setVisible(true);
			}
		});
	}

}
