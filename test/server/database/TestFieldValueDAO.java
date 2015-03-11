package server.database;

import java.util.ArrayList;
import java.util.List;

import org.junit.* ;

import shared.model.*;
import static org.junit.Assert.* ;

public class TestFieldValueDAO {
	
	@BeforeClass
	public static void classSetup() throws Exception {
		Database.initialize();
	}
	
	private Database db;
	
	@Before
	public void setup() throws Exception {
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
	public void testReadFieldValue() throws Exception {
		FieldValue createdFieldValue = new FieldValue();
		createdFieldValue.setRecordId(22200);
		createdFieldValue.setFieldId(33300);
		db.getFieldValueDAO().createFieldValue(createdFieldValue);
		FieldValue gotFieldValue = db.getFieldValueDAO().readFieldValue(createdFieldValue.getValueId());
		assertEquals(gotFieldValue.getValueId(), createdFieldValue.getValueId());
		assertEquals(gotFieldValue.getFieldId(), createdFieldValue.getFieldId());
	}

	@Test
	public void testCreateFieldValue() throws Exception {
		FieldValue createdFieldValue = new FieldValue();
		createdFieldValue.setRecordId(22200);
		createdFieldValue.setFieldId(33300);
		db.getFieldValueDAO().createFieldValue(createdFieldValue);
		FieldValue gotFieldValue = db.getFieldValueDAO().readFieldValue(createdFieldValue.getValueId());
		assertEquals(gotFieldValue.getValueId(), createdFieldValue.getValueId());
		assertEquals(gotFieldValue.getFieldId(), createdFieldValue.getFieldId());
	}

	@Test
	public void testUpdateFieldValue() throws Exception {
		FieldValue createdFieldValue = new FieldValue();
		createdFieldValue.setRecordId(22200);
		createdFieldValue.setFieldId(33300);
		db.getFieldValueDAO().createFieldValue(createdFieldValue);
		createdFieldValue.setFieldId(32300);
		db.getFieldValueDAO().updateFieldValue(createdFieldValue);
		FieldValue gotFieldValue = db.getFieldValueDAO().readFieldValue(createdFieldValue.getValueId());
		assertEquals(gotFieldValue.getValueId(), createdFieldValue.getValueId());
		assertEquals(gotFieldValue.getFieldId(), createdFieldValue.getFieldId());
	}

	@Test
	public void testDeleteFieldValue() throws Exception {
		FieldValue createdFieldValue = new FieldValue();
		createdFieldValue.setRecordId(22200);
		createdFieldValue.setFieldId(33300);
		db.getFieldValueDAO().createFieldValue(createdFieldValue);
		db.getFieldValueDAO().deleteFieldValue(createdFieldValue.getValueId());

		FieldValue gotFieldValue = db.getFieldValueDAO().readFieldValue(createdFieldValue.getValueId());
		assertNull(gotFieldValue);
	}

	@Test
	public void testSearchFieldValue() throws Exception {
		FieldValue createdFieldValue = new FieldValue();
		createdFieldValue.setRecordId(22200);
		createdFieldValue.setFieldId(33300);
		createdFieldValue.setValue("Jimbo");
		db.getFieldValueDAO().createFieldValue(createdFieldValue);
		List<String> searchVals = new ArrayList<String>();
		searchVals.add(createdFieldValue.getValue());
		List<Integer> searchFields = new ArrayList<Integer>();
		searchFields.add(createdFieldValue.getFieldId());
		FieldValue gotFieldValue = db.getFieldValueDAO().findMatchingValuesOfFields(searchVals, searchFields).get(0);
		assertEquals(gotFieldValue.getValueId(), createdFieldValue.getValueId());
		assertEquals(gotFieldValue.getFieldId(), createdFieldValue.getFieldId());
	}


}

