package client.communication;

import java.util.ArrayList;
import java.util.List;

import org.junit.* ;

import client.ClientException;
import server.database.Database;
import shared.communication.*;
import shared.model.*;
import static org.junit.Assert.* ;

public class TestClientCommunicator {
	
	@BeforeClass
	public static void classSetup() throws Exception {
		Database.initialize();
	}

	@AfterClass
	public static void classTeardown() throws Exception {
	}
	
	private ClientCommunicator cu;
	private static int newUserId;
	private static int newProjectId;
	private static int newBatchId;
	private static int newRecordId;
	private static int newFieldId;
	private static int newFieldValueId;
	private static String newFieldValue;
	private static String newImageUrl;
	
	@Before
	public void setup() throws Exception {
		Database setupdb = new Database();

		setupdb.startTransaction();
		User newUser = new User(-1, "jjones", "Jacob", "Jones", "123", "@com", -1, 0);
		setupdb.getUserDAO().createUser(newUser);
		newUserId = newUser.getUserId();
		setupdb.endTransaction(true);

		setupdb.startTransaction();
		Project newProj = new Project("census 2040", 1, 50, 98);
		setupdb.getProjectDAO().createProject(newProj);
		newProjectId = newProj.getProjectId();
		setupdb.endTransaction(true);

		setupdb.startTransaction();
		Batch newBatch = new Batch(-1, newProj.getProjectId(), "images/totally_fake_image.png", false, -1);
		setupdb.getBatchDAO().createBatch(newBatch);
		newBatchId = newBatch.getBatchId();
		newImageUrl = newBatch.getImageFile();
		setupdb.endTransaction(true);

		setupdb.startTransaction();
		Record newRecord = new Record();
		newRecord.setBatchId(newBatch.getBatchId());
		newRecord.setRecordNum(1);
		setupdb.getRecordDAO().createRecord(newRecord);
		newRecordId = newRecord.getRecordId();
		setupdb.endTransaction(true);

		setupdb.startTransaction();
		Field newField = new Field("newfieldtitle", newProj.getProjectId(), 37, 44, "something.com", "idk.txt");
		setupdb.getFieldDAO().createField(newField);
		newFieldId = newField.getFieldId();
		setupdb.endTransaction(true);

		setupdb.startTransaction();
		FieldValue newFV = new FieldValue();
		newFV.setFieldId(newField.getFieldId());
		newFV.setRecordId(newRecord.getRecordId());
		newFV.setValue("searchme");
		setupdb.getFieldValueDAO().createFieldValue(newFV);
		newFieldValue = newFV.getValue();
		newFieldValueId = newFV.getValueId();
		setupdb.endTransaction(true);
		
		cu = new ClientCommunicator("localhost", 8989);
	}
	
	@After
	public void teardown() throws Exception {
		Database teardownDb = new Database();
		teardownDb.startTransaction();
		teardownDb.getUserDAO().deleteUser(newUserId);
		teardownDb.getProjectDAO().deleteProject(newProjectId);
		teardownDb.getBatchDAO().deleteBatch(newBatchId);
		teardownDb.getRecordDAO().deleteRecord(newRecordId);
		teardownDb.getFieldDAO().deleteField(newFieldId);
		teardownDb.getFieldValueDAO().deleteFieldValue(newFieldValueId);
		teardownDb.endTransaction(true);
	}
	
	@Test
	public void validateUserTest() throws Exception {
		ValidateUser_Params params = new ValidateUser_Params();
		params.setUserName("jjones");
		params.setPassword("123");
		ValidateUser_Result result = cu.validateUser(params);
		assertNotNull(result.getUser());

		params.setPassword("123412341234");
		result = cu.validateUser(params);
		assertNull(result.getUser());
	}

	@Test
	public void getProjectsTest() throws Exception {
		GetProjects_Params params = new GetProjects_Params();
		params.setUserName("jjones");
		params.setPassword("123");
		GetProjects_Result result = cu.getProjects(params);
		assertNotNull(result.getProjects());

		params.setPassword("123412341234");
		try {
			result = cu.getProjects(params);
		} catch(ClientException e) {
			assertTrue(true);
			return;
		}
		assertTrue(false);
	}

