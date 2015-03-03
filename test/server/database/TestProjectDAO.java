package server.database;

import java.util.List;

import org.junit.* ;

import shared.model.*;
import static org.junit.Assert.* ;

public class TestProjectDAO {
	
	@BeforeClass
	public static void classSetup() throws Exception {
		Database.initialize();
	}
	
	private Database db;
	
	@Before
	public void setup() throws Exception {
		db = new Database();
		db.startTransaction();
		List<Project> projects = db.getProjectDAO().readProjects();
		
		for (Project p : projects) {
			db.getProjectDAO().deleteProject(p.getProjectId());
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
	public void testReadProject() throws Exception {
		Project createdProject = new Project("california", 20, 35, 77);
		db.getProjectDAO().createProject(createdProject);
		Project gotProject = db.getProjectDAO().readProject(createdProject.getProjectId());
		assertEquals(gotProject.getProjectId(), createdProject.getProjectId());
		assertEquals(gotProject.getTitle(), createdProject.getTitle());
		assertEquals(gotProject.getRecordsPerImage(), createdProject.getRecordsPerImage());
		assertEquals(gotProject.getRecordHeight(), createdProject.getRecordHeight());
		assertEquals(gotProject.getFirstYCoord(), createdProject.getFirstYCoord());
	}

	@Test
	public void testReadProjects() throws Exception {
		Project createdProject1 = new Project("california", 20, 35, 77);
		Project createdProject2 = new Project("florida", 30, 15, 86);
		db.getProjectDAO().createProject(createdProject1);
		db.getProjectDAO().createProject(createdProject2);
		List<Project> gotProjects = db.getProjectDAO().readProjects();
		assertEquals(gotProjects.get(0).getProjectId(), createdProject1.getProjectId());
		assertEquals(gotProjects.get(0).getTitle(), createdProject1.getTitle());
		assertEquals(gotProjects.get(0).getRecordsPerImage(), createdProject1.getRecordsPerImage());
		assertEquals(gotProjects.get(0).getRecordHeight(), createdProject1.getRecordHeight());
		assertEquals(gotProjects.get(0).getFirstYCoord(), createdProject1.getFirstYCoord());

		assertEquals(gotProjects.get(1).getProjectId(), createdProject2.getProjectId());
		assertEquals(gotProjects.get(1).getTitle(), createdProject2.getTitle());
		assertEquals(gotProjects.get(1).getRecordsPerImage(), createdProject2.getRecordsPerImage());
		assertEquals(gotProjects.get(1).getRecordHeight(), createdProject2.getRecordHeight());
		assertEquals(gotProjects.get(1).getFirstYCoord(), createdProject2.getFirstYCoord());
	}

	@Test
	public void testUpdateProject() throws Exception {
		Project createdProject = new Project("california", 20, 35, 77);
		db.getProjectDAO().createProject(createdProject);
		createdProject.setTitle("los angeles");
		createdProject.setRecordsPerImage(37);
		createdProject.setRecordHeight(99);
		createdProject.setFirstYCoord(89);
		db.getProjectDAO().updateProject(createdProject);

		Project gotProject = db.getProjectDAO().readProject(createdProject.getProjectId());
		assertEquals(gotProject.getProjectId(), createdProject.getProjectId());
		assertEquals(gotProject.getTitle(), createdProject.getTitle());
		assertEquals(gotProject.getRecordsPerImage(), createdProject.getRecordsPerImage());
		assertEquals(gotProject.getRecordHeight(), createdProject.getRecordHeight());
		assertEquals(gotProject.getFirstYCoord(), createdProject.getFirstYCoord());
	}

	@Test
	public void testDeleteProject() throws Exception {
		Project createdProject1 = new Project("california", 20, 35, 77);
		Project createdProject2 = new Project("florida", 30, 15, 86);
		Project createdProject3 = new Project("johannesburg", 32, 5, 60);
		db.getProjectDAO().createProject(createdProject1);
		db.getProjectDAO().createProject(createdProject2);
		db.getProjectDAO().createProject(createdProject3);

		db.getProjectDAO().deleteProject(createdProject1.getProjectId());

		Project gotProject = db.getProjectDAO().readProject(createdProject1.getProjectId());
		assertNull(gotProject);

		List<Project> projects = db.getProjectDAO().readProjects();
		for (Project b : projects) {
			db.getProjectDAO().deleteProject(b.getProjectId());
		}
		projects = db.getProjectDAO().readProjects();
		assertEquals(0, projects.size());
	}

}

