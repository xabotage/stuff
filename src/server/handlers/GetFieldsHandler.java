package server.handlers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.logging.*;

import com.sun.net.httpserver.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import shared.communication.*;
import shared.model.*;
import server.AuthException;
import server.ServerException;
import server.ServerFacade;

public class GetFieldsHandler implements HttpHandler {

	private Logger logger = Logger.getLogger("indexer"); 
	
	private XStream xmlStream = new XStream(new DomDriver());	

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		GetFields_Params params = (GetFields_Params)xmlStream.fromXML(exchange.getRequestBody());
		String validateAuth = exchange.getRequestHeaders().getFirst("authorization");
		List<Field> fields = null;
		
		try {
			ServerFacade.validateUser(validateAuth.split(":")[0], validateAuth.split(":")[1]);
			fields = ServerFacade.getFields(params.getProjectId());
		} catch (ServerException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		} catch (AuthException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_FORBIDDEN, -1);
			return;
		}
		
		GetFields_Result result = new GetFields_Result();
		result.setFields(fields);
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, -1);
		xmlStream.toXML(result, exchange.getResponseBody());
		exchange.getResponseBody().close();
	}
}

