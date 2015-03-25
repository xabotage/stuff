package client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import shared.communication.*;
import shared.model.Project;
import client.communication.*;
import shared.model.SearchResultObject;

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
		params.setUserName(searchFrame.getUserName());
		params.setPassword(searchFrame.getPassword());

		GetFields_Params fParams = new GetFields_Params();
		fParams.setUserName(searchFrame.getUserName());
		fParams.setPassword(searchFrame.getPassword());
		
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

	public void searchKeywords() {
		try {
			ClientCommunicator cu = new ClientCommunicator(searchFrame.getHost(),
					Integer.parseInt(searchFrame.getPort()));
			String keywordsString = searchFrame.getSearchKeywords();
			Search_Params params = new Search_Params();

			// set the field ids
			List<Integer> selectedFields = searchFrame.getSelectedFields();
			params.setFieldIds(searchFrame.getSelectedFields());

			// set the search values
			List<String> searchValues = new ArrayList<String>();
			for(String sv : Arrays.asList(keywordsString.split(","))) {
				if(!sv.equals(""))
					searchValues.add(sv.toLowerCase());
			}
			params.setSearchValue(searchValues);
			params.setUserName(searchFrame.getUserName());
			params.setPassword(searchFrame.getPassword());
			if(searchValues.size() == 0) {
				throw new Exception("No search values specified");
			}
			Search_Result result = cu.search(params);
			result.setUrlBase("http://" + searchFrame.getHost() + ":" + searchFrame.getPort() + "/");
			List<String> searchResultImages = new ArrayList<String>();
			for(SearchResultObject sr : result.getSearchResults()) {
				searchResultImages.add(result.getUrlBase() + sr.getImageUrl());
			}
			searchFrame.setSearchResultImages(searchResultImages);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadImage(String imageUrl) {
	}
}
