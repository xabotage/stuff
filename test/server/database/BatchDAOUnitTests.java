package server.database;

import java.util.List;

import org.junit.* ;

import shared.model.*;
import server.database.*;
import static org.junit.Assert.* ;

public class BatchDAOUnitTests {
	
	@BeforeClass
	public static void classSetup() throws Exception {
		Database.initialize();
	}
	
	private Database db;
	
	@Before
	public void setup() throws Exception {
		db = new Database();
		db.startTransaction();
		List<Batch> users = db.getBatchDAO().readBatchesForProject(-1);
		
		for (Batch u : users) {
			db.getBatchDAO().deleteBatch(u.getBatchId());
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
	public void testReadBatch() throws Exception {
		Batch createdBatch = new Batch(-1, 100, "image.png", false, 200);
		db.getBatchDAO().createBatch(createdBatch);
		Batch gotBatch = db.getBatchDAO().readBatch(createdBatch.getBatchId());
		assertEquals(gotBatch.getBatchId(), createdBatch.getBatchId());
		assertEquals(gotBatch.getProjectId(), createdBatch.getProjectId());
		assertEquals(gotBatch.getImageFile(), createdBatch.getImageFile());
		assertEquals(gotBatch.isIndexed(), createdBatch.isIndexed());
		assertEquals(gotBatch.getAssignedUser(), createdBatch.getAssignedUser());
	}

	@Test
	public void testReadBatches() throws Exception {
		Batch createdBatch1 = new Batch(-1, 100, "image1.png", false, 200);
		Batch createdBatch2 = new Batch(-1, 100, "image2.png", true, 201);
		db.getBatchDAO().createBatch(createdBatch1);
		db.getBatchDAO().createBatch(createdBatch2);
		List<Batch> gotBatches = db.getBatchDAO().readBatchesForProject(createdBatch1.getProjectId());
		assertEquals(gotBatches.get(0).getBatchId(), createdBatch1.getBatchId());
		assertEquals(gotBatches.get(0).getProjectId(), createdBatch1.getProjectId());
		assertEquals(gotBatches.get(0).getImageFile(), createdBatch1.getImageFile());
		assertEquals(gotBatches.get(0).isIndexed(), createdBatch1.isIndexed());
		assertEquals(gotBatches.get(0).getAssignedUser(), createdBatch1.getAssignedUser());

		assertEquals(gotBatches.get(1).getBatchId(), createdBatch2.getBatchId());
		assertEquals(gotBatches.get(1).getProjectId(), createdBatch2.getProjectId());
		assertEquals(gotBatches.get(1).getImageFile(), createdBatch2.getImageFile());
		assertEquals(gotBatches.get(1).isIndexed(), createdBatch2.isIndexed());
		assertEquals(gotBatches.get(1).getAssignedUser(), createdBatch2.getAssignedUser());
	}

	@Test
	public void testUpdateBatch() throws Exception {
		Batch createdBatch = new Batch(-1, 100, "image.png", false, 200);
		db.getBatchDAO().createBatch(createdBatch);
		createdBatch.setProjectId(101);
		createdBatch.setImageFile("image1.png");
		createdBatch.setIndexed(true);
		createdBatch.setAssignedUser(201);
		db.getBatchDAO().updateBatch(createdBatch);
		Batch gotBatch = db.getBatchDAO().readBatch(createdBatch.getBatchId());
		assertEquals(gotBatch.getBatchId(), createdBatch.getBatchId());
		assertEquals(gotBatch.getProjectId(), createdBatch.getProjectId());
		assertEquals(gotBatch.getImageFile(), createdBatch.getImageFile());
		assertEquals(gotBatch.isIndexed(), createdBatch.isIndexed());
		assertEquals(gotBatch.getAssignedUser(), createdBatch.getAssignedUser());
	}

	@Test
	public void testDeleteBatch() throws Exception {
		Batch createdBatch1 = new Batch(-1, 100, "image.png", false, 200);
		Batch createdBatch2 = new Batch(-1, 100, "image1.png", true, 201);
		Batch createdBatch3 = new Batch(-1, 100, "image2.png", true, 202);
		db.getBatchDAO().createBatch(createdBatch1);
		db.getBatchDAO().createBatch(createdBatch2);
		db.getBatchDAO().createBatch(createdBatch3);
		db.getBatchDAO().deleteBatch(createdBatch1.getBatchId());

		Batch gotBatch = db.getBatchDAO().readBatch(createdBatch1.getBatchId());
		assertNull(gotBatch);

		List<Batch> batches = db.getBatchDAO().readBatchesForProject(100);
		for (Batch b : batches) {
			db.getBatchDAO().deleteBatch(b.getBatchId());
		}
		batches = db.getBatchDAO().readBatchesForProject(100);
		assertEquals(0, batches.size());
	}

}

