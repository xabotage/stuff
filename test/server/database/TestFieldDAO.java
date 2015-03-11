package server.database;

import java.util.List;

import org.junit.* ;

import shared.model.*;
import static org.junit.Assert.* ;

public class TestFieldDAO {
	
	@BeforeClass
	public static void classSetup() throws Exception {
		Database.initialize();
	}
	
	private Database db;
	
	@Before
	public void setup() throws Exception {
		db = new Database();
		db.startTransaction();
		List<Field> fields = db.getFieldDAO().readFields();
		
		for (Field f : fields) {
			db.getFieldDAO().deleteField(f.getFieldId());
		}
		
		db.endTransaction(true);

		// Prepare database for test case	
		db = new Database();
		db.startTransaction();
	}
	
	@After
	public void teardown() {
		// Roll back transaction so changes to database are undone
		db.endTransaction(false);
		db = null;
	}
	
	@Test
	public void testReadField() throws Exception {
		Field createdField = new Field();
		createdField.setProjectId(22200);
		createdField.setTitle("jim");
		db.getFieldDAO().createField(createdField);
		Field gotField = db.getFieldDAO().readField(createdField.getFieldId());
		assertEquals(gotField.getFieldId(), createdField.getFieldId());
		assertEquals(gotField.getTitle(), createdField.getTitle());
	}

	@Test
	public void testCreateField() throws Exception {
		Field createdField = new Field();
		createdField.setProjectId(22200);
		createdField.setTitle("jim");
		db.getFieldDAO().createField(createdField);
		Field gotField = db.getFieldDAO().readField(createdField.getFieldId());
		assertEquals(gotField.getFieldId(), createdField.getFieldId());
		assertEquals(gotField.getTitle(), createdField.getTitle());
	}


	@Test
	public void testReadFields() throws Exception {
		Field createdField1 = new Field();
		createdField1.setProjectId(22200);
		createdField1.setTitle("jim");
		Field createdField2 = new Field();
		createdField1.setProjectId(22200);
		createdField1.setTitle("jim");
		db.getFieldDAO().createField(createdField1);
		db.getFieldDAO().createField(createdField2);
		List<Field> gotFields = db.getFieldDAO().readFields();
		assertEquals(gotFields.get(0).getFieldId(), createdField1.getFieldId());
		assertEquals(gotFields.get(1).getFieldId(), createdField2.getFieldId());
	}

	@Test
	public void testReadProjectFields() throws Exception {
		Field createdField1 = new Field();
		createdField1.setProjectId(22200);
		createdField1.setTitle("jim");
		Field createdField2 = new Field();
		createdField2.setProjectId(createdField1.getProjectId());
		createdField2.setTitle("bo");
		db.getFieldDAO().createField(createdField1);
		db.getFieldDAO().createField(createdField2);
		List<Field> gotFields = db.getFieldDAO().readFieldsForProject(createdField1.getProjectId());
		assertEquals(gotFields.get(0).getTitle(), createdField1.getTitle());
		assertEquals(gotFields.get(1).getTitle(), createdField2.getTitle());
	}

	@Test
	public void testUpdateField() throws Exception {
		Field createdField = new Field();
		createdField.setProjectId(22200);
		createdField.setTitle("Jim");
		db.getFieldDAO().createField(createdField);
		createdField.setTitle("Bo");
		db.getFieldDAO().updateField(createdField);
		Field gotField = db.getFieldDAO().readField(createdField.getFieldId());
		assertEquals(gotField.getFieldId(), createdField.getFieldId());
		assertEquals(gotField.getTitle(), createdField.getTitle());
	}

	@Test
	public void testDeleteField() throws Exception {
		Field createdField = new Field();
		createdField.setProjectId(22200);
		createdField.setTitle("Jim");
		db.getFieldDAO().createField(createdField);
		db.getFieldDAO().deleteField(createdField.getFieldId());

		Field gotField = db.getFieldDAO().readField(createdField.getFieldId());
		assertNull(gotField);
	}

}

