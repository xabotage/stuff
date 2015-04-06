package client.search;

import shared.model.Project;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by daniel on 3/25/15.
 */
public class SearchPanel extends JPanel {
	private SearchKeywordsPanel searchKeywordsPanel;
	private ProjectListPanel projectListPanel;
	private SearchController controller;

	public SearchPanel() {
		setLayout(new BorderLayout());
		projectListPanel = new ProjectListPanel();
		add(projectListPanel, BorderLayout.CENTER);

		searchKeywordsPanel = new SearchKeywordsPanel();
		add(searchKeywordsPanel, BorderLayout.SOUTH);
	}

	public void setProjects(List<Project> projects) {
		projectListPanel.setProjects(projects);
	}

	public void setController(SearchController controller) {
		this.controller = controller;
		searchKeywordsPanel.setController(controller);
	}

	public List<Integer> getSelectedFields() {
		return projectListPanel.getSelectedFields();
	}

	public String getSearchKeywords() {
		return searchKeywordsPanel.getSearchKeywords();
	}
}
