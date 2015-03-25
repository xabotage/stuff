package client;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import shared.model.*;

@SuppressWarnings("serial")
public class ProjectListPanel extends JPanel {
	private List<ProjectPanel> projectPanels;
	private List<Project> projects;
	
	public void setProjects(List<Project> projects) {
		this.projects = projects;
		removeAll();
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.projectPanels = new ArrayList<ProjectPanel>();
		for(Project p : projects) {
			ProjectPanel pp = new ProjectPanel(p);
			projectPanels.add(pp);
			add(pp);
		}
	}

}
