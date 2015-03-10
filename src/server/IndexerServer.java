package server;

import java.io.*;
import java.net.*;
import java.util.logging.*;

import com.sun.net.httpserver.*;

import server.handlers.*;

public class IndexerServer {

	private static final int SERVER_PORT_NUMBER = 8989;
	private static final int MAX_WAITING_CONNECTIONS = 10;
	
	private static Logger logger;
	
	static {
		try {
			initLog();
		}
		catch (IOException e) {
			System.out.println("Could not initialize log: " + e.getMessage());
		}
	}
	
	private static void initLog() throws IOException {
		
		Level logLevel = Level.FINE;
		
		logger = Logger.getLogger("indexer"); 
		logger.setLevel(logLevel);
		logger.setUseParentHandlers(false);
		
		Handler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(logLevel);
		consoleHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(consoleHandler);

		FileHandler fileHandler = new FileHandler("log.txt", false);
		fileHandler.setLevel(logLevel);
		fileHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(fileHandler);
	}

	
	private HttpServer server;
	
	private IndexerServer() {
		return;
	}
	
	private void run(int portNum) {
		
		logger.info("Initializing Model");
		
		portNum = (portNum == -1) ? SERVER_PORT_NUMBER : portNum;
		
		try {
			ServerFacade.initialize();		
		}
		catch (ServerException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			return;
		}
		
		logger.info("Initializing HTTP Server");
		
		try {
			server = HttpServer.create(new InetSocketAddress(portNum),
											MAX_WAITING_CONNECTIONS);
		} 
		catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);			
			return;
		}

		server.setExecutor(null); // use the default executor
		
		server.createContext("/validateUser", vuHandler);
		server.createContext("/downloadBatch", dbHandler);
		server.createContext("/getFields", gfHandler);
		server.createContext("/getProjects", gpHandler);
		server.createContext("/getSampleImage", gsiHandler);
		server.createContext("/search", searchHandler);
		server.createContext("/submitBatch", sbHandler);
		server.createContext("/", dfHandler);
		
		logger.info("Starting HTTP Server");

		server.start();
	}

	private HttpHandler vuHandler = new ValidateUserHandler();
	private HttpHandler dbHandler = new DownloadBatchHandler();
	private HttpHandler gfHandler = new GetFieldsHandler();
	private HttpHandler gpHandler = new GetProjectsHandler();
	private HttpHandler gsiHandler = new GetSampleImageHandler();
	private HttpHandler searchHandler = new SearchHandler();
	private HttpHandler sbHandler = new SubmitBatchHandler();
	private HttpHandler dfHandler = new DownloadFileHandler();
	
	public static void main(String[] args) {
		if(args.length > 0) {
			new IndexerServer().run(Integer.parseInt(args[0]));
		} else {
			new IndexerServer().run(-1);
		}
	}

}