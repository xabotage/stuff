package client;

import java.util.List;

import shared.communication.*;
import shared.model.Project;
import client.communication.*;

public class SearchController {
	
	public SearchController(SearchFrame searchFrame) {
		this.searchFrame = searchFrame;
	}

	private SearchFrame searchFrame;

	public SearchFrame getSearchFrame() {
		return searchFrame;
	}

	public void setSearchFrame(SearchFrame searchFrame) {
		this.searchFrame = searchFrame;
	}
	
	public void getProjectsFromServer() {
		ClientCommunicator cu = new ClientCommunicator(searchFrame.getHost(), 
				Integer.parseInt(searchFrame.getPort()));
		GetProjects_Params params = new GetProjects_Params();
		params.setUserName("sheila");//searchFrame.getUserName());
		params.setPassword("parker");//searchFrame.getPassword());

		GetFields_Params fParams = new GetFields_Params();
		fParams.setUserName("sheila");//searchFrame.getUserName());
		fParams.setPassword("parker");//searchFrame.getPassword());
		
		try {

			GetProjects_Result result = cu.getProjects(params);
			List<Project> projects = result.getProjects();
			for(Project p : projects) {
				fParams.setProjectId(p.getProjectId());
				p.setFields(cu.getFields(fParams).getFields());
			}
			searchFrame.generateProjectsComponent(projects);
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}
}
