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
import server.database.access.UserAccess;
import shared.model.User;

public class UserAccessTest {

	private Database db;
	private UserAccess dbUser;
	
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
				
				List<User> users = db.getUserAccess().getAllUsers();
				
				for (User u : users) {
					db.getUserAccess().delete(u);
				}
				
				db.endTransaction(true);

				// Prepare database for test case	
				db = new Database();
				db.startTransaction();
				dbUser = db.getUserAccess();	}

	@After
	public void tearDown() throws Exception {
		// Roll back transaction so changes to database are undone
		db.endTransaction(false);
		
		db = null;
		dbUser = null;
	}

	@Test
	public void getAllTest() throws DatabaseException {
		List<User> all = dbUser.getAllUsers();
		assertEquals(0, all.size());
	}
	@Test
	public void testAdd() throws DatabaseException {
		User bob = new User(-1, "Bob White", "bobwhite", "Bob", 
						"White", "bob@white.org",3,2);
		User amy = new User(-1, "Amy White", "amy@indexer", "Amy",
						"White", "amy@white.org", 15, 18);
		
		dbUser.addUser(bob);
		dbUser.addUser(amy);
		
		List<User> all = dbUser.getAllUsers();
		assertEquals(2, all.size());
		
		boolean foundBob = false;
		boolean foundAmy = false;
		
		for (User u : all) {
		
			assertFalse(u.getId() == -1);
		
			if (!foundBob) {
				foundBob = areEqual(u, bob, false);
			}		
			if (!foundAmy) {
				foundAmy = areEqual(u, amy, false);
			}
		}
		
		assertTrue(foundBob && foundAmy);
	}
	
	@Test
	public void testUpdate() throws DatabaseException {
		
		User bob = new User(-1, "Bob White", "bobwhite", "Bob", 
				"White", "bob@white.org",3,2);
		User amy = new User(-1, "Amy White", "amy@indexer", "Amy",
				"White", "amy@white.org", 15, 18);
		
		dbUser.addUser(bob);
		dbUser.addUser(amy);
		
		bob.setUsername("Robert White");
		bob.setPassword("robby@#$");
		bob.setFirstname("Robert");
		bob.setLastname("White");
		bob.setEmail("robert@white.org");
		bob.setIndexrecords(5);
		bob.setCurBatch(1);
		
		amy.setUsername("Amy Wilson White");
		amy.setPassword("@#$%^&*(");
		amy.setFirstname("Amy");
		amy.setLastname("White");
		amy.setEmail("amy.white@white.org");
		amy.setIndexrecords(89);
		amy.setCurBatch(44);
		
		dbUser.updateUser(bob);
		dbUser.updateUser(amy);
		
		List<User> all = dbUser.getAllUsers();
		assertEquals(2, all.size());
		
		boolean foundBob = false;
		boolean foundAmy = false;
		
		for (User u : all) {
			
			if (!foundBob) {
				foundBob = areEqual(u, bob, false);
			}		
			if (!foundAmy) {
				foundAmy = areEqual(u, amy, false);
			}
		}
		
		assertTrue(foundBob && foundAmy);
	}

	@Test
	public void testDelete() throws DatabaseException {
		
		User bob = new User(-1, "Bob White", "bobwhite", "Bob", 
				"White", "bob@white.org",3,2);
		User amy = new User(-1, "Amy White", "amy@indexer", "Amy",
				"White", "amy@white.org", 15, 18);
		
		dbUser.addUser(bob);
		dbUser.addUser(amy);
		
		List<User> all = dbUser.getAllUsers();
		assertEquals(2, all.size());
		
		dbUser.delete(bob);
		dbUser.delete(amy);
		
		all = dbUser.getAllUsers();
		assertEquals(0, all.size());
		
		
	}
	
	public void testValidateUserFail() throws DatabaseException {
		User bob = new User(-1, "Bob White", "bobwhite", "Bob", 
				"White", "bob@white.org",3,2);
		User amy = new User(-1, "Amy White", "amy@indexer", "Amy",
				"White", "amy@white.org", 15, 18);
		
		
		dbUser.addUser(bob);
		dbUser.addUser(amy);
		
		@SuppressWarnings("unused")
		User carl = new User(-1, "Carl White", "carl@indexer", "Carl",
				"White", "amy@white.org", 15, 18);
		
		@SuppressWarnings("unused")
		User u = dbUser.validateUser("Carl", "White");
		
		assertEquals(u,null);
	}
	
	@Test
	public void testValidateUser() throws DatabaseException {
		User bob = new User(-1, "Bob White", "bobwhite", "Bob", 
				"White", "bob@white.org",3,2);
		User amy = new User(-1, "Amy White", "amy@indexer", "Amy",
				"White", "amy@white.org", 15, 18);
		
		
		dbUser.addUser(bob);
		dbUser.addUser(amy);
		
		User u = dbUser.validateUser("Bob White", "bobwhite");
		User u2 = dbUser.validateUser("Amy White", "amy@indexer");
		
		boolean foundBob = false;
		boolean foundAmy = false;
		
		foundBob = areEqual(u, bob, false);
		foundAmy = areEqual(u2, amy, false);
		
		assertTrue(foundBob && foundAmy);
	}
	
	@Test
	public void testUpdataCurBatch() throws DatabaseException {
		User bob = new User(-1, "Bob White", "bobwhite", "Bob", 
				"White", "bob@white.org",3,2);
		User amy = new User(-1, "Amy White", "amy@indexer", "Amy",
				"White", "amy@white.org", 15, 18);
		
		dbUser.addUser(bob);
		dbUser.addUser(amy);
		
		bob.setCurBatch(99);
		amy.setCurBatch(100);
		
		dbUser.updateCurBatch(bob);
		dbUser.updateCurBatch(amy);
		
		List<User> all = dbUser.getAllUsers();
		assertEquals(2, all.size());
		
		boolean foundBob = false;
		boolean foundAmy = false;
		
		for (User u : all) {
			
			if (!foundBob) {
				foundBob = areEqual(u, bob, false);
			}		
			if (!foundAmy) {
				foundAmy = areEqual(u, amy, false);
			}
		}
		
		assertTrue(foundBob && foundAmy);
		
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidAdd() throws DatabaseException {
		
		User invalidUser = new User(-1, null, null, null, null, null, 0 ,0);
		dbUser.addUser(invalidUser);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidUpdate() throws DatabaseException {
		
		User invalidUser = new User(-1, null, null, null, null, null, 0,0);
		dbUser.updateUser(invalidUser);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidDelete() throws DatabaseException {
		
		User invalidUser = new User(-1, null, null, null, null, null,0,0);
		dbUser.delete(invalidUser);
	}
	
	
	
	private boolean areEqual(User a, User b, boolean compareIDs) {
		if (compareIDs) {
			if (a.getId() != b.getId()) {
				return false;
			}
		}	
		return (safeEquals(a.getUsername(), b.getUsername()) &&
				safeEquals(a.getPassword(), b.getPassword()) &&
				safeEquals(a.getFirstname(), b.getFirstname()) &&
				safeEquals(a.getLastname(), b.getLastname()) &&
				safeEquals(a.getEmail(), b.getEmail()) &&
				safeEquals(a.getIndexrecords(), b.getIndexrecords()) &&
				safeEquals(a.getCurBatch(), b.getCurBatch())
				);
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
