package server.database;

import java.util.List;
import java.util.ArrayList;
import org.w3c.dom.*;

import shared.model.*;


public class IndexerData {
	private List<User> users;
	private List<Project> projects;
	private Database db;
	private List<Field> currentProjectFields;
	
	public IndexerData(Element rootElement) throws DatabaseException {
		Database.initialize();
		db = new Database();
		users = new ArrayList<User>();
		projects = new ArrayList<Project>();
		currentProjectFields = new ArrayList<Field>();
		ArrayList<Element> rootElements = DataImporter.getChildElements(rootElement);
		ArrayList<Element> userElements =
		DataImporter.getChildElements(rootElements.get(0));
		for(Element userElement : userElements) {
			users.add(new User(userElement));
		}
		ArrayList<Element> projectElements = DataImporter.getChildElements(rootElements.get(1));
		for(Element projectElement : projectElements) {
			projects.add(new Project(projectElement));
		}
	}
	
	public void populateDatabase() throws DatabaseException {
		for(Project p : projects) {
			db.getProjectDAO().createProject(p);
			createFieldsForProject(p);
			createBatchesForProject(p);
		}
		createUsers();
	}
	
	private void createUsers() throws DatabaseException {
		for(User u : users) {
			db.getUserDAO().createUser(u);
		}
	}
	
	private void createFieldsForProject(Project p) throws DatabaseException {
		currentProjectFields = new ArrayList<Field>();
		for(Field f : p.getFields()) {
			f.setProjectId(p.getProjectId());
			db.getFieldDAO().createField(f);
			currentProjectFields.add(f);
		}
	}
	
	private void createBatchesForProject(Project p) throws DatabaseException {
		for(Batch b : p.getBatches()) {
			b.setProjectId(p.getProjectId());
			db.getBatchDAO().createBatch(b);
			createRecordsForBatch(b);
		}
	}
	
	private void createRecordsForBatch(Batch b) throws DatabaseException {
		for(Record r : b.getRecords()) {
			r.setBatchId(b.getBatchId());
			db.getRecordDAO().createRecord(r);
			createFieldValuesForRecord(r);
		}
	}
	
	private void createFieldValuesForRecord(Record r) throws DatabaseException {
		int i = 0;
		for(FieldValue fv : r.getFieldValues()) {
			fv.setRecordId(r.getRecordId());
			fv.setFieldId(r.getFieldValues().get(i).getFieldId());
			// we're definitely making some sort of dumb assumption here 
			// double check that fv and field match
			//assert(?);
			db.getFieldValueDAO().createFieldValue(fv);
			i++;
		}
	}

}
