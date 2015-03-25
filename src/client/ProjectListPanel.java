package client;

import java.awt.*;
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
		this.setLayout(new FlowLayout());
		this.projectPanels = new ArrayList<ProjectPanel>();
		for(Project p : projects) {
			ProjectPanel pp = new ProjectPanel(p);
			projectPanels.add(pp);
			add(pp);
		}
	}

	public List<Integer> getSelectedFields() {
		List<Integer> selectedFields = new ArrayList<Integer>();
		int pIdx = 0;
		for(ProjectPanel pp : projectPanels) {
			for(Integer i : pp.getSelectedFieldIndices()) {
				selectedFields.add(projects.get(pIdx).getFields().get(i).getFieldId());
			}
			pIdx++;
		}
		return selectedFields;
	}

}
