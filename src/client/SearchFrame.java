package client;

import java.util.List;

import javax.swing.JFrame;

import shared.model.*;

@SuppressWarnings("serial")
public class SearchFrame extends JFrame {
	public static final int DEFAULT_WIDTH = 640;
	public static final int DEFAULT_HEIGHT = 480;

	private ProjectListPanel projectListPanel;
	private SettingsPanel settingsPanel;
	private SearchController controller;

	public SearchFrame() {
		super();
		this.setSize(SearchFrame.DEFAULT_WIDTH, SearchFrame.DEFAULT_HEIGHT);
		this.setTitle("Search Gui");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(200, 200);
		settingsPanel = new SettingsPanel();
		add(settingsPanel);
		projectListPanel = new ProjectListPanel();
		add(settingsPanel);
		projectListPanel.setVisible(false);
	}
	
	public void generateProjectsComponent(List<Project> projects) {
		projectListPanel.setVisible(true);
		projectListPanel.setProjects(projects);
		pack();
	}
	

	/**
	 * @return the controller
	 */
	public SearchController getController() {
		return controller;
	}

	/**
	 * @param controller the controller to set
	 */
	public void setController(SearchController controller) {
		this.controller = controller;
		settingsPanel.setController(controller);
	}
	
	public String getUserName() {
		return settingsPanel.getName();
	}

	public String getPassword() {
		return settingsPanel.getPassword();
	}

	public String getHost() {
		return settingsPanel.getHost();
	}

	public String getPort() {
		return settingsPanel.getPort();
	}

}
