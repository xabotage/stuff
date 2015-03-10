package servertester.controllers;

import java.util.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import client.communication.ClientCommunicator;

import servertester.views.*;
import shared.communication.SubmitBatch_Params;
import shared.communication.ValidateUser_Params;
import shared.model.Batch;
import shared.model.FieldValue;
import shared.model.Record;

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
			getView().setResponse("FAILED");
		}
	}
	
	private void getProjects() {
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
			getView().setResponse("FAILED");
		}
	}
	
	private void getSampleImage() {
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
			getView().setResponse("FAILED");
		}
	}
	
	private void downloadBatch() {
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
			getView().setResponse("FAILED");
		}
	}
	
	private void getFields() {
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
			String[] recordStrings = rawParams[3].split(";");
			int recordNum = 1;
			for(String rs : recordStrings) {
				Record record = new Record();
				record.setRecordNum(recordNum);
				record.setBatchId(b.getBatchId());
				String[] valueStrings = rs.split(",");
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

			getView().setRequest(new XStream(new DomDriver()).toXML(params));
			getView().setResponse(cu.submitBatch(params).toString());
			
		} catch (Exception e) {
			getView().setResponse("FAILED");
		}
	}
	
	private void search() {
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
			getView().setResponse("FAILED");
		}
	}

}

