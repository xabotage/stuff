package client.communication;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import shared.communication.*;
import client.ClientException;

/**
 * Communicator class for the client
 * @author phelpsdb
 *
 */
public class ClientCommunicator {
	private static final String HTTP_GET = "GET";
	private static final String HTTP_POST = "POST";

	/**
	 * The domain name, port, and framework. 'http://stuff.com:8080/'
	 */
	private String urlBase;
	
	public ClientCommunicator() {
	}
	
	public ClientCommunicator(String domainHost, int port) {
		this.urlBase = "http://" + domainHost + ":" + Integer.toString(port);
	}

	/**
	 * Verify the username and password
	 * @param params Object containing user credentials
	 * @return the result of the query
	 */
	public ValidateUser_Result validateUser(ValidateUser_Params params) throws ClientException {
		return (ValidateUser_Result) post("/validateUser", params);
	}

	/**
	 * Get all available projects
	 * @param params parameters for querying all projects
	 * @return a result object containing the projects
	 */
	public GetProjects_Result getProjects(GetProjects_Params params) throws ClientException {
		return (GetProjects_Result) get("/getProjects", params);
	}

	/**
	 * Obtain a sample image from the database for a project
	 * @param params parameter object
	 * @return result object containing retrieved image
	 */
	public GetSampleImage_Result getSampleImage(GetSampleImage_Params params) throws ClientException {
		return (GetSampleImage_Result) post("/getSampleImage", params);
	}

	/**
	 * Download a batch from the server
	 * @param params parameter object specifying which batch
	 * @return result object containing retrieved batch
	 */
	public DownloadBatch_Result downloadBatch(DownloadBatch_Params params) throws ClientException {
		return (DownloadBatch_Result) post("/downloadBatch", params);
	}

	/**
	 * Submit an indexed batch to the server
	 * @param params parameter object containing the batch
	 * @return result object 
	 */
	public SubmitBatch_Result submitBatch(SubmitBatch_Params params) throws ClientException {
		return (SubmitBatch_Result) post("/submitBatch", params);
	}

	/**
	 * Get the fields for a project
	 * @param params parameters specifying which project to retrieve fields for
	 * @return a result object containing the fields
	 */
	public GetFields_Result getFields(GetFields_Params params) throws ClientException {
		return (GetFields_Result) post("/getFields", params);
	}

	/**
	 * Search the field values in the database for a given value
	 * @param params specifies what to search
	 * @return the results obtained from the search
	 */
	public Search_Result search(Search_Params params) throws ClientException {
		return (Search_Result) post("/search", params);
	}

	/**
	 * Download a file from the server
	 * @param params parameter object
	 * @return result object
	 */
	public DownloadFile_Result downloadFile(DownloadFile_Params params) {
		return null;
	}
	
	/**
	 * @param urlPath A path such as '/validateUser' or '/getProjects'
	 * @param data The _Params object to send to the server
	 * @return the result object of the transaction
	 */
	public Object post(String urlPath, Params params) throws ClientException {
		try {
			XStream xstream = new XStream(new DomDriver());
			URL url = new URL(urlBase + urlPath); 
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod(HTTP_POST);
			conn.addRequestProperty("authorization", params.getUserName() + ":" + params.getPassword());
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.connect();
			xstream.toXML(params, conn.getOutputStream());
			conn.getOutputStream().close();
			if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				Object result = xstream.fromXML(conn.getInputStream());
				return result;
			} else {
				throw new ClientException(String.format("post failed: %s (http code %d)",
						urlPath, conn.getResponseCode()));
			}
		} catch(IOException e) {
			throw new ClientException(String.format("post failed: %s", e.getMessage()), e);
		}
	}
	
	/**
	 * @param urlPath A path such as '/validateUser' or '/getProjects'
	 * @param data The _Params object to send to the server
	 * @return the result object of the transaction
	 */
	public Object get(String urlPath, Params params) throws ClientException {
		try {
			XStream xstream = new XStream(new DomDriver());
			URL url = new URL(urlBase + urlPath); // TODO: urlBase should be set by this class's constructor and should have http://stuff.com:8080
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod(HTTP_GET);
			conn.addRequestProperty("authorization", params.getUserName() + ":" + params.getPassword());
			conn.setDoInput(true);
			conn.connect();
			if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				Object result = xstream.fromXML(conn.getInputStream());
				return result;
			} else {
				throw new ClientException(String.format("get failed: %s (http code %d)",
						urlPath, conn.getResponseCode()));
			}
		} catch(IOException e) {
			throw new ClientException(String.format("get failed: %s", e.getMessage()), e);
		}
	}
}