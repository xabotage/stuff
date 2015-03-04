package server.database;

import java.util.List;
import java.util.ArrayList;
import org.w3c.dom.*;

import shared.model.*;


public class IndexerData {
	private List<User> users;
	private List<Project> projects;
	
	public IndexerData(Element rootElement) {
		users = new ArrayList<User>();
		projects = new ArrayList<Project>();
		ArrayList<Element> rootElements = DataImporter.getChildElements(rootElement);
		ArrayList<Element> userElements =
		DataImporter.getChildElements(rootElements.get(0));
		for(Element userElement : userElements) {
			users.add(new User(userElement));
		}
		ArrayList<Element> projectElements = DataImporter.getChildElements(rootElements.get(1));
		for(Element projectElement : projectElements) {
			projects.add(new Project(projectElement));
		}
	}

}
