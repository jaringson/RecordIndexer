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
import server.database.access.RecordAccess;
import shared.model.Record;

public class RecordAccessTest {

	private Database db;
	private RecordAccess dbRecord;
	
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
				
				List<Record> records = db.getRecordAccess().getAllRecords();
				
				for (Record r : records) {
					db.getRecordAccess().delete(r);
				}
				
				db.endTransaction(true);

				// Prepare database for test case	
				db = new Database();
				db.startTransaction();
				dbRecord = db.getRecordAccess();	
	}

	@After
	public void tearDown() throws Exception {
		// Roll back transaction so changes to database are undone
		db.endTransaction(false);
		db = null;
		dbRecord = null;
	}
	
	@Test
	public void getAllTest() throws DatabaseException {
		List<Record> all = dbRecord.getAllRecords();
		assertEquals(0, all.size());
	}
	
	@Test
	public void testAdd() throws DatabaseException {
		Record bob = new Record(-1, 3,2);
		Record amy = new Record(-1,18,10);
		
		dbRecord.addRecord(bob);
		dbRecord.addRecord(amy);
		
		List<Record> all = dbRecord.getAllRecords();
		assertEquals(2, all.size());
		
		boolean foundBob = false;
		boolean foundAmy = false;
		
		for (Record r : all) {
		
			assertFalse(r.getId() == -1);
		
			if (!foundBob) {
				foundBob = areEqual(r, bob, false);
			}		
			if (!foundAmy) {
				foundAmy = areEqual(r, amy, false);
			}
		}
		
		assertTrue(foundBob && foundAmy);
	}
	
	@Test
	public void testUpdate() throws DatabaseException {
		
		Record bob = new Record(-1, 3,2);
		Record amy = new Record(-1,18,10);
		
		dbRecord.addRecord(bob);
		dbRecord.addRecord(amy);
		
		bob.setBatch_id(100);
		bob.setRow_number(200);
		
		amy.setBatch_id(300);
		amy.setRow_number(400);
		
		dbRecord.updateRecord(bob);
		dbRecord.updateRecord(amy);
		
		List<Record> all = dbRecord.getAllRecords();
		assertEquals(2, all.size());
		
		boolean foundBob = false;
		boolean foundAmy = false;
		
		for (Record r : all) {
		
			assertFalse(r.getId() == -1);
		
			if (!foundBob) {
				foundBob = areEqual(r, bob, false);
			}		
			if (!foundAmy) {
				foundAmy = areEqual(r, amy, false);
			}
		}
		
		assertTrue(foundBob && foundAmy);
	}
	
	@Test
	public void testDelete() throws DatabaseException {

		Record bob = new Record(-1, 3,2);
		Record amy = new Record(-1,18,10);
		
		dbRecord.addRecord(bob);
		dbRecord.addRecord(amy);
		
		List<Record> all = dbRecord.getAllRecords();
		assertEquals(2, all.size());
		
		dbRecord.delete(bob);
		dbRecord.delete(amy);
		
		all = dbRecord.getAllRecords();
		assertEquals(0, all.size());
		
	}
	
	@Test
	public void testGetRecordByBatchID() throws DatabaseException{
		Record bob = new Record(-1, 3,2);
		Record amy = new Record(-1,18,10);
		
		dbRecord.addRecord(bob);
		dbRecord.addRecord(amy);
		
		List<Record> all = dbRecord.getAllRecords();
		assertEquals(2, all.size());
		
		ArrayList<Record> newbob = dbRecord.getRecordsByBatch(3);
		ArrayList<Record> newamy = dbRecord.getRecordsByBatch(18);
		
		boolean foundBob = false;
		boolean foundAmy = false;
		
		
		
		foundBob = areEqual(newbob.get(0), bob, false);
		foundAmy = areEqual(newamy.get(0), amy, false);
		
		assertTrue(foundBob && foundAmy);
	}
	
	
	@Test(expected=DatabaseException.class)
	public void testInvalidUpdate() throws DatabaseException {
		
		Record invalidRecord= new Record(-1,0,0);
		dbRecord.updateRecord(invalidRecord);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidDelete() throws DatabaseException {
		
		Record invalidRecord = new Record(-1,0,0);
		dbRecord.delete(invalidRecord);
	}

	
	private boolean areEqual(Record a, Record b, boolean compareIDs) {
		if (compareIDs) {
			if (a.getId() != b.getId()) {
				return false;
			}
		}	
		return (safeEquals(a.getBatch_id(), b.getBatch_id())&&
				safeEquals(a.getRow_number(), b.getRow_number()));
				
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
