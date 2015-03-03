package server.database;

import java.io.*;
import java.sql.*;
import java.util.logging.*;


public class Database {
	
	private static final String DATABASE_DIRECTORY = "database";
	private static final String DATABASE_FILE = "indexer_db.sqlite";
	private static final String DATABASE_URL = "jdbc:sqlite:" + DATABASE_DIRECTORY +
												File.separator + DATABASE_FILE;

	private static Logger logger;
	
	static {
		logger = Logger.getLogger("contactmanager");
	}

	public static void initialize() throws DatabaseException {
		try {
			final String driver = "org.sqlite.JDBC";
			Class.forName(driver);
		}
		catch(ClassNotFoundException e) {
			
			DatabaseException serverEx = new DatabaseException("Could not load database driver", e);
			
			logger.throwing("server.database.Database", "initialize", serverEx);

			throw serverEx; 
		}
	}

	private Connection connection;
	private UserDAO userDAO;
	private BatchDAO batchDAO;
	private FieldDAO fieldDAO;
	private FieldValueDAO fieldValueDAO;
	private ProjectDAO projectDAO;
	private RecordDAO recordDAO;
	
	public Database() {
		connection = null;
		userDAO = new UserDAO(this);
		batchDAO = new BatchDAO(this);
		projectDAO = new ProjectDAO(this);
	}
	
	/**
	 * @return the userDAO
	 */
	public UserDAO getUserDAO() {
		return userDAO;
	}

	/**
	 * @return the batchDAO
	 */
	public BatchDAO getBatchDAO() {
		return batchDAO;
	}

	/**
	 * @return the fieldDAO
	 */
	public FieldDAO getFieldDAO() {
		return fieldDAO;
	}

	/**
	 * @return the fieldValueDAO
	 */
	public FieldValueDAO getFieldValueDAO() {
		return fieldValueDAO;
	}

	/**
	 * @return the projectDAO
	 */
	public ProjectDAO getProjectDAO() {
		return projectDAO;
	}

	/**
	 * @return the recordDAO
	 */
	public RecordDAO getRecordDAO() {
		return recordDAO;
	}

	public Connection getConnection() {
		return connection;
	}

	public void startTransaction() throws DatabaseException {
		try {
			assert (connection == null);			
			connection = DriverManager.getConnection(DATABASE_URL);
			connection.setAutoCommit(false);
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not connect to database. Make sure " + 
				DATABASE_FILE + " is available in ./" + DATABASE_DIRECTORY, e);
		}
	}
	
	public void endTransaction(boolean commit) {
		if (connection != null) {		
			try {
				if (commit) {
					connection.commit();
				}
				else {
					connection.rollback();
				}
			}
			catch (SQLException e) {
				System.out.println("Could not end transaction");
				e.printStackTrace();
			}
			finally {
				safeClose(connection);
				connection = null;
			}
		}
	}
	
	public static void safeClose(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			}
			catch (SQLException e) {
				// ...
			}
		}
	}
	
	public static void safeClose(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			}
			catch (SQLException e) {
				// ...
			}
		}
	}
	
	public static void safeClose(PreparedStatement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			}
			catch (SQLException e) {
				// ...
			}
		}
	}
	
	public static void safeClose(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			}
			catch (SQLException e) {
				// ...
			}
		}
	}
	
}
