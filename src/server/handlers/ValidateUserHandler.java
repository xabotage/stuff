package server.handlers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.*;

import com.sun.net.httpserver.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import shared.communication.*;
import shared.model.User;
import server.AuthException;
import server.ServerException;
import server.ServerFacade;

public class ValidateUserHandler implements HttpHandler {

	private Logger logger = Logger.getLogger("indexer"); 
	
	private XStream xmlStream = new XStream(new DomDriver());	

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		ValidateUser_Params params = (ValidateUser_Params)xmlStream.fromXML(exchange.getRequestBody());
		String validateAuth = exchange.getRequestHeaders().getFirst("authorization");
		User gotUser = null;
		
		try {
			gotUser = ServerFacade.validateUser(validateAuth.split(":")[0], validateAuth.split(":")[1]);
		} catch (ServerException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		} catch (AuthException e) {
			ValidateUser_Result result = new ValidateUser_Result();
			result.setUser(gotUser); // returns null
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			xmlStream.toXML(result, exchange.getResponseBody());
			exchange.getResponseBody().close();
			return;
		}
		
		ValidateUser_Result result = new ValidateUser_Result();
		result.setUser(gotUser);
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		xmlStream.toXML(result, exchange.getResponseBody());
		exchange.getResponseBody().close();
	}
}

