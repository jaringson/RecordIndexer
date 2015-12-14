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
import server.database.access.FieldAccess;
import shared.model.Field;

public class FieldAccessTest {

	private Database db;
	private FieldAccess dbField;
	
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
				
				List<Field> fields = db.getFieldAccess().getAllFields();
				
				for (Field f : fields) {
					db.getFieldAccess().delete(f);
				}
				
				db.endTransaction(true);

				// Prepare database for test case	
				db = new Database();
				db.startTransaction();
				dbField = db.getFieldAccess();	}

	@After
	public void tearDown() throws Exception {
		// Roll back transaction so changes to database are undone
		db.endTransaction(false);
		db = null;
		dbField = null;
	}
	
	@Test
	public void getAllTest() throws DatabaseException {
		List<Field> all = dbField.getAllFields();
		assertEquals(0, all.size());
	}
	@Test
	public void testAdd() throws DatabaseException {
		Field bob = new Field(-1,2, "Draft Records", 3,2,"google","interesting",5);
		Field amy = new Field(-1,11, "Draft Records", 7,8,"Facebook","yeah",56);
		
		dbField.addField(bob);
		dbField.addField(amy);
		
		List<Field> all = dbField.getAllFields();
		assertEquals(2, all.size());
		
		boolean foundBob = false;
		boolean foundAmy = false;
		
		for (Field f : all) {
		
			assertFalse(f.getId() == -1);
		
			if (!foundBob) {
				foundBob = areEqual(f, bob, false);
			}		
			if (!foundAmy) {
				foundAmy = areEqual(f, amy, false);
			}
		}
		
		assertTrue(foundBob && foundAmy);
	}
	@Test
	public void testUpdate() throws DatabaseException {
		
		Field bob = new Field(-1,2, "Draft Records", 3,2,"google","interesting",5);
		Field amy = new Field(-1,11, "1900 Census", 7,8,"Facebook","yeah",56);
		
		dbField.addField(bob);
		dbField.addField(amy);
		
		bob.setProject_id(13);
		bob.setTitle("title");
		bob.setXcoord(15);
		bob.setHelphtml("help!");
		bob.setKnowndata("data");
		bob.setColumnnumber(16);
		
		amy.setProject_id(17);
		amy.setTitle("titlez");
		amy.setXcoord(19);
		amy.setHelphtml("helpz!");
		amy.setKnowndata("dataz");
		amy.setColumnnumber(20);
		
		dbField.updateField(bob);
		dbField.updateField(amy);
		
		List<Field> all = dbField.getAllFields();
		assertEquals(2, all.size());
		
		boolean foundBob = false;
		boolean foundAmy = false;
		
		for (Field f : all) {
			
			if (!foundBob) {
				foundBob = areEqual(f, bob, false);
			}		
			if (!foundAmy) {
				foundAmy = areEqual(f, amy, false);
			}
		}
		
		assertTrue(foundBob && foundAmy);
	}
	
	@Test
	public void testGetProjectsByID() throws DatabaseException{
		Field bob = new Field(-1,2, "Draft Records", 3,2,"google","interesting",5);
		Field amy = new Field(-1,11, "1900 Census", 7,8,"Facebook","yeah",56);
		Field carl = new Field(-1,2, "1880",15,16,"jaron","help",18);
		
		dbField.addField(bob);
		dbField.addField(amy);
		dbField.addField(carl);
		
		List<Field> all = dbField.getAllFields();
		assertEquals(3, all.size());
		
		List<Field> two = dbField.getFieldByProject(2);
		List<Field> eleven = dbField.getFieldByProject(11);
		
		assertEquals(two.size(),2);
		assertEquals(eleven.size(),1);
	}
	
	@Test
	public void testDelete() throws DatabaseException {
		
		Field bob = new Field(-1,2,"Draft Records", 3,2,"google","interesting",5);
		Field amy = new Field(-1,11, "Draft Records", 7,8,"Facebook","yeah",56);
		
		dbField.addField(bob);
		dbField.addField(amy);
		
		List<Field> all = dbField.getAllFields();
		assertEquals(2, all.size());
		
		dbField.delete(bob);
		dbField.delete(amy);
		
		all = dbField.getAllFields();
		assertEquals(0, all.size());
		
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidAdd() throws DatabaseException {
		
		Field invalidField = new Field(-1, 0 ,null,0,0,null,null,0);
		dbField.addField(invalidField);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidUpdate() throws DatabaseException {
		
		Field invalidField = new Field(-1, 0,null,0,0,null,null,0);
		dbField.updateField(invalidField);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidDelete() throws DatabaseException {
		
		Field invalidField = new Field(-1,0,null,0,0,null,null,0);
		dbField.delete(invalidField);
	}
	
	
	private boolean areEqual(Field a, Field b, boolean compareIDs) {
		if (compareIDs) {
			if (a.getId() != b.getId()) {
				return false;
			}
		}	
		return (safeEquals(a.getProject_id(), b.getProject_id()) &&
				safeEquals(a.getTitle(), b.getTitle()) &&
				safeEquals(a.getXcoord(), b.getXcoord()) &&
				safeEquals(a.getWidth(), b.getWidth()) &&
				safeEquals(a.getHelphtml(), b.getHelphtml()) &&
				safeEquals(a.getKnowndata(), b.getKnowndata()) &&
				safeEquals(a.getColumnnumber(), b.getColumnnumber())
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
