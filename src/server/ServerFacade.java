package server;

import java.util.*;

import server.database.*;
import shared.model.*;
import server.*;

public class ServerFacade {

	public static void initialize() throws ServerException {		
		try {
			Database.initialize();		
		}
		catch (DatabaseException e) {
			throw new ServerException(e.getMessage(), e);
		}		
	}
	
	public static boolean validateUser(User user) throws ServerException {
		Database db = new Database();
		try {
			boolean result;
			db.startTransaction();
			User compareUser = db.getUserDAO().readUserWithName(user.getUserName());
			assert(compareUser.getUserName() == user.getUserName());
			if(compareUser.getPassword() == user.getPassword()) {
				result = true;
			} else {
				result = false;
			}
			return result;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	public static Batch downloadBatch(int batchId) throws ServerException {	
		Database db = new Database();
		try {
			db.startTransaction();
			Batch batch = db.getBatchDAO().readBatch(batchId);
			db.endTransaction(true);
			return batch;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}

	public static List<Project> getProjects() throws ServerException {	
		Database db = new Database();
		try {
			db.startTransaction();
			List<Project> projects = db.getProjectDAO().readProjects();
			db.endTransaction(true);
			return projects;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	public static List<Field> getFields(int projectId) throws ServerException {	
		Database db = new Database();
		try {
			db.startTransaction();
			List<Field> fields = db.getFieldDAO().readFieldsForProject(projectId);
			db.endTransaction(true);
			return fields;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}

	public static Batch getSampleImage(String imageFile) throws ServerException {	
		Database db = new Database();
		try {
			db.startTransaction();
			Batch batch = db.getBatchDAO().readBatch(batchId);
			db.endTransaction(true);
			return batch;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}


	public static void addContact(Contact contact) throws ServerException {

		Database db = new Database();
		
		try {
			db.startTransaction();
			db.getContactsDAO().add(contact);
			db.endTransaction(true);
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	public static void updateContact(Contact contact) throws ServerException {

		Database db = new Database();
		
		try {
			db.startTransaction();
			db.getContactsDAO().update(contact);
			db.endTransaction(true);
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	public static void deleteContact(Contact contact) throws ServerException {

		Database db = new Database();
		
		try {
			db.startTransaction();
			db.getContactsDAO().delete(contact);
			db.endTransaction(true);
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}

}