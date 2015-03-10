package server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import server.database.*;
import shared.model.*;


public class ServerFacade {
	private static final String PROJECT_DATA_PATH = "projectData";

	public static void initialize() throws ServerException {		
		try {
			Database.initialize();		
		}
		catch (DatabaseException e) {
			throw new ServerException(e.getMessage(), e);
		}		
	}
	
	public static User validateUser(String userName, String password) throws ServerException, AuthException {
		Database db = new Database();
		try {
			db.startTransaction();
			User compareUser = db.getUserDAO().readUserWithName(userName);
			assert(compareUser.getUserName().equals(userName));
			if(compareUser.getPassword().equals(password)) {
				return compareUser;
			} else {
				throw new AuthException("User credentials invalid");
			}
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	public static Project downloadBatch(User user, int projectId) throws ServerException {	
		Database db = new Database();
		try {
			db.startTransaction();
			User gotUser = db.getUserDAO().readUserWithName(user.getUserName());
			if(gotUser.getCurrentBatch() != -1) {
				db.endTransaction(false);
				throw new ServerException("User already has a batch assigned");
			}

			Project projectWrapper = db.getProjectDAO().readProject(projectId);
			assert(projectId == projectWrapper.getProjectId());
			projectWrapper.setFields(db.getFieldDAO().readFieldsForProject(projectId));

			List<Batch> batches = db.getBatchDAO().readBatchesForProject(projectId);
			assert(batches.size() > 0);
			for(Batch b : batches) {
				if(!b.isIndexed() && b.getAssignedUser() == -1) {
					db.endTransaction(true);
					projectWrapper.setBatches(new ArrayList<Batch>());
					projectWrapper.getBatches().add(b);
					return projectWrapper;
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

	public static String getSampleImage(int projectId) throws ServerException {	
		Database db = new Database();
		try {
			db.startTransaction();
			List<Batch> batches = db.getBatchDAO().readBatchesForProject(projectId);
			assert(batches.size() > 0);
			String imageUrl = batches.get(0).getImageFile();
			db.endTransaction(true);
			return imageUrl;
		} catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}

	public static void submitBatch(Batch batch, String userName) throws ServerException {	
		Database db = new Database();
		try {
			db.startTransaction();
			User user = db.getUserDAO().readUserWithName(userName);
			Batch fullBatch = db.getBatchDAO().readBatch(batch.getBatchId());
			Project batchProject = db.getProjectDAO().readProject(fullBatch.getProjectId());

			int newIndexedRecords = user.getIndexedRecords();
			newIndexedRecords += batchProject.getRecordsPerImage();
			user.setIndexedRecords(newIndexedRecords);
			user.setCurrentBatch(-1);
			db.getUserDAO().updateUser(user);

			fullBatch.setIndexed(true);
			fullBatch.setAssignedUser(-1);
			db.getBatchDAO().updateBatch(fullBatch);
			
			List<Field> fields = db.getFieldDAO().readFieldsForProject(batchProject.getProjectId());
			db.getRecordDAO().deleteRecordsForBatch(batch.getBatchId());
			for(Record r : batch.getRecords()) {
				assert(r.getBatchId() == batch.getBatchId());
				assert(r.getRecordNum() > 0);
				db.getRecordDAO().createRecord(r);
				for(int i = 0; i < r.getFieldValues().size(); i++) {
					r.getFieldValues().get(i).setFieldId(fields.get(i).getFieldId());
					r.getFieldValues().get(i).setRecordId(r.getRecordId());
					db.getFieldValueDAO().createFieldValue(r.getFieldValues().get(i));
				}
			}
			db.endTransaction(true);
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	public static List<SearchResultObject> search(List<Integer> fieldIds, List<String> values) throws ServerException {	
		Database db = new Database();
		try {
			db.startTransaction();
			List<FieldValue> matches = db.getFieldValueDAO().findMatchingValuesOfFields(values, fieldIds);
			Record r;
			Batch b;
			List<SearchResultObject> searchResults = new ArrayList<SearchResultObject>();
			SearchResultObject sro;
			for(FieldValue m : matches) {
				sro = new SearchResultObject();
				r = db.getRecordDAO().readRecord(m.getRecordId());
				b = db.getBatchDAO().readBatch(r.getBatchId());
				sro.setBatchId(r.getBatchId());
				sro.setImageUrl(new URL(b.getImageFile()));
				sro.setFieldId(m.getFieldId());
				sro.setRecordNumber(r.getRecordNum());
				searchResults.add(sro);
			}
			db.endTransaction(true);
			return searchResults;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		} catch (MalformedURLException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	public static byte[] downloadFile(String fileURI) throws ServerException {
		Path p = Paths.get("./" + PROJECT_DATA_PATH + fileURI);
		try {
			return Files.readAllBytes(p);
		} catch(IOException e) {
			throw new ServerException(e.getMessage(), e);
		}
	}
	

}