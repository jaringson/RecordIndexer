package server;

import org.junit.* ;

//import server.DBAccessTests.*;
//import server.database.DatabaseException;
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

public static void main(String[] args) {
		
		String[] testClasses = new String[] {
				"server.ServerUnitTests",
				"server.DBAccessTests.BatchAccessTest",
				"server.DBAccessTests.FieldAccessTest",
				"server.DBAccessTests.InputValAccessTest",
				"server.DBAccessTests.ProjectsAccessTest",
				"server.DBAccessTests.RecordAccessTest",
				"server.DBAccessTests.UserAccessTest",
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}
	
}

