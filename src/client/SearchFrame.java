package client;

import java.util.List;

import javax.swing.JFrame;

import client.communication.ClientCommunicator;
import shared.model.*;

@SuppressWarnings("serial")
public class SearchFrame extends JFrame {
	public static final int DEFAULT_WIDTH = 640;
	public static final int DEFAULT_HEIGHT = 480;

	private ProjectListPanel projectListPanel;
	private HostPortPanel hostPortPanel;

	public SearchFrame() {
		this.setSize(SearchFrame.DEFAULT_WIDTH, SearchFrame.DEFAULT_HEIGHT);
		this.setTitle("Search Gui");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(200, 200);
	}
	
	private void getProjectsFromServer() {
		ClientCommunicator cu = new ClientCommunicator(getHost(), 
				Integer.parseInt(getPort()));
		this.projectListPanel = new ProjectListPanel(projects);
		this.removeAll();
		this.add(projectListPanel);
	}

}
