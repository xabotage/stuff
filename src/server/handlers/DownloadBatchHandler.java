package server.handlers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.*;

import com.sun.net.httpserver.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import shared.communication.*;
import shared.model.*;
import server.AuthException;
import server.ServerException;
import server.ServerFacade;

public class DownloadBatchHandler implements HttpHandler {

	private Logger logger = Logger.getLogger("indexer"); 
	
	private XStream xmlStream = new XStream(new DomDriver());	

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		DownloadBatch_Params params = (DownloadBatch_Params)xmlStream.fromXML(exchange.getRequestBody());
		String validateAuth = exchange.getRequestHeaders().getFirst("authorization");
		Project gotProject;
		try {
			ServerFacade.validateUser(validateAuth.split(":")[0], validateAuth.split(":")[1]);
			gotProject = ServerFacade.downloadBatch(params.getUserName(), params.getProjectId());
		}
		catch (ServerException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		} catch (AuthException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, -1);
			return;
		}
		
		DownloadBatch_Result result = new DownloadBatch_Result();
		//assert(gotProject.getBatches().size() == 1);
		result.setBatch(gotProject.getBatches().get(0));
		result.setProject(gotProject);
		
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		xmlStream.toXML(result, exchange.getResponseBody());
		exchange.getResponseBody().close();
	}
}

