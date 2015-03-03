package server.database;

import java.util.ArrayList;

import org.w3c.dom.*;

public class IndexerData {
	
	public IndexerData(Element rootElement) {
		private ArrayList<User> users = new ArrayList<User>();
		private ArrayList<Project> projects = new ArrayList<Project>();
		public IndexerData(Element root) {
		ArrayList<Element> rootElements = DataImporter.getChildElements(root);
		ArrayList<Element> userElements =
		DataImporter.getChildElements(rootElements.get(0));
		for(Element userElement : userElements) {
		users.add(new User(userElement));
		}
		ArrayList<Element> projectElements =
		DataImporter.getChildElements(rootElements.get(1));
		for(Element projectElement : projectElements) {
		projects.add(new Project(projectElement));
		}
		}
	}

}
