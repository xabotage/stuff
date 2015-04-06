package client.search;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;

import shared.model.*;

@SuppressWarnings("serial")
public class ProjectPanel extends JPanel {
	private JList fieldList;
	private JLabel projectLabel;
	private Project project;

	public ProjectPanel(Project project) {
		this.project = project;
		this.projectLabel = new JLabel(project.getTitle());
		this.add(projectLabel, BorderLayout.NORTH);
		DefaultListModel<String> lm = new DefaultListModel<String>();
		for(Field f : project.getFields()) {
			lm.addElement(f.getTitle());
		}
		this.fieldList = new JList(lm);
		fieldList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		this.add(fieldList, BorderLayout.CENTER);
	}

	public int[] getSelectedFieldIndices()  {
		return fieldList.getSelectedIndices();
	}

}
