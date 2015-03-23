package client;

import java.awt.FlowLayout;

import javax.swing.*;

import shared.model.*;

@SuppressWarnings("serial")
public class ProjectPanel extends JPanel {
	private JList fieldList;
	private JLabel projectLabel;
	private Project project;

	public ProjectPanel(Project project) {
		this.setLayout(new FlowLayout());
		this.project = project;
		this.projectLabel = new JLabel(project.getTitle());
		this.add(projectLabel);
		DefaultListModel<String> lm = new DefaultListModel<String>();
		for(Field f : project.getFields()) {
			lm.addElement(f.getTitle());
		}
		this.fieldList = new JList();
		fieldList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		this.add(fieldList);
	}

}
