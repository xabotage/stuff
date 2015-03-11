package servertester.controllers;

import java.util.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import client.communication.ClientCommunicator;

import servertester.views.*;
import shared.communication.*;
import shared.model.*;

public class Controller implements IController {

	private IView _view;
	
	public Controller() {
		return;
	}
	
	public IView getView() {
		return _view;
	}
	
	public void setView(IView value) {
		_view = value;
	}
	
	// IController methods
	//
	
	@Override
	public void initialize() {
		getView().setHost("localhost");
		getView().setPort("39640");
		operationSelected();
	}

	@Override
	public void operationSelected() {
		ArrayList<String> paramNames = new ArrayList<String>();
		paramNames.add("User");
		paramNames.add("Password");
		
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			break;
		case GET_PROJECTS:
			break;
		case GET_SAMPLE_IMAGE:
			paramNames.add("Project");
			break;
		case DOWNLOAD_BATCH:
			paramNames.add("Project");
			break;
		case GET_FIELDS:
			paramNames.add("Project");
			break;
		case SUBMIT_BATCH:
			paramNames.add("Batch");
			paramNames.add("Record Values");
			break;
		case SEARCH:
			paramNames.add("Fields");
			paramNames.add("Search Values");
			break;
		default:
			assert false;
			break;
		}
		
		getView().setRequest("");
		getView().setResponse("");
		getView().setParameterNames(paramNames.toArray(new String[paramNames.size()]));
	}

	@Override
	public void executeOperation() {
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			validateUser();
			break;
		case GET_PROJECTS:
			getProjects();
			break;
		case GET_SAMPLE_IMAGE:
			getSampleImage();
			break;
		case DOWNLOAD_BATCH:
			downloadBatch();
			break;
		case GET_FIELDS:
			getFields();
			break;
		case SUBMIT_BATCH:
			submitBatch();
			break;
		case SEARCH:
			search();
			break;
		default:
			assert false;
			break;
		}
	}
	
	private void validateUser() {
		try {
			ClientCommunicator cu = new ClientCommunicator(getView().getHost(), 
														   Integer.parseInt(getView().getPort()));
			String[] rawParams = getView().getParameterValues();
			ValidateUser_Params params = new ValidateUser_Params();
			params.setUserName(rawParams[0]);
			params.setPassword(rawParams[1]);
			getView().setRequest(new XStream(new DomDriver()).toXML(params));
			getView().setResponse(cu.validateUser(params).toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			getView().setResponse("FAILED");
		}
	}
	
	private void getProjects() {
		try {
			ClientCommunicator cu = new ClientCommunicator(getView().getHost(), 
														   Integer.parseInt(getView().getPort()));
			String[] rawParams = getView().getParameterValues();
			GetProjects_Params params = new GetProjects_Params();
			params.setUserName(rawParams[0]);
			params.setPassword(rawParams[1]);
			getView().setRequest(new XStream(new DomDriver()).toXML(params));
			getView().setResponse(cu.getProjects(params).toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			getView().setResponse("FAILED");
		}
	}
	
	private void getSampleImage() {
		try {
			ClientCommunicator cu = new ClientCommunicator(getView().getHost(), 
														   Integer.parseInt(getView().getPort()));
			String[] rawParams = getView().getParameterValues();
			GetSampleImage_Params params = new GetSampleImage_Params();
			params.setProjectId(Integer.parseInt(rawParams[2]));
			params.setUserName(rawParams[0]);
			params.setPassword(rawParams[1]);
			getView().setRequest(new XStream(new DomDriver()).toXML(params));
			String urlBase = "http://" + getView().getHost() + ":" + getView().getPort() + "/";
			getView().setResponse(urlBase + cu.getSampleImage(params).toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			getView().setResponse("FAILED");
		}
	}
	
	private void downloadBatch() {
		try {
			ClientCommunicator cu = new ClientCommunicator(getView().getHost(), 
														   Integer.parseInt(getView().getPort()));
			String[] rawParams = getView().getParameterValues();
			DownloadBatch_Params params = new DownloadBatch_Params();
			params.setProjectId(Integer.parseInt(rawParams[2]));
			params.setUserName(rawParams[0]);
			params.setPassword(rawParams[1]);
			getView().setRequest(new XStream(new DomDriver()).toXML(params));
			DownloadBatch_Result result = cu.downloadBatch(params);
			result.setUrlBase("http://" + getView().getHost() + ":" + getView().getPort() + "/");
			getView().setResponse(result.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			getView().setResponse("FAILED");
		}
	}
	
	private void getFields() {
		try {
			ClientCommunicator cu = new ClientCommunicator(getView().getHost(), 
														   Integer.parseInt(getView().getPort()));
			String[] rawParams = getView().getParameterValues();
			GetFields_Params params = new GetFields_Params();
			if(!rawParams[2].equals("")) {
				params.setProjectId(Integer.parseInt(rawParams[2]));
			} else {
				params.setProjectId(-1);
			}
			params.setUserName(rawParams[0]);
			params.setPassword(rawParams[1]);
			getView().setRequest(new XStream(new DomDriver()).toXML(params));
			getView().setResponse(cu.getFields(params).toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			getView().setResponse("FAILED");
		}
	}
	
	private void submitBatch() {
		try {
			ClientCommunicator cu = new ClientCommunicator(getView().getHost(), 
														   Integer.parseInt(getView().getPort()));
			String[] rawParams = getView().getParameterValues();
			SubmitBatch_Params params = new SubmitBatch_Params();
			params.setUserName(rawParams[0]);
			params.setPassword(rawParams[1]);
			Batch b = new Batch();
			b.setBatchId(Integer.parseInt(rawParams[2]));
			List<Record> records = new ArrayList<Record>();
			String[] recordStrings = rawParams[3].split(";",-1);
			int recordNum = 1;
			for(String rs : recordStrings) {
				Record record = new Record();
				record.setRecordNum(recordNum);
				record.setBatchId(b.getBatchId());
				String[] valueStrings = rs.split(",",-1);
				List<FieldValue> fvs = new ArrayList<FieldValue>();
				for(String vs : valueStrings) {
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

			getView().setRequest(new XStream(new DomDriver()).toXML(params));
			getView().setResponse(cu.submitBatch(params).toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			getView().setResponse("FAILED");
		}
	}
	
	private void search() {
		try {
			ClientCommunicator cu = new ClientCommunicator(getView().getHost(), 
														   Integer.parseInt(getView().getPort()));
			String[] rawParams = getView().getParameterValues();
			Search_Params params = new Search_Params();
			
			// set the field ids
			List<String> fieldIdsAsStrings = new ArrayList<String>(Arrays.asList(rawParams[2].split(",")));
			List<Integer> fieldIds = new ArrayList<Integer>();
			for(String s : fieldIdsAsStrings) {
				fieldIds.add(Integer.parseInt(s));
			}
			params.setFieldIds(fieldIds);
			
			// set the search values
			List<String> searchValues = new ArrayList<String>();
			for(String sv : Arrays.asList(rawParams[3].split(","))) {
				if(!sv.equals(""))
					searchValues.add(sv.toLowerCase());
			}
			params.setSearchValue(searchValues);
			params.setUserName(rawParams[0]);
			params.setPassword(rawParams[1]);
			getView().setRequest(new XStream(new DomDriver()).toXML(params));
			if(searchValues.size() == 0) {
				throw new Exception("No search values specified");
			}
			Search_Result result = cu.search(params);
			result.setUrlBase("http://" + getView().getHost() + ":" + getView().getPort() + "/");
			getView().setResponse(result.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			getView().setResponse("FAILED");
		}
	}

}

