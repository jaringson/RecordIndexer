package server;

import org.junit.* ;

import client.ClientUnitTests;
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
		
	String[] ss = new String[]{""};
	ClientUnitTests.main(ss);	
	
	String[] testClasses = new String[] {
				"server.ServerUnitTests",
				"server.DBAccessTests.BatchAccessTest",
				"server.DBAccessTests.FieldAccessTest",
				"server.DBAccessTests.InputValAccessTest",
				"server.DBAccessTests.ProjectsAccessTest",
				"server.DBAccessTests.RecordAccessTest",
				"server.DBAccessTests.UserAccessTest",
				
				"client.ClientUnitTests",
				"client.CCTests.GetProjectsTest",
				"client.CCTests.ValidateUsersTest",
				"client.CCTests.GetSampleImgTest",
				"client.CCTests.DownloadBatchTest",
				"client.CCTests.GetFeildsTest",
				"client.CCTests.SearchTest"
		};

		org.junit.runner.JUnitCore.main(testClasses);
		
	}
	
	
}

