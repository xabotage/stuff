package server.database;

import java.util.List;

import org.junit.* ;

import shared.model.*;
import static org.junit.Assert.* ;

public class TestRecordDAO {
	
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
	public void testReadRecord() throws Exception {
		Record createdRecord = new Record();
		createdRecord.setBatchId(22200);
		createdRecord.setRecordNum(1);
		db.getRecordDAO().createRecord(createdRecord);
		Record gotRecord = db.getRecordDAO().readRecord(createdRecord.getRecordId());
		assertEquals(gotRecord.getRecordId(), createdRecord.getRecordId());
		assertEquals(gotRecord.getBatchId(), createdRecord.getBatchId());
		assertEquals(gotRecord.getRecordNum(), createdRecord.getRecordNum());
	}

	@Test
	public void testCreateRecord() throws Exception {
		Record createdRecord = new Record();
		createdRecord.setBatchId(22200);
		createdRecord.setRecordNum(1);
		db.getRecordDAO().createRecord(createdRecord);
		Record gotRecord = db.getRecordDAO().readRecord(createdRecord.getRecordId());
		assertEquals(gotRecord.getRecordId(), createdRecord.getRecordId());
		assertEquals(gotRecord.getBatchId(), createdRecord.getBatchId());
		assertEquals(gotRecord.getRecordNum(), createdRecord.getRecordNum());
	}

	@Test
	public void testReadRecords() throws Exception {
		Record createdRecord1 = new Record();
		createdRecord1.setBatchId(22200);
		createdRecord1.setRecordNum(1);
		Record createdRecord2 = new Record();
		createdRecord2.setBatchId(22200);
		createdRecord2.setRecordNum(2);
		db.getRecordDAO().createRecord(createdRecord1);
		db.getRecordDAO().createRecord(createdRecord2);
		List<Record> gotRecords = db.getRecordDAO().readRecordsForBatch(createdRecord1.getBatchId());
		assertEquals(gotRecords.get(0).getRecordId(), createdRecord1.getRecordId());

		assertEquals(gotRecords.get(1).getRecordId(), createdRecord2.getRecordId());
	}

	@Test
	public void testUpdateRecord() throws Exception {
		Record createdRecord = new Record();
		createdRecord.setBatchId(22200);
		createdRecord.setRecordNum(1);
		db.getRecordDAO().createRecord(createdRecord);
		createdRecord.setRecordNum(201);
		db.getRecordDAO().updateRecord(createdRecord);
		Record gotRecord = db.getRecordDAO().readRecord(createdRecord.getRecordId());
		assertEquals(gotRecord.getRecordId(), createdRecord.getRecordId());
		assertEquals(gotRecord.getRecordNum(), createdRecord.getRecordNum());
	}

	@Test
	public void testDeleteRecord() throws Exception {
		Record createdRecord = new Record();
		createdRecord.setBatchId(22200);
		createdRecord.setRecordNum(1);
		db.getRecordDAO().createRecord(createdRecord);
		db.getRecordDAO().deleteRecord(createdRecord.getRecordId());

		Record gotRecord = db.getRecordDAO().readRecord(createdRecord.getRecordId());
		assertNull(gotRecord);
	}

}

