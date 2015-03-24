package client;

import java.util.List;

import javax.swing.JFrame;

import shared.communication.*;
import shared.model.*;

@SuppressWarnings("serial")
public class SearchFrame extends JFrame {
	public static final int DEFAULT_WIDTH = 640;
	public static final int DEFAULT_HEIGHT = 480;

	private ProjectListPanel projectListPanel;
	private SettingsPanel settingsPanel;

	public SearchFrame() {
		this.setSize(SearchFrame.DEFAULT_WIDTH, SearchFrame.DEFAULT_HEIGHT);
		this.setTitle("Search Gui");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(200, 200);
		settingsPanel = new SettingsPanel();
		this.add(settingsPanel);
	}
	
	private void getProjectsFromServer() {
		ClientCommunicator cu = new ClientCommunicator(settingsPanel.getHost(), 
				Integer.parseInt(settingsPanel.getPort()));
		GetProjects_Params params = new GetProjects_Params();
		params.setUserName(settingsPanel.getUserName());
		params.setPassword(settingsPanel.getPassword());
		getView().setRequest(new XStream(new DomDriver()).toXML(params));
		getView().setResponse(cu.getProjects(params).toString());

		GetProjects_Result result = cu.getProjects(new )
		this.projectListPanel = new ProjectListPanel(cu.getP);
		this.removeAll();
		this.add(projectListPanel);
	}

}