	@Test
	public void getSampleImageTest() throws Exception {
		GetSampleImage_Params params = new GetSampleImage_Params();
		params.setUserName("jjones");
		params.setPassword("123");
		params.setProjectId(newProjectId);
		GetSampleImage_Result result = cu.getSampleImage(params);
		assertTrue(newImageUrl.equals(result.getImageUrl()));

		params.setProjectId(98172349);
		try {
			result = cu.getSampleImage(params);
		} catch(ClientException e) {
			assertTrue(true);
			return;
		}
		assertTrue(false);
	}

	@Test
	public void downloadBatchTest() throws Exception {
		DownloadBatch_Params params = new DownloadBatch_Params();
		params.setUserName("jjones");
		params.setPassword("123");
		params.setProjectId(newProjectId);
		DownloadBatch_Result result = cu.downloadBatch(params);
		assertEquals(result.getBatch().getBatchId(), newBatchId);
		assertTrue(result.getBatch().getAssignedUser() > -1);

		try {
			result = cu.downloadBatch(params);
		} catch(ClientException e) {
			assertTrue(true);
			return;
		}
		assertTrue(false);
	}

	@Test
	public void submitBatchTest() throws Exception {
		DownloadBatch_Params params = new DownloadBatch_Params();
		params.setUserName("jjones");
		params.setPassword("123");
		params.setProjectId(newProjectId);
		DownloadBatch_Result result = cu.downloadBatch(params);
		assertEquals(result.getBatch().getBatchId(), newBatchId);
		assertTrue(result.getBatch().getAssignedUser() > -1);
		
		SubmitBatch_Params sbParams = new SubmitBatch_Params();
		sbParams.setUserName("jjones");
		sbParams.setPassword("123");
		Batch newBatch = new Batch(newBatchId, newProjectId, newImageUrl, false, -1);
		Record newRecord = new Record();
		newRecord.setBatchId(newBatchId);
		newRecord.setRecordNum(1);
		FieldValue newFV = new FieldValue();
		newFV.setFieldId(newFieldId);
		newFV.setRecordId(newRecordId);
		newFV.setValue("testnewvaluefv");
		List<FieldValue> fvs = new ArrayList<FieldValue>();
		fvs.add(newFV);
		newRecord.setFieldValues(fvs);
		List<Record> records = new ArrayList<Record>();
		records.add(newRecord);
		newBatch.setRecords(records);
		sbParams.setBatch(newBatch);
		
		SubmitBatch_Result sbResult = cu.submitBatch(sbParams);
		assertNotNull(sbResult);

		try {
			sbResult = cu.submitBatch(sbParams);
		} catch(ClientException e) {
			assertTrue(true);
			return;
		}
		assertTrue(false);
	}

	@Test
	public void getFieldsTest() throws Exception {
		GetFields_Params params = new GetFields_Params();
		params.setUserName("jjones");
		params.setPassword("123");
		params.setProjectId(newProjectId);
		GetFields_Result result = cu.getFields(params);
		assertEquals(newFieldId, result.getFields().get(0).getFieldId());

		params.setProjectId(787878);
		try {
			result = cu.getFields(params);
		} catch(ClientException e) {
			assertTrue(true);
			return;
		}
		assertTrue(false);
	}

	@Test
	public void searchTest() throws Exception {
		Search_Params params = new Search_Params();
		params.setUserName("jjones");
		params.setPassword("123");

		// set the field ids
		List<Integer> fieldIds = new ArrayList<Integer>();
		fieldIds.add(newFieldId);
		params.setFieldIds(fieldIds);
		
		// set the search values
		List<String> searchValues = new ArrayList<String>();
		searchValues.add(newFieldValue);
		params.setSearchValue(searchValues);

		Search_Result result = cu.search(params);
		assertEquals(newBatchId, result.getSearchResults().get(0).getBatchId());

		// set phony field ids
		fieldIds = new ArrayList<Integer>();
		fieldIds.add(-1);
		params.setFieldIds(fieldIds);
		result = cu.search(params);
		assertEquals(0, result.getSearchResults().size());
	}
}

