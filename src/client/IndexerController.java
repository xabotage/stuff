package client;

import shared.communication.*;
import shared.model.User;

import java.util.ArrayList;
import java.util.List;

import client.communication.ClientCommunicator;
import client.gui.IndexerFrame;
import shared.model.*;

public class IndexerController {
	private IndexerFrame indexerFrame;
	private int port;
	private String host;
	private String urlBase;

	public IndexerController(IndexerFrame indexerFrame, int port, String host) {
		this.indexerFrame = indexerFrame;
		this.port = port;
		this.host = host;
		this.urlBase = "http://" + host + ":" + port + "/";
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


	public User attemptLogin(String userName, String password) {
		ClientCommunicator cu = new ClientCommunicator(host, port);
		ValidateUser_Params params = new ValidateUser_Params();
		params.setUserName(userName);
		params.setPassword(password);
		try {
			ValidateUser_Result result = cu.validateUser(params);
			return result.getUser();
		} catch (ClientException e) {
			e.printStackTrace();
			return null;
		}
	}


	public List<Project> getProjects(User user) {
		ClientCommunicator cu = new ClientCommunicator(host, port);
		GetProjects_Params params = new GetProjects_Params();
		params.setUserName(user.getUserName());
		params.setPassword(user.getPassword());

		GetFields_Params fParams = new GetFields_Params();
		fParams.setUserName(user.getUserName());
		fParams.setPassword(user.getPassword());
		
		try {

			GetProjects_Result result = cu.getProjects(params);
			List<Project> projects = result.getProjects();
			for(Project p : projects) {
				fParams.setProjectId(p.getProjectId());
				p.setFields(cu.getFields(fParams).getFields());
			}
			return projects;
		} catch (ClientException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Project getCurrentUserProjectWithId(int projectId, User user) {
		if(user.getCurrentBatch() == -1)
			return null; 

		ClientCommunicator cu = new ClientCommunicator(host, port);
		GetProjects_Params params = new GetProjects_Params();
		params.setUserName(user.getUserName());
		params.setPassword(user.getPassword());

		GetFields_Params fParams = new GetFields_Params();
		fParams.setUserName(user.getUserName());
		fParams.setPassword(user.getPassword());
		
		try {
			GetProjects_Result result = cu.getProjects(params);
			List<Project> projects = result.getProjects();
			for(Project p : projects) {
				if(p.getProjectId() == projectId) {
					fParams.setProjectId(p.getProjectId());
					p.setFields(cu.getFields(fParams).getFields());
					return p;
				}
			}
			return null;
		} catch (ClientException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String getSampleImageForProject(Project project, User user) {
		try {
			ClientCommunicator cu = new ClientCommunicator(host, port);
			GetSampleImage_Params params = new GetSampleImage_Params();
			params.setProjectId(project.getProjectId());
			params.setUserName(user.getUserName());
			params.setPassword(user.getPassword());
			GetSampleImage_Result result = cu.getSampleImage(params);
			return urlBase + result.getImageUrl();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Batch downloadBatch(Project project, User user) {
		try {
			ClientCommunicator cu = new ClientCommunicator(host, port);
			DownloadBatch_Params params = new DownloadBatch_Params();
			params.setProjectId(project.getProjectId());
			params.setUserName(user.getUserName());
			params.setPassword(user.getPassword());
			DownloadBatch_Result result = cu.downloadBatch(params);
			result.setUrlBase("http://" + host + ":" + port + "/");
			return result.getBatch();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void submitBatch(User user, String[][] values) {
		try {
			ClientCommunicator cu = new ClientCommunicator(host, port);
			SubmitBatch_Params params = new SubmitBatch_Params();
			params.setUserName(user.getUserName());
			params.setPassword(user.getPassword());
			Batch b = new Batch();
			b.setBatchId(user.getCurrentBatch());
			List<Record> records = new ArrayList<Record>();
			int recordNum = 1;
			for(String[] rs : values) {
				Record record = new Record();
				record.setRecordNum(recordNum);
				record.setBatchId(b.getBatchId());
				List<FieldValue> fvs = new ArrayList<FieldValue>();
				for(String vs : rs) {
					FieldValue fv = new FieldValue();
					fv.setValue(vs);
					fvs.add(fv);
				}
				record.setFieldValues(fvs);
				records.add(record);
				recordNum++;
			}
			b.setRecords(records);
			params.setBatch(b);

			cu.submitBatch(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
