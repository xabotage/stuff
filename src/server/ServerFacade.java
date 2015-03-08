package server;

import java.net.MalformedURLException;
import java.net.URL;
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
	
	public static Batch downloadBatch(User user, int projectId) throws ServerException {	
		Database db = new Database();
		try {
			db.startTransaction();
			User gotUser = db.getUserDAO().readUserWithName(user.getUserName());
			if(gotUser.getCurrentBatch() != -1) {
				db.endTransaction(false);
				throw new ServerException("User already has a batch assigned");
			}
			List<Batch> batches = db.getBatchDAO().readBatchesForProject(projectId);
			assert(batches.size() > 0);
			for(Batch b : batches) {
				if(!b.isIndexed()) {
					db.endTransaction(true);
					return b;
				}
			}
			db.endTransaction(false);
			throw new ServerException("No unindexed batches found");
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
			List<Field> fields;
			if(projectId == -1) {
				fields = db.getFieldDAO().readFields();
			} else {
				fields = db.getFieldDAO().readFieldsForProject(projectId);
			}
			db.endTransaction(true);
			return fields;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}

	public static URL getSampleImage(int projectId) throws ServerException {	
		Database db = new Database();
		try {
			db.startTransaction();
			List<Batch> batches = db.getBatchDAO().readBatchesForProject(projectId);
			assert(batches.size() > 0);
			URL imageUrl = new URL(batches.get(0).getImageFile());
			db.endTransaction(true);
			return imageUrl;
		} catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		} catch (MalformedURLException e) {
			db.endTransaction(false);
			e.printStackTrace();
			throw new ServerException(e.getMessage(), e);
		}
	}

	public static void submitBatch(User user, Batch batch) throws ServerException {	
		Database db = new Database();
		try {
			db.startTransaction();
			user = db.getUserDAO().readUserWithName(user.getUserName());
			int newIndexedRecords = user.getIndexedRecords();
			newIndexedRecords += db.getProjectDAO().readProject(batch.getProjectId()).getRecordsPerImage();
			user.setIndexedRecords(newIndexedRecords);
			user.setCurrentBatch(-1);
			db.getUserDAO().updateUser(user);

			batch.setIndexed(true);
			db.getBatchDAO().updateBatch(batch);
			db.endTransaction(true);
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	public static List<SearchResultObject> search(List<Field> fields, List<FieldValue> values) throws ServerException {	
		Database db = new Database();
		try {
			db.startTransaction();
			List<FieldValue> matches = db.getFieldValueDAO().findValuesOfFieldsMatching(values, fields);
			Record r;
			Batch b;
			List<SearchResultObject> searchResults = new ArrayList<SearchResultObject>();
			for(FieldValue m : matches) {
				r = db.getRecordDAO().readRecord(m.getRecordId());
				b = db.getBatchDAO().readBatch(r.getBatchId());
			}
			db.endTransaction(true);
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