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
		User createdUser = new User(-1, "jacob", "Higgins", "pass1234", "test@test.com", 1, 0);
		db.getUserDAO().createUser(createdUser);
		User gotUser = db.getUserDAO().readUser(createdUser.getUserId());
		assertEquals(gotUser.getFirstName(), createdUser.getFirstName());
		assertEquals(gotUser.getLastName(), createdUser.getLastName());
		assertEquals(gotUser.getPassword(), createdUser.getPassword());
		assertEquals(gotUser.getEmail(), createdUser.getEmail());
		assertEquals(gotUser.getCurrentBatch(), createdUser.getCurrentBatch());
		assertEquals(gotUser.getIndexedRecords(), createdUser.getIndexedRecords());
	}

	public static void main(String[] args) {
		
		String[] testClasses = new String[] {
				"server.ServerUnitTests"
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}
	
}

