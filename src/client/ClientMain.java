package client;

import java.awt.*;

import javax.swing.JOptionPane;

import client.gui.IndexerFrame;


public class ClientMain {

	public static void main(String[] args) {
		String host = "localhost";
		int port = 8989;
		if(args.length == 2) {
			if (!host.equals(""))
				host = args[0];
			if (!args[1].equals(""))
				port = Integer.parseInt(args[1]);
		}

		final int fPort = port;
		final String fHost = host;

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				IndexerFrame frame = new IndexerFrame();
				frame.setController(new IndexerController(frame, fPort, fHost));
				frame.showLoginDialog();
			}
		});
	}

}
