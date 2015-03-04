package server;

import java.util.List;

import org.junit.* ;

import shared.model.*;
import server.database.*;
import static org.junit.Assert.* ;

public class ServerUnitTests {
	
	@BeforeClass
	public static void classSetup() throws Exception {
		Database.initialize();
	}
	
	private Database db;
	
	@Before
	public void setup() throws Exception {
		db = new Database();
		db.startTransaction();
		List<User> users = db.getUserDAO().readUsers();
		
		for (User u : users) {
			db.getUserDAO().deleteUser(u.getUserId());
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
	public void testReadUser() throws Exception {
		User createdUser = new User(-1, "jhigg99", "jacob", "Higgins", "pass1234", "test@test.com", 1, 0);
		db.getUserDAO().createUser(createdUser);
		User gotUser = db.getUserDAO().readUser(createdUser.getUserId());
		assertEquals(gotUser.getFirstName(), createdUser.getFirstName());
		assertEquals(gotUser.getLastName(), createdUser.getLastName());
		assertEquals(gotUser.getPassword(), createdUser.getPassword());
		assertEquals(gotUser.getEmail(), createdUser.getEmail());
		assertEquals(gotUser.getCurrentBatch(), createdUser.getCurrentBatch());
		assertEquals(gotUser.getIndexedRecords(), createdUser.getIndexedRecords());
	}

	@Test
	public void testReadUsers() throws Exception {
		User createdUser1 = new User(-1, "jhigg99", "jacob", "Higgins", "pass1234", "test@test.com", 1, 0);
		User createdUser2 = new User(-1, "JAlph22", "ralph", "Jones", "pass4321", "test2@test2.com", 2, 19);
		db.getUserDAO().createUser(createdUser1);
		db.getUserDAO().createUser(createdUser2);
		List<User> gotUsers = db.getUserDAO().readUsers();
		assertEquals(gotUsers.get(0).getFirstName(), createdUser1.getFirstName());
		assertEquals(gotUsers.get(0).getLastName(), createdUser1.getLastName());
		assertEquals(gotUsers.get(0).getPassword(), createdUser1.getPassword());
		assertEquals(gotUsers.get(0).getEmail(), createdUser1.getEmail());
		assertEquals(gotUsers.get(0).getCurrentBatch(), createdUser1.getCurrentBatch());
		assertEquals(gotUsers.get(0).getIndexedRecords(), createdUser1.getIndexedRecords());

		assertEquals(gotUsers.get(1).getFirstName(), createdUser2.getFirstName());
		assertEquals(gotUsers.get(1).getLastName(), createdUser2.getLastName());
		assertEquals(gotUsers.get(1).getPassword(), createdUser2.getPassword());
		assertEquals(gotUsers.get(1).getEmail(), createdUser2.getEmail());
		assertEquals(gotUsers.get(1).getCurrentBatch(), createdUser2.getCurrentBatch());
		assertEquals(gotUsers.get(1).getIndexedRecords(), createdUser2.getIndexedRecords());
	}

	@Test
	public void testUpdateUser() throws Exception {
		User createdUser = new User(-1, "jhigg99", "jacob", "Higgins", "pass1234", "test@test.com", 1, 0);
		db.getUserDAO().createUser(createdUser);
		createdUser.setFirstName("Joseph");
		createdUser.setLastName("Mars");
		createdUser.setEmail("test2@anothertest.com");
		createdUser.setPassword("passfail");
		createdUser.setIndexedRecords(10);
		createdUser.setCurrentBatch(12);
		db.getUserDAO().updateUser(createdUser);
		User gotUser = db.getUserDAO().readUser(createdUser.getUserId());
		assertEquals(gotUser.getFirstName(), createdUser.getFirstName());
		assertEquals(gotUser.getLastName(), createdUser.getLastName());
		assertEquals(gotUser.getPassword(), createdUser.getPassword());
		assertEquals(gotUser.getEmail(), createdUser.getEmail());
		assertEquals(gotUser.getCurrentBatch(), createdUser.getCurrentBatch());
		assertEquals(gotUser.getIndexedRecords(), createdUser.getIndexedRecords());
	}

	@Test
	public void testDeleteUser() throws Exception {
		User createdUser = new User(-1, "jhigg99", "jacob", "Higgins", "pass1234", "test@test.com", 1, 0);
		db.getUserDAO().createUser(createdUser);
		db.getUserDAO().deleteUser(createdUser.getUserId());
		User gotUser = db.getUserDAO().readUser(createdUser.getUserId());
		assertNull(gotUser);

		List<User> users = db.getUserDAO().readUsers();
		for (User u : users) {
			db.getUserDAO().deleteUser(u.getUserId());
		}
		users = db.getUserDAO().readUsers();
		assertEquals(0, users.size());
	}

	public static void main(String[] args) {
		
		String[] testClasses = new String[] {
				"server.database.TestBatchDAO",
				"server.ServerUnitTests"
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}
	
}

