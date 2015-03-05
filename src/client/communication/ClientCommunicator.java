package client.communication;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import shared.communication.*;

/**
 * Communicator class for the client
 * @author phelpsdb
 *
 */
public class ClientCommunicator {
	/**
	 * The domain name, port, and framework. 'http://stuff.com:8080/'
	 */
	private String urlBase;
	
	public ClientCommunicator() {
	}
	
	public ClientCommunicator(String domainHost, int port) {
		this.urlBase = domainHost + ":" + Integer.toString(port);
	}

	/**
	 * Verify the username and password
	 * @param params Object containing user credentials
	 * @return the result of the query
	 */
	public ValidateUser_Result validateUser(ValidateUser_Params params) {
		return null;
	}

	/**
	 * Get all available projects
	 * @param params parameters for querying all projects
	 * @return a result object containing the projects
	 */
	public GetProjects_Result getProjects(GetProjects_Params params) {
		return null;
	}

	/**
	 * Obtain a sample image from the database for a project
	 * @param params parameter object
	 * @return result object containing retrieved image
	 */
	public GetSampleImage_Result getSampleImage(GetSampleImage_Params params) {
		return null;
	}

	/**
	 * Download a batch from the server
	 * @param params parameter object specifying which batch
	 * @return result object containing retrieved batch
	 */
	public DownloadBatch_Result downloadBatch(DownloadBatch_Params params) {
		return null;
	}

	/**
	 * Submit an indexed batch to the server
	 * @param params parameter object containing the batch
	 * @return result object 
	 */
	public SubmitBatch_Result submitBatch(SubmitBatch_Params params) {
		return null;
	}

	/**
	 * Get the fields for a project
	 * @param params parameters specifying which project to retrieve fields for
	 * @return a result object containing the fields
	 */
	public GetFields_Result getFields(GetFields_Params params) {
		return null;
	}

	/**
	 * Search the field values in the database for a given value
	 * @param params specifies what to search
	 * @return the results obtained from the search
	 */
	public Search_Result search(Search_Params params) {
		return null;
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
	 * @return
	 */
	public Object post(String urlPath, Object data) throws IOException {
		XStream xstream = new XStream(new DomDriver());
		URL url = new URL(urlBase + urlPath); // TODO: urlBase should be set by this class's constructor and should have http://stuff.com:8080
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.connect();
		xstream.toXML(data, conn.getOutputStream());
		conn.disconnect();
		if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
			Object result = xstream.fromXML(conn.getInputStream());
			return result;
		}
		return null;
	}
}
