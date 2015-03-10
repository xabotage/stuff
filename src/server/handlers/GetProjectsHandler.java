package server.handlers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.logging.*;

import com.sun.net.httpserver.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import shared.communication.*;
import shared.model.Project;
import server.AuthException;
import server.ServerException;
import server.ServerFacade;

public class GetProjectsHandler implements HttpHandler {

	private Logger logger = Logger.getLogger("indexer"); 
	
	private XStream xmlStream = new XStream(new DomDriver());	

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		//GetProjects_Params params = (GetProjects_Params)xmlStream.fromXML(exchange.getRequestBody());
		String validateAuth = exchange.getRequestHeaders().getFirst("authorization");
		List<Project> projects;
		
		try {
			ServerFacade.validateUser(validateAuth.split(":")[0], validateAuth.split(":")[1]);
			projects = ServerFacade.getProjects();
		} catch (ServerException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		} catch (AuthException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, -1);
			return;
		}
		
		GetProjects_Result result = new GetProjects_Result();
		result.setProjects(projects);
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		xmlStream.toXML(result, exchange.getResponseBody());
		exchange.getResponseBody().close();
	}
}

