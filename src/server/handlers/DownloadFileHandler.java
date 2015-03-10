package server.handlers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.*;

import com.sun.net.httpserver.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import shared.communication.*;
import server.AuthException;
import server.ServerException;
import server.ServerFacade;

public class DownloadFileHandler implements HttpHandler {

	private Logger logger = Logger.getLogger("indexer"); 
	
	private XStream xmlStream = new XStream(new DomDriver());	

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		GetSampleImage_Params params = (GetSampleImage_Params)xmlStream.fromXML(exchange.getRequestBody());
		String validateAuth = exchange.getRequestHeaders().getFirst("authorization");
		URL sampleImageUrl = null;
		
		try {
			ServerFacade.validateUser(validateAuth.split(":")[0], validateAuth.split(":")[1]);
			sampleImageUrl = ServerFacade.getSampleImage(params.getProjectId());
		} catch (ServerException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		} catch (AuthException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, -1);
			return;
		}
		
		GetSampleImage_Result result = new GetSampleImage_Result();
		result.setImageUrl(sampleImageUrl);
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, -1);
		xmlStream.toXML(result, exchange.getResponseBody());
		exchange.getResponseBody().close();
	}
}

