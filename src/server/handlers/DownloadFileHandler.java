package server.handlers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.*;

import com.sun.net.httpserver.*;

import server.ServerException;
import server.ServerFacade;

public class DownloadFileHandler implements HttpHandler {

	private Logger logger = Logger.getLogger("indexer"); 
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		byte[] fileData;
		try {
			fileData = ServerFacade.downloadFile(exchange.getRequestURI().toString());
		} catch (ServerException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		}
		
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, fileData.length);
		exchange.getResponseBody().write(fileData);
		exchange.getResponseBody().close();
	}
}

