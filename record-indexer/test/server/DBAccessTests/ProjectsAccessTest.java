package server.DBAccessTests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.database.Database;
import server.database.DatabaseException;
import server.database.access.ProjectAccess;
import shared.model.Project;

public class ProjectsAccessTest {

	private Database db;
	private ProjectAccess dbProject;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// Load database driver	
		Database.initialize();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		// Delete all contacts from the database	
				db = new Database();		
				db.startTransaction();
				
				List<Project> projects = db.getProjectAccess().getAllProjects();
				
				for (Project p : projects) {
					db.getProjectAccess().delete(p);
				}
				
				db.endTransaction(true);

				// Prepare database for test case	
				db = new Database();
				db.startTransaction();
				dbProject = db.getProjectAccess();	}

	@After
	public void tearDown() throws Exception {
		// Roll back transaction so changes to database are undone
		db.endTransaction(false);
		db = null;
		dbProject = null;
	}
	
	@Test
	public void getAllTest() throws DatabaseException {
		List<Project> all = dbProject.getAllProjects();
		assertEquals(0, all.size());
	}
	@Test
	public void testAdd() throws DatabaseException {
		Project bob = new Project(-1, "Draft Records", 3,2,5);
		Project amy = new Project(-1, "1900 Census", 15, 18,10);
		
		dbProject.addProject(bob);
		dbProject.addProject(amy);
		
		List<Project> all = dbProject.getAllProjects();
		assertEquals(2, all.size());
		
		boolean foundBob = false;
		boolean foundAmy = false;
		
		for (Project p : all) {
		
			assertFalse(p.getId() == -1);
		
			if (!foundBob) {
				foundBob = areEqual(p, bob, false);
			}		
			if (!foundAmy) {
				foundAmy = areEqual(p, amy, false);
			}
		}
		
		assertTrue(foundBob && foundAmy);
	}
	
	@Test
	public void testUpdate() throws DatabaseException {
		
		Project bob = new Project(-1, "Draft Records", 3,2,5);
		Project amy = new Project(-1, "1900 Census", 15, 18,10);
		
		dbProject.addProject(bob);
		dbProject.addProject(amy);
		
		bob.setTitle("new Title1");
		bob.setRecordsperimage(100);
		bob.setFirstycoordinate(101);
		bob.setRecordheight(102);
		
		amy.setTitle("new Title2");
		amy.setRecordsperimage(200);
		amy.setFirstycoordinate(106);
		amy.setRecordheight(600);
		
		
		dbProject.updateProject(bob);
		dbProject.updateProject(amy);
		
		List<Project> all = dbProject.getAllProjects();
		assertEquals(2, all.size());
		
		boolean foundBob = false;
		boolean foundAmy = false;
		
		for (Project p : all) {
			
			if (!foundBob) {
				foundBob = areEqual(p, bob, false);
			}		
			if (!foundAmy) {
				foundAmy = areEqual(p, amy, false);
			}
		}
		
		assertTrue(foundBob && foundAmy);
	}
	
	@Test
	public void testGetProjectsByID() throws DatabaseException{
		Project bob = new Project(-1, "Draft Records", 3,2,5);
		Project amy = new Project(-1, "1900 Census", 15, 18,10);
		
		dbProject.addProject(bob);
		dbProject.addProject(amy);
		
		List<Project> all = dbProject.getAllProjects();
		assertEquals(2, all.size());
		
		Project newbob = dbProject.getProjectByID(bob.getId());
		Project newamy = dbProject.getProjectByID(amy.getId());
		
		boolean foundBob = false;
		boolean foundAmy = false;
		
		foundBob = areEqual(newbob, bob, false);
		foundAmy = areEqual(newamy, amy, false);
		
		assertTrue(foundBob && foundAmy);
	}

	@Test
	public void testDelete() throws DatabaseException {
		
		Project bob = new Project(-1, "Draft Records", 3,2,5);
		Project amy = new Project(-1, "1900 Census", 15, 18,10);
		
		dbProject.addProject(bob);
		dbProject.addProject(amy);
		
		List<Project> all = dbProject.getAllProjects();
		assertEquals(2, all.size());
		
		dbProject.delete(bob);
		dbProject.delete(amy);
		
		all = dbProject.getAllProjects();
		assertEquals(0, all.size());
		
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidAdd() throws DatabaseException {
		
		Project invalidProject = new Project(-1, null, 0 ,0,0);
		dbProject.addProject(invalidProject);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidUpdate() throws DatabaseException {
		
		Project invalidProject= new Project(-1,null, 0,0,0);
		dbProject.updateProject(invalidProject);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidDelete() throws DatabaseException {
		
		Project invalidProject = new Project(-1, null,0,0,0);
		dbProject.delete(invalidProject);
	}
	
	private boolean areEqual(Project a, Project b, boolean compareIDs) {
		if (compareIDs) {
			if (a.getId() != b.getId()) {
				return false;
			}
		}	
		return (safeEquals(a.getTitle(), b.getTitle())&&
				safeEquals(a.getRecordsperimage(), b.getRecordsperimage()) &&
				safeEquals(a.getFirstycoordinate(), b.getFirstycoordinate())) &&
				safeEquals(a.getRecordheight(), b.getRecordheight());
	}
	
	private boolean safeEquals(Object a, Object b) {
		if (a == null || b == null) {
			return (a == null && b == null);
		}
		else {
			return a.equals(b);
		}
	}

}
