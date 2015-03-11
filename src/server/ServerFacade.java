package server;

import java.io.IOException;
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
			if(compareUser != null && compareUser.getPassword().equals(password)) {
				//assert(compareUser.getUserName().equals(userName));
				db.endTransaction(true);
				return compareUser;
			} else {
				db.endTransaction(false);
				throw new AuthException("User credentials invalid");
			}
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	public static Project downloadBatch(String userName, int projectId) throws ServerException {	
		Database db = new Database();
		try {
			db.startTransaction();
			User gotUser = db.getUserDAO().readUserWithName(userName);
			if(gotUser.getCurrentBatch() != -1) {
				db.endTransaction(false);
				throw new ServerException("User already has a batch assigned");
			}
			Project projectWrapper = db.getProjectDAO().readProject(projectId);
			//assert(projectId == projectWrapper.getProjectId());
			projectWrapper.setFields(db.getFieldDAO().readFieldsForProject(projectId));

			List<Batch> batches = db.getBatchDAO().readBatchesForProject(projectId);
			//assert(batches.size() > 0);
			db.endTransaction(true);
			
			db.startTransaction();
			for(Batch b : batches) {
				if(!b.isIndexed() && b.getAssignedUser() == -1) {
					b.setAssignedUser(gotUser.getUserId());
					db.getBatchDAO().updateBatch(b);
					db.endTransaction(true);

					db.startTransaction();
					gotUser.setCurrentBatch(b.getBatchId());
					db.getUserDAO().updateUser(gotUser);
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
			List<Field> fields = new ArrayList<Field>();
			if(projectId == -1) {
				fields = db.getFieldDAO().readFields();
			} else {
				fields = db.getFieldDAO().readFieldsForProject(projectId);
			}
			if(fields.size() == 0) {
				throw new DatabaseException("invalid project id");
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
			if(batches.size() == 0) {
				db.endTransaction(false);
				throw new ServerException("No Batches exist for project: " + projectId);
			}
			//assert(batches.size() > 0);
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
			db.endTransaction(true);
			db.startTransaction();
			Batch fullBatch = db.getBatchDAO().readBatch(batch.getBatchId());
			Project batchProject = db.getProjectDAO().readProject(fullBatch.getProjectId());
			if(user.getCurrentBatch() == -1 || fullBatch.getAssignedUser() == -1 || 
					user.getUserId() != fullBatch.getAssignedUser()) {
				db.endTransaction(false);
				throw new ServerException("Batch was not assigned to this user");
			}
			int newIndexedRecords = user.getIndexedRecords();
			newIndexedRecords += batchProject.getRecordsPerImage();
			user.setIndexedRecords(newIndexedRecords);
			user.setCurrentBatch(-1);
			db.getUserDAO().updateUser(user);

			db.endTransaction(true);
			db.startTransaction();

			fullBatch.setIndexed(true);
			fullBatch.setAssignedUser(-1);
			db.getBatchDAO().updateBatch(fullBatch);
			
			List<Field> fields = db.getFieldDAO().readFieldsForProject(batchProject.getProjectId());
			for(Record r : batch.getRecords()) {
				//assert(r.getBatchId() == batch.getBatchId());
				//assert(r.getRecordNum() > 0);
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
				sro.setImageUrl(b.getImageFile());
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