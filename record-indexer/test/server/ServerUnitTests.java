package server;

import org.junit.* ;

import server.DBAccessTests.*;
import server.database.DatabaseException;
import static org.junit.Assert.* ;

public class ServerUnitTests {
	
	@Before
	public void setup() {
		
	}
	
	@After
	public void teardown() {
		
	}
	
	@Test
	public void test_1() {
		assertEquals("OK", "OK");
		assertTrue(true);
		assertFalse(false);
	}

	public static void main(String[] args) throws DatabaseException {
		BatchAccessTest batchTest = new BatchAccessTest();
		
		FieldAccessTest fieldTest = new FieldAccessTest();
		
	}
	
}

