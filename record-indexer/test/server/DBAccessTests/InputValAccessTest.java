package server.DBAccessTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.database.Database;
import server.database.DatabaseException;
import server.database.access.InputValAccess;
import shared.model.Batch;
import shared.model.Inputvalue;
import shared.model.Record;

public class InputValAccessTest {

	private Database db;
	private InputValAccess dbValue;
	
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
				
				List<Inputvalue> values = db.getInputValAccess().getAllValues();
				
				for (Inputvalue i : values) {
					db.getInputValAccess().delete(i);
				}
				
				db.endTransaction(true);

				// Prepare database for test case	
				db = new Database();
				db.startTransaction();
				dbValue = db.getInputValAccess();	
	}

	@After
	public void tearDown() throws Exception {
		// Roll back transaction so changes to database are undone
		db.endTransaction(false);
		db = null;
		dbValue = null;
	}
	
	@Test
	public void getAllTest() throws DatabaseException {
		List<Inputvalue> all = dbValue.getAllValues();
		assertEquals(0, all.size());
	}
	@Test
	public void testAdd() throws DatabaseException {
		Inputvalue bob = new Inputvalue(-1,2,3, "Draft Records");
		Inputvalue amy = new Inputvalue(-1,11,6, "value");
		
		dbValue.addValue(bob);
		dbValue.addValue(amy);
		
		List<Inputvalue> all = dbValue.getAllValues();
		assertEquals(2, all.size());
		
		boolean foundBob = false;
		boolean foundAmy = false;
		
		for (Inputvalue i : all) {
		
			assertFalse(i.getId() == -1);
		
			if (!foundBob) {
				foundBob = areEqual(i, bob, false);
			}		
			if (!foundAmy) {
				foundAmy = areEqual(i, amy, false);
			}
		}
		
		assertTrue(foundBob && foundAmy);
	}
	@Test
	public void testUpdate() throws DatabaseException {
		
		Inputvalue bob = new Inputvalue(-1,2,3, "Draft Records");
		Inputvalue amy = new Inputvalue(-1,11,6, "value");
		
		dbValue.addValue(bob);
		dbValue.addValue(amy);
		
		bob.setRecord_id(13);
		bob.setField_id(14);
		bob.setInputvalue("title");
		
		amy.setRecord_id(17);
		amy.setField_id(18);
		amy.setInputvalue("titlez");
		
		dbValue.updateValue(bob);
		dbValue.updateValue(amy);
		
		List<Inputvalue> all = dbValue.getAllValues();
		assertEquals(2, all.size());
		
		boolean foundBob = false;
		boolean foundAmy = false;
		
		for (Inputvalue i : all) {
			
			if (!foundBob) {
				foundBob = areEqual(i, bob, false);
			}		
			if (!foundAmy) {
				foundAmy = areEqual(i, amy, false);
			}
		}
		
		assertTrue(foundBob && foundAmy);
	}
	
	@Test
	public void testGetValByRecordID() throws DatabaseException{
		Inputvalue bob = new Inputvalue(-1,2,3, "Draft Records");
		Inputvalue amy = new Inputvalue(-1,11,6, "value");
		Inputvalue carl = new Inputvalue(-1,2,14, "1880");
		
		dbValue.addValue(bob);
		dbValue.addValue(amy);
		dbValue.addValue(carl);
		
		List<Inputvalue> all = dbValue.getAllValues();
		assertEquals(3, all.size());
		
		List<Inputvalue> two = dbValue.getValByRecordID(2);
		List<Inputvalue> eleven = dbValue.getValByRecordID(11);
		
		assertEquals(two.size(),2);
		assertEquals(eleven.size(),1);
	}
	
	@Test
	public void testGetValByFieldID() throws DatabaseException{
		Inputvalue bob = new Inputvalue(-1,2,3, "Draft Records");
		Inputvalue amy = new Inputvalue(-1,11,6, "value");
		Inputvalue carl = new Inputvalue(-1,2,3, "1880");
		
		dbValue.addValue(bob);
		dbValue.addValue(amy);
		dbValue.addValue(carl);
		
		List<Inputvalue> all = dbValue.getAllValues();
		assertEquals(3, all.size());
		
		List<Inputvalue> three = dbValue.getValByFieldID(3);
		List<Inputvalue> six = dbValue.getValByFieldID(6);
		
		assertEquals(three.size(),2);
		assertEquals(six.size(),1);
	}
	
	@Test
	public void testDelete() throws DatabaseException {

		Inputvalue bob = new Inputvalue(-1,2,3, "Draft Records");
		Inputvalue amy = new Inputvalue(-1,11,6, "value");
		
		dbValue.addValue(bob);
		dbValue.addValue(amy);
		
		List<Inputvalue> all = dbValue.getAllValues();
		assertEquals(2, all.size());
		
		dbValue.delete(bob);
		dbValue.delete(amy);
		
		all = dbValue.getAllValues();
		assertEquals(0, all.size());
		
	}
	@Test
	public void testSearch() throws DatabaseException{
		Batch batch1 = new Batch(-1,2, "Draft Records", true,true,false);
		Batch batch2= new Batch(-1,11, "1990 Census", false,true,false);
		
		db.getBatchAccess().addBatch(batch1);
		db.getBatchAccess().addBatch(batch2);
		
		Record record1 = new Record(-1, 1,2);
		Record record2 = new Record(-1,2,10);
		
		db.getRecordAccess().addRecord(record1);
		db.getRecordAccess().addRecord(record2);
		
		Inputvalue bob = new Inputvalue(-1,1,3, "Draft Records");
		Inputvalue amy = new Inputvalue(-1,2,6, "value");
		
		dbValue.addValue(bob);
		dbValue.addValue(amy);
		
		ArrayList<String> tuple = db.getInputValAccess().search(1);
//		System.out.println(tuple.get(0));
//		System.out.println(tuple.get(1));
//		System.out.println(tuple.get(2));
//		System.out.println(tuple.get(3));
		
		assertEquals("3", tuple.get(0));
		assertEquals("images/draft_image0.png", tuple.get(1));
		assertEquals("0", tuple.get(2));
		assertEquals("3", tuple.get(3));
		
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidAdd() throws DatabaseException {
		
		Inputvalue invalidValue = new Inputvalue(-1, 0 ,0,null);
		dbValue.addValue(invalidValue);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidUpdate() throws DatabaseException {
		
		Inputvalue invalidField =  new Inputvalue(-1, 0 ,0,null);
		dbValue.updateValue(invalidField);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidDelete() throws DatabaseException {
		
		Inputvalue invalidValue = new Inputvalue(-1, 0 ,0,null);
		dbValue.delete(invalidValue);
	}
	
	
	private boolean areEqual(Inputvalue a, Inputvalue b, boolean compareIDs) {
		if (compareIDs) {
			if (a.getId() != b.getId()) {
				return false;
			}
		}	
		return (safeEquals(a.getRecord_id(), b.getRecord_id()) &&
				safeEquals(a.getField_id(), b.getField_id()) &&
				safeEquals(a.getInputvalue(), b.getInputvalue()) 
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
