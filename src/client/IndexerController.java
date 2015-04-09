package client;

import shared.communication.ValidateUser_Params;
import shared.communication.ValidateUser_Result;
import client.communication.ClientCommunicator;

public class IndexerController {

	private IndexerFrame indexerFrame;
	private int port;
	private String host;

	public IndexerController(IndexerFrame indexerFrame, int port, String host) {
		this.indexerFrame = indexerFrame;
		this.port = port;
		this.host = host;
	}

	public IndexerFrame getIndexerFrame() {
		return indexerFrame;
	}

	public void setIndexerFrame(IndexerFrame indexerFrame) {
		this.indexerFrame = indexerFrame;
	}
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}


	public boolean attemptLogin(String userName, String password) {
		ClientCommunicator cu = new ClientCommunicator(host, port);
		ValidateUser_Params params = new ValidateUser_Params();
		params.setUserName(userName);
		params.setPassword(password);
		try {
			ValidateUser_Result result = cu.validateUser(params);
			if(result.getUser() != null)
				return true;
			else
				return false;
		} catch (ClientException e) {
			e.printStackTrace();
			return false;
		}
	}

	/*
	public void getProjectsFromServer() {
		ClientCommunicator cu = new ClientCommunicator(indexerFrame.getHost(), 
				Integer.parseInt(indexerFrame.getPort()));
		GetProjects_Params params = new GetProjects_Params();
		params.setUserName(indexerFrame.getUserName());
		params.setPassword(indexerFrame.getPassword());

		GetFields_Params fParams = new GetFields_Params();
		fParams.setUserName(indexerFrame.getUserName());
		fParams.setPassword(indexerFrame.getPassword());
		
		try {

			GetProjects_Result result = cu.getProjects(params);
			List<Project> projects = result.getProjects();
			for(Project p : projects) {
				fParams.setProjectId(p.getProjectId());
				p.setFields(cu.getFields(fParams).getFields());
			}
			indexerFrame.generateProjectsComponent(projects);
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}
	*/
}
