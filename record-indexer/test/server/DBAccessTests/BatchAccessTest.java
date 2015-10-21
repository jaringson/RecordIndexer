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
import server.database.access.BatchAccess;
import shared.model.Batch;

public class BatchAccessTest {

	private Database db;
	private BatchAccess dbBatch;
	
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
				
				List<Batch> batches = db.getBatchAccess().getAllBatches();
				
				for (Batch b : batches) {
					db.getBatchAccess().delete(b);
				}
				
				db.endTransaction(true);

				// Prepare database for test case	
				db = new Database();
				db.startTransaction();
				dbBatch = db.getBatchAccess();	}

	@After
	public void tearDown() throws Exception {
		// Roll back transaction so changes to database are undone
		db.endTransaction(false);
		db = null;
		dbBatch = null;
	}
	
	@Test
	public void getAllTest() throws DatabaseException {
		List<Batch> all = dbBatch.getAllBatches();
		assertEquals(0, all.size());
	}
	@Test
	public void testAdd() throws DatabaseException {
		Batch bob = new Batch(-1,2, "Draft Records", true,true,false);
		Batch amy = new Batch(-1,11, "1990 Census", false,true,false);
		
		dbBatch.addBatch(bob);
		dbBatch.addBatch(amy);
		
		List<Batch> all = dbBatch.getAllBatches();
		assertEquals(2, all.size());
		
		boolean foundBob = false;
		boolean foundAmy = false;
		
		for (Batch b : all) {
		
			assertFalse(b.getId() == -1);
		
			if (!foundBob) {
				foundBob = areEqual(b, bob, false);
			}		
			if (!foundAmy) {
				foundAmy = areEqual(b, amy, false);
			}
		}
		
		assertTrue(foundBob && foundAmy);
	}
	@Test
	public void testUpdate() throws DatabaseException {
		
		Batch bob = new Batch(-1,2, "Draft Records", true,true,false);
		Batch amy = new Batch(-1,11, "1990 Census", false,true,false);
		
		dbBatch.addBatch(bob);
		dbBatch.addBatch(amy);
		
		bob.setProjectid(13);
		bob.setFile("title");
		bob.setComplete(true);
		bob.setAvailable(true);
		bob.setCheckedout(false);
		
		amy.setProjectid(17);
		amy.setFile("titlez");
		amy.setComplete(false);
		amy.setAvailable(false);
		amy.setCheckedout(false);
		
		dbBatch.updateBatch(bob);
		dbBatch.updateBatch(amy);
		
		List<Batch> all = dbBatch.getAllBatches();
		assertEquals(2, all.size());
		
		boolean foundBob = false;
		boolean foundAmy = false;
		
		for (Batch b : all) {
			
			if (!foundBob) {
				foundBob = areEqual(b, bob, false);
			}		
			if (!foundAmy) {
				foundAmy = areEqual(b, amy, false);
			}
		}
		
		assertTrue(foundBob && foundAmy);
	}
	
	@Test
	public void testGetProjectsByID() throws DatabaseException{
		Batch bob = new Batch(-1,2, "Draft Records", true,true,false);
		Batch amy = new Batch(-1,11, "1990 Census", false,true,false);
		Batch carl = new Batch(-1,2, "1880",true,false,false);
		
		dbBatch.addBatch(bob);
		dbBatch.addBatch(amy);
		dbBatch.addBatch(carl);
		
		List<Batch> all = dbBatch.getAllBatches();
		assertEquals(3, all.size());
		
		List<Batch> two = dbBatch.getBatchByProjectID(2);
		List<Batch> eleven = dbBatch.getBatchByProjectID(11);
		
		assertEquals(two.size(),2);
		assertEquals(eleven.size(),1);
	}
	@Test
	public void testGetBatchByID() throws DatabaseException{
		Batch bob = new Batch(-1,2, "Draft Records", true,true,false);
		Batch amy = new Batch(-1,11, "1990 Census", false,true,false);
		
		dbBatch.addBatch(bob);
		dbBatch.addBatch(amy);
		
		Batch newbob = dbBatch.getBatchByID(1);
		Batch newamy = dbBatch.getBatchByID(2);
		

		boolean foundBob = false;
		boolean foundAmy = false;
		
		foundBob = areEqual(newbob, bob, false);
		foundAmy = areEqual(newamy, amy, false);
		
		assertTrue(foundBob && foundAmy);
	}
	
	@Test
	public void testDelete() throws DatabaseException {
		
		Batch bob = new Batch(-1,2, "Draft Records", true,true,false);
		Batch amy = new Batch(-1,11, "1990 Census", false,true,false);
		
		dbBatch.addBatch(bob);
		dbBatch.addBatch(amy);
		
		List<Batch> all = dbBatch.getAllBatches();
		assertEquals(2, all.size());
		
		dbBatch.delete(bob);
		dbBatch.delete(amy);
		
		all = dbBatch.getAllBatches();
		assertEquals(0, all.size());
		
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidAdd() throws DatabaseException {
		
		Batch invalidBatch = new Batch(-1,2, null, true,true,false);
		dbBatch.addBatch(invalidBatch);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidUpdate() throws DatabaseException {
		
		Batch invalidBatch = new Batch(-1,2, null, true,true,false);
		dbBatch.updateBatch(invalidBatch);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidDelete() throws DatabaseException {
		
		Batch invalidBatch = new Batch(-1,2, null, true,true,false);
		dbBatch.delete(invalidBatch);
	}
	
	
	private boolean areEqual(Batch a, Batch b, boolean compareIDs) {
		if (compareIDs) {
			if (a.getId() != b.getId()) {
				return false;
			}
		}	
		return (safeEquals(a.getProjectid(), b.getProjectid()) &&
				safeEquals(a.getFile(), b.getFile()) &&
				safeEquals(a.isComplete(), b.isComplete()) &&
				safeEquals(a.isAvailable(), b.isAvailable()) &&
				safeEquals(a.isCheckedout(), b.isCheckedout()) 
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
