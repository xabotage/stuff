package client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import shared.model.*;

@SuppressWarnings("serial")
public class IndexerFrame extends JFrame {
	public static final int DEFAULT_WIDTH = 640;
	public static final int DEFAULT_HEIGHT = 480;

	private JMenuBar menuBar;
	private JMenuItem downloadBatch;
	private ImageButtons
	private SettingsPanel settingsPanel;
	private SearchPanel searchPanel;
	private SearchController controller;
	private SearchResultPanel searchResultPanel;

	public IndexerFrame() {
		super();
		this.setSize(IndexerFrame.DEFAULT_WIDTH, IndexerFrame.DEFAULT_HEIGHT);
		this.setTitle("Search Gui");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(200, 200);

		this.menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");

		this.downloadBatch = new JMenuItem("Download Batch");
		downloadBatch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		downloadBatch.setEnabled(false);
		menu.add(downloadBatch);
		
		JMenuItem logout = new JMenuItem("Logout");
		logout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		menu.add(logout);

		JMenuItem exit = new JMenuItem("Exit");
		logout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		menu.add(exit);
		menuBar.get

		this.setJMenuBar(menuBar);
		
		settingsPanel = new SettingsPanel();
		add(settingsPanel, BorderLayout.NORTH);

		searchPanel = new SearchPanel();
		add(searchPanel, BorderLayout.CENTER);
		searchPanel.setVisible(false);

		searchResultPanel = new SearchResultPanel();
		add(searchResultPanel, BorderLayout.SOUTH);
		searchResultPanel.setVisible(false);
		pack();
	}
	
	public void generateProjectsComponent(List<Project> projects) {
		searchPanel.setVisible(true);
		searchPanel.setProjects(projects);
		pack();
	}
	

	public SearchController getController() {
		return controller;
	}

	public void setController(SearchController controller) {
		this.controller = controller;
		settingsPanel.setController(controller);
		searchPanel.setController(controller);
	}

	public String getSearchKeywords() {
		return searchPanel.getSearchKeywords();
	}

	public List<Integer> getSelectedFields() {
		return searchPanel.getSelectedFields();
	}

	public void setSearchResultImages(List<String> images) {
		searchResultPanel.setVisible(true);
		searchResultPanel.setSearchResultImages(images);
		pack();
	}
	
	public String getUserName() {
		return settingsPanel.getUserName();
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
