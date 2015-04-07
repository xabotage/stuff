package client;

import java.awt.*;

import javax.swing.JOptionPane;


public class ClientMain {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {		
			public void run() {
				IndexerFrame frame = new IndexerFrame();
				//frame.setVisible(true);
			}
		});
	}

}
